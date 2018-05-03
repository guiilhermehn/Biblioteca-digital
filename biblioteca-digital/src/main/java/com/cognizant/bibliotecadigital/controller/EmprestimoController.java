package com.cognizant.bibliotecadigital.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.cognizant.bibliotecadigital.model.Emprestimo;
import com.cognizant.bibliotecadigital.model.UnidadeLivro;
import com.cognizant.bibliotecadigital.repository.UnidadadeLivroRepository;
import com.cognizant.bibliotecadigital.service.EmprestimoService;
import com.cognizant.bibliotecadigital.service.LivroService;

@Controller
public class EmprestimoController {

	@Autowired
	private EmprestimoService emprestimoService;
	
	@Autowired
	private LivroService livroService;

	
	@Autowired
	private UnidadadeLivroRepository unidadelivroRepository;
	
	@GetMapping("/emprestimos")
	public ModelAndView findAll() {
		ModelAndView mv = new ModelAndView("/emprestimos/emprestimo");
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
		Emprestimo emprestimo = new Emprestimo(0L, now.getTime(), null, prazo.getTime(),unidades) ;
		
		emprestimoService.save(emprestimo);
		
		ModelAndView mv = new ModelAndView("redirect:/emprestimos/emprestimo");		
		
		return mv;
	}
	


}


