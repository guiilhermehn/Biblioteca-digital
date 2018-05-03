package com.cognizant.bibliotecadigital.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
<<<<<<< HEAD
import org.springframework.web.bind.annotation.PostMapping;
=======
import org.springframework.web.bind.annotation.RequestMapping;
>>>>>>> aa6887729be9552d8dd8fbb96ab71c6255b43848
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cognizant.bibliotecadigital.model.Usuario;
import com.cognizant.bibliotecadigital.service.UsuarioService;

@Controller
@RequestMapping
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;

	@GetMapping("/login")
	public ModelAndView login() {
		return new ModelAndView("login/Login");
	}

	@GetMapping("/register")
	public ModelAndView register() {
		ModelAndView modelAndView = new ModelAndView("register/Register");
		modelAndView.addObject("usuario", new Usuario());
		return modelAndView;
	}

	@GetMapping("/usuarios")
	public ModelAndView findAll() {
		ModelAndView mv = new ModelAndView("/usuario/usuario");
		mv.addObject("usuarios", usuarioService.findAll());

		return mv;
	}
	
<<<<<<< HEAD
	@GetMapping("/usuarios/novo")
	public ModelAndView novo() {
	        ModelAndView modelAndView = new ModelAndView("/pessoa/pessoa_form");
	        modelAndView.addObject("usuario", new Usuario());
	        return modelAndView;
	}
	
	/*@PostMapping(path = "/usuarios/salvar")
	public ModelAndView create(@Valid @ModelAttribute Usuario usuario, BindingResult bindingRes, RedirectAttributes redAttributes) {
		if (bindingRes.hasErrors()){
			
			return new ModelAndView("/pessoa/pessoa_form");
		}
		usuarioService.save(usuario);
		
		redAttributes.addFlashAttribute("mensagem", "Usuário salvo com sucesso!");
		
		return new ModelAndView("redirect:/usuarios");		
	}*/
	
	
=======
	

	// @GetMapping("/usuarios/novo")
	// public ModelAndView novo() {
	// ModelAndView modelAndView = new ModelAndView("/pessoa/pessoa_form");
	// modelAndView.addObject("usuario", new Usuario());
	// return modelAndView;
	// }

	/*
	 * @PostMapping(path = "/usuarios/salvar") public ModelAndView
	 * create(@ModelAttribute Usuario usuario) { usuarioService.save(usuario);
	 * return new ModelAndView("redirect:/usuarios"); }
	 */

>>>>>>> aa6887729be9552d8dd8fbb96ab71c6255b43848
	@GetMapping("/usuarios/{id}")
	public ModelAndView detail(@PathVariable("id") Long id) {
		ModelAndView mv = new ModelAndView("/usuario/usuario");
		mv.addObject("usuario", usuarioService.findById(id));

		return mv;
	}

	/*
	 * Alteração Bruno TODO : Usuario altera seu perfil ( dados pessoais)
	 * 
	 * @GetMapping("/usuarios/perfil") public ModelAndView novo() { ModelAndView
	 * modelAndView = new ModelAndView("/pessoa/pessoa_form");
	 * modelAndView.addObject("usuario", new Usuario()); return modelAndView; }
	 */

}
