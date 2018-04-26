package com.cognizant.bibliotecadigital.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cognizant.bibliotecadigital.model.Usuario;
import com.cognizant.bibliotecadigital.service.UsuarioService;

@Controller
public class LoginController {
	
	@Autowired
	private UsuarioService usuarioService;
	
	/*@GetMapping("/login/{id}")
	public ModelAndView findById(@PathVariable("id") Long id) {
		Usuario usuario = usuarioService.findById(id);
		ModelAndView mv = new ModelAndView("/usuario/login");
		mv.addObject("usuarios",usuarioService.findAll());
		
		return mv;
	}*/
	
	
	@PostMapping(path = "/usuarios/salvar")
	public ModelAndView create(@ModelAttribute Usuario usuario) {
		usuarioService.save(usuario);
		return new ModelAndView("redirect:/usuarios");
		
	}

}
