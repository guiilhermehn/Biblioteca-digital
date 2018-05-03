package com.cognizant.bibliotecadigital.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.cognizant.bibliotecadigital.model.Usuario;
import com.cognizant.bibliotecadigital.service.UsuarioService;

@Controller
@RequestMapping
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;

	@GetMapping("/login")
	public ModelAndView login(@RequestParam(name = "error", required = false, defaultValue = "") String erro) {
		ModelAndView login = new ModelAndView("login/Login");
		if (erro.equals("erroLogin")) {
			login.addObject("msgErro", "Email ou Senha incorreta");
		}

		return login;
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

	@PostMapping("/register")
	public ModelAndView create(@ModelAttribute Usuario usuario) {

		usuarioService.save(usuario);

		ModelAndView mv = new ModelAndView("redirect:/login");
		return mv;
	}

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
