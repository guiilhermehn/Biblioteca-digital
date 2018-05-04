package com.cognizant.bibliotecadigital.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cognizant.bibliotecadigital.model.Emprestimo;
import com.cognizant.bibliotecadigital.model.UnidadeLivro;
<<<<<<< HEAD
import com.cognizant.bibliotecadigital.repository.UnidadadeLivroRepository;
import com.cognizant.bibliotecadigital.service.EmprestimoService;
import com.cognizant.bibliotecadigital.service.LivroService;
=======
import com.cognizant.bibliotecadigital.model.Usuario;
import com.cognizant.bibliotecadigital.service.EmprestimoService;
import com.cognizant.bibliotecadigital.service.UnidadeLivroService;
>>>>>>> master

@Controller
public class EmprestimoController {

	@Autowired
	private EmprestimoService emprestimoService;
<<<<<<< HEAD
	
	@Autowired
	private LivroService livroService;
=======
	@Autowired
	private UnidadeLivroService unidadeService;
>>>>>>> master

	
	@Autowired
	private UnidadadeLivroRepository unidadelivroRepository;
	
	@GetMapping("/emprestimos")
	public ModelAndView findAll() {
		ModelAndView mv = new ModelAndView("/emprestimos/emprestimo");
<<<<<<< HEAD
		mv.addObject("emprestimos", emprestimoService.findAll());

		return mv;
	}
	
	@GetMapping("/consulta/templateDetalhes/{id}")
	public ModelAndView detalhes(@PathVariable("id") Long id ) {
		ModelAndView mv = new ModelAndView("/consulta/templateDetalhes");
		mv.addObject("livro", livroService.findById(id).get());
		System.out.println(id);
		return mv;
	}
	
	
	@GetMapping("/consulta/templateDetalhes/efetuarEmprestimo/{id}")
	public ModelAndView save(@PathVariable("id") Long id) {
		emprestimoService.findById(id);

		UnidadeLivro unidadeLivro = unidadelivroRepository.findById(id).get();
		
		GregorianCalendar now = new GregorianCalendar();
		GregorianCalendar prazo = new GregorianCalendar();
		prazo.add(Calendar.DAY_OF_MONTH, 7);
		List<UnidadeLivro> unidades = new ArrayList<>();
		unidades.add(unidadeLivro);
		Emprestimo emprestimo = new Emprestimo(0L, now.getTime(), null, prazo.getTime(), unidades) ;
		
		emprestimoService.save(emprestimo);
		
		ModelAndView mv = new ModelAndView("redirect:/emprestimos/emprestimo");		
=======
		
		Usuario usuario = null;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			usuario = (Usuario) auth.getPrincipal();
		}
		
		mv.addObject("emprestimos", emprestimoService.findAllByUsuarioId(usuario.getId()));
		return mv;
	}
	
	/*
	@PostMapping("/emprestimos/deletarEmprestimo")
	public ModelAndView deletar(@RequestParam("id") Long id) {
		emprestimoService.deleteById(id);
		ModelAndView mv = new ModelAndView("redirect:/emprestimos");		
>>>>>>> master
		
		return mv;
	}
	*/
	
	@PostMapping("/emprestimos/efetuarEmprestimo")
	public ModelAndView save(@RequestParam("unidadeId") Long unidadeId, 
			RedirectAttributes redirectAttributes) {
		// emprestimoService.findById(id);
		
		if(emprestimoService.isEmprestado(unidadeId)) {
			redirectAttributes.addFlashAttribute("message", "Livro já está emprestado!");
			return new ModelAndView("redirect:/emprestimos");
		}	
		
		UnidadeLivro unidade = unidadeService.findById(unidadeId).get();
		
		GregorianCalendar agora = new GregorianCalendar();
		
		GregorianCalendar prazo = new GregorianCalendar();
		prazo.add(Calendar.DAY_OF_MONTH, 7);
		
		Usuario usuario = null;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			usuario = (Usuario) auth.getPrincipal();
		}
		
		Emprestimo emprestimo = new Emprestimo(0L, agora.getTime(), null, prazo.getTime(), unidade, usuario) ;
		
		emprestimoService.save(emprestimo);
		
		
 		return new ModelAndView("redirect:/emprestimos");
 	}
	
<<<<<<< HEAD

=======
	@PostMapping("/emprestimos/efetuarDevolucao")
	public ModelAndView deletar(@RequestParam("id") Long id, 
			RedirectAttributes redirectAttributes) {
		
		//TODO validar se usuário é o locatário
		
		Emprestimo emprestimo = emprestimoService.findById(id).get();
		
		emprestimo.setDataDevolucao(new Date());
		
		emprestimoService.save(emprestimo);
		
		return new ModelAndView("redirect:/emprestimos");
	}
>>>>>>> master

}


