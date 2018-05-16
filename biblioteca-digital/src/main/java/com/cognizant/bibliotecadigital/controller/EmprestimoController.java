package com.cognizant.bibliotecadigital.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Set;

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
import com.cognizant.bibliotecadigital.model.Livro;
import com.cognizant.bibliotecadigital.model.Mail;
import com.cognizant.bibliotecadigital.model.Reserva;
import com.cognizant.bibliotecadigital.model.Status;
import com.cognizant.bibliotecadigital.model.StatusLivro;
import com.cognizant.bibliotecadigital.model.UnidadeLivro;
import com.cognizant.bibliotecadigital.model.Usuario;
import com.cognizant.bibliotecadigital.service.EmailService;
import com.cognizant.bibliotecadigital.service.EmprestimoService;
import com.cognizant.bibliotecadigital.service.LivroService;
import com.cognizant.bibliotecadigital.service.ReservaService;
import com.cognizant.bibliotecadigital.service.UnidadeLivroService;

@Controller
public class EmprestimoController {

	@Autowired
	private EmprestimoService emprestimoService;
	@Autowired
	private UnidadeLivroService unidadeService;
	@Autowired
	private ReservaService reservaService;
	@Autowired
	private EmailService emailService;
	@Autowired
	private LivroService livroService;

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

//		if (emprestimoService.isEmprestado(unidadeId)) {
//			redirectAttributes.addFlashAttribute("message", "Livro já está emprestado!");
//			return new ModelAndView("redirect:/emprestimos");
//		}

		UnidadeLivro unidade = unidadeService.findById(unidadeId).get();

		GregorianCalendar agora = new GregorianCalendar();

		String template = "email-emprestimo";

		GregorianCalendar prazo = new GregorianCalendar();
		prazo.add(Calendar.DAY_OF_MONTH, 2);

		Usuario usuario = null;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			usuario = (Usuario) auth.getPrincipal();
		}

		unidade.getLivro().setStatusLivro(StatusLivro.COM_EMPRESTIMO);

		Emprestimo emprestimo = new Emprestimo(0L, agora.getTime(), null, prazo.getTime(), unidade, usuario);

		String assunto = "O " + emprestimo.getUnidadeLivro().getLivro().getTitulo() + " foi emprestado com sucesso !";
		emprestimoService.save(emprestimo);

		Mail email = emailService.enviarEmail(emprestimo.getUsuario(), emprestimo.getUnidadeLivro(), assunto);

		emailService.sendSimpleMessage(email, template);

		return new ModelAndView("redirect:/emprestimos");
	}

	@PostMapping("/emprestimos/efetuarDevolucao")
	public ModelAndView deletar(@RequestParam("id") Long id, RedirectAttributes redirectAttributes)
			throws MessagingException, IOException {
		String template = "email-devolucao";

		// TODO validar se usuário é o locatário

		Emprestimo emprestimo = emprestimoService.findById(id).get();

		String assunto = "O " + emprestimo.getUnidadeLivro().getLivro().getTitulo() + " foi devolvido com sucesso !";

		emprestimo.setDataDevolucao(new Date());
		Livro livro = emprestimo.getUnidadeLivro().getLivro();
		livro.setStatusLivro(StatusLivro.SEM_EMPRESTIMO);
		livroService.save(livro);

		emprestimoService.save(emprestimo);

		Long idReserva = reservaService.findReservaIdByEmprestimo(id);
		if (idReserva != null) {
			Reserva reserva = reservaService.findById(idReserva).get();

			reserva.setStatus(Status.AGUARDANDO);

			reservaService.save(reserva);

		}

		Mail email = emailService.enviarEmail(emprestimo.getUsuario(), emprestimo.getUnidadeLivro(), assunto);

		emailService.sendSimpleMessage(email, template);

		return new ModelAndView("redirect:/emprestimos");
	}
	
	public void prazoDevolucaoEmail() {
		List<Emprestimo> emprestimos = (List<Emprestimo>) emailService.prazoDevolucao();
		String nome = "", email = "", livro = "", dataDev = "", dia = "", mes = "";
		Date dataAtual = new Date();
		Long id;
		String template = "email-lembrete";
		for(int i = 0;i<emprestimos.size();i++) {
			try {
				nome = emprestimos.get(i).getUsuario().getNome().toString();
				email = emprestimos.get(i).getUsuario().getEmail().toString();
				Date data = emprestimos.get(i).getPrazoDevolucao();
				
				dataDev = data.toString();
				mes = dataDev.substring(5, 7);
				dia = dataDev.substring(8, 10);
				dataDev = dia + "/" + mes;
				
				id = emprestimos.get(i).getUnidadeLivro().getId();
				UnidadeLivro unidade = unidadeService.findById(id).get();
				livro = unidade.getLivro().getTitulo().toString();
				
				if(dataAtual.before(data)) {
					template = "email-lembrete";
				}
				else if(dataAtual.after(data)) {
					template = "email-esquecer";
				}
				Mail mail = emailService.lembreteDevolucao(email, nome, livro, dataDev);
				emailService.sendSimpleMessage(mail, template);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public String formatarData(Date date) {
		SimpleDateFormat formatar = new SimpleDateFormat("dd/MM/yyyy");
		return formatar.format(date);
	}
}
