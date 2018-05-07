package com.cognizant.bibliotecadigital.controller;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cognizant.bibliotecadigital.model.Emprestimo;
import com.cognizant.bibliotecadigital.model.Mail;
import com.cognizant.bibliotecadigital.model.UnidadeLivro;
import com.cognizant.bibliotecadigital.model.Usuario;
import com.cognizant.bibliotecadigital.service.EmailService;
import com.cognizant.bibliotecadigital.service.EmprestimoService;
import com.cognizant.bibliotecadigital.service.UnidadeLivroService;

@Controller
public class EmprestimoController {

	@Autowired
	private EmprestimoService emprestimoService;
	@Autowired
	private UnidadeLivroService unidadeService;
	@Autowired
	private EmailService emailService;

	@GetMapping("/emprestimos")
	public ModelAndView findAll() {
		ModelAndView mv = new ModelAndView("/emprestimos/emprestimo");

		Usuario usuario = null;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			usuario = (Usuario) auth.getPrincipal();
		}

		mv.addObject("emprestimos", emprestimoService.findAllByUsuarioId(usuario.getId()));
		return mv;
	}

	/*
	 * @PostMapping("/emprestimos/deletarEmprestimo") public ModelAndView
	 * deletar(@RequestParam("id") Long id) { emprestimoService.deleteById(id);
	 * ModelAndView mv = new ModelAndView("redirect:/emprestimos");
	 * 
	 * return mv; }
	 */

	@PostMapping("/emprestimos/efetuarEmprestimo")
	public ModelAndView save(@RequestParam("unidadeId") Long unidadeId, RedirectAttributes redirectAttributes)
			throws MessagingException, IOException {
		// emprestimoService.findById(id);

		if (emprestimoService.isEmprestado(unidadeId)) {
			redirectAttributes.addFlashAttribute("message", "Livro já está emprestado!");
			return new ModelAndView("redirect:/emprestimos");
		}

		UnidadeLivro unidade = unidadeService.findById(unidadeId).get();

		GregorianCalendar agora = new GregorianCalendar();
		
		return mv;
	}
	/* TODO Adicionar melhoria realizada pela Raquel/Francisco
	@PostMapping("/emprestimos/deletarEmprestimo")
	public ModelAndView save(@RequestParam("id") Long id) {
		emprestimoService.deleteById(id);
		ModelAndView mv = new ModelAndView("redirect:/emprestimos");		
		
		return mv;
	}*/
}



	// @PostMapping("/emprestimos/deletarEmprestimo")
	// public ModelAndView save(@RequestParam("id") Long id) {
	// emprestimoService.deleteById(id);
	// ModelAndView mv = new ModelAndView("redirect:/emprestimos");
	//
	// return mv;
	// }

	@PostMapping("/emprestimos/efetuarDevolucao")
	public ModelAndView deletar(@RequestParam("id") Long id, RedirectAttributes redirectAttributes)
			throws MessagingException, IOException {
		String template = "email-devolucao";
		

		// TODO validar se usuário é o locatário

		Emprestimo emprestimo = emprestimoService.findById(id).get();
		
		String assunto = "O " + emprestimo.getUnidadeLivro().getLivro().getTitulo() + " foi devolvido com sucesso !";
				

		emprestimo.setDataDevolucao(new Date());

		emprestimoService.save(emprestimo);

		Mail email = emailService.enviarEmail(emprestimo.getUsuario(), emprestimo.getUnidadeLivro(),assunto);

		emailService.sendSimpleMessage(email, template);

		return new ModelAndView("redirect:/emprestimos");
	}

}
