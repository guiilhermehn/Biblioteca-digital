/* ****************************************************
	 * Necessario Implementação futura
	 ******************************************************/

/*package com.cognizant.bibliotecadigital.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cognizant.bibliotecadigital.service.UsuarioService;

@Controller
public class AdminController {
	
	@Autowired
	UsuarioService usuarioService;
	
	//@GetMapping("/gerenciar")
	private ModelAndView findAll() {
		ModelAndView mv = new ModelAndView("/admin/gerenciar");
		mv.addObject("usuarios",usuarioService.findAll());
		return mv;
	}
}*/