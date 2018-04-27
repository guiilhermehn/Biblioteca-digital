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
public class UsuarioController {
	
	@Autowired
	private UsuarioService usuarioService;
	
	@GetMapping("/usuarios")
	public ModelAndView findAll() {
		ModelAndView mv = new ModelAndView("/usuario/usuario");
		mv.addObject("usuarios",usuarioService.findAll());
		
		return mv;
	}
	
	@GetMapping("/usuarios/novo")
	public ModelAndView novo() {
	        ModelAndView modelAndView = new ModelAndView("/pessoa/pessoa_form");
	        modelAndView.addObject("usuario", new Usuario());
	        return modelAndView;
	}
	
	@PostMapping(path = "/usuarios/salvar")
	public ModelAndView create(@ModelAttribute Usuario usuario) {
		usuarioService.save(usuario);
		return new ModelAndView("redirect:/usuarios");		
	}
	
	
	@GetMapping("/usuarios/{id}")
	public ModelAndView detail(@PathVariable("id") Long id) {
		ModelAndView mv = new ModelAndView("/usuario/usuario");
		mv.addObject("usuario",usuarioService.findById(id));
		
		return mv;
	}
	
	
	/* Alteração Bruno TODO : Usuario altera seu perfil ( dados pessoais)
	@GetMapping("/usuarios/perfil")
	public ModelAndView novo() {
	        ModelAndView modelAndView = new ModelAndView("/pessoa/pessoa_form");
	        modelAndView.addObject("usuario", new Usuario());
	        return modelAndView;
	}	
	*/
	
	

}
