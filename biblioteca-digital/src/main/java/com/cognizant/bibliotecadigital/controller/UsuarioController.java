package com.cognizant.bibliotecadigital.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UsuarioController {
	
	@Autowired
	private UsuarioService usuarioService;
	
	@GetMapping("/")
	public ModelAndView findAll {
		ModelAndView mv = new ModelAndView("/usuario/usuario");
		mv.addObject("usuarios",usuarioService.findAll());
		
		return mv;
	}

}
