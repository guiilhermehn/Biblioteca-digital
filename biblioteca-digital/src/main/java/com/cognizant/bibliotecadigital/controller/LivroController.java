package com.cognizant.bibliotecadigital.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cognizant.bibliotecadigital.service.LivroService;

@Controller
public class LivroController {

	@Autowired
	private LivroService livroService;
	
	@GetMapping("/livros")
	public ModelAndView findAll() {
		ModelAndView mv = new ModelAndView("/livro/livro");
		mv.addObject("livros",livroService.findAll());
		
		return mv;
	}
}
