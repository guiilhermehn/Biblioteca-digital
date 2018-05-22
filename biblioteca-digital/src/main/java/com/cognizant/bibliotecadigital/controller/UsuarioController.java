package com.cognizant.bibliotecadigital.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.cognizant.bibliotecadigital.model.Usuario;
import com.cognizant.bibliotecadigital.security.SecurityConfig;
import com.cognizant.bibliotecadigital.service.PapelService;
import com.cognizant.bibliotecadigital.service.UsuarioService;

@Controller
@RequestMapping
public class UsuarioController {
	//Serviços chamados
	@Autowired
	private UsuarioService usuarioService;
	@Autowired
	private PapelService papelService;

	/* ***************************************************************
	 * Faz o mapeamento do login,  faz a validação do e-mail e senha
	 * Caso estejam errado, mostra mensagem de erro
	 **************************************************************** */
	@GetMapping("/login")
	public ModelAndView login(@RequestParam(name = "error", required = false, defaultValue = "") String erro) {
		ModelAndView login = new ModelAndView("login/Login");

		if (erro.equals("erroLogin")) {
			login.addObject("msgErro", "Email ou Senha incorreta");
		} 
        login.addObject("usuario", new Usuario());

		return login;
	}

	@GetMapping("/usuarios")
	public ModelAndView findAll() {
		ModelAndView mv = new ModelAndView("/usuario/usuario");
		mv.addObject("usuarios", usuarioService.findAll());

		return mv;
	}

	/* ********************************************************
	 * Faz o mapeamento para a página de cadastro de usuários
	 ******************************************************** */
	@GetMapping("/register")
	public ModelAndView register() {
		ModelAndView modelAndView = new ModelAndView("register/Register");
		modelAndView.addObject("usuario", new Usuario());
		return modelAndView;
	}
	
	/* ***********************************************
	 * Faz o cadastro do usuário no banco de dados,
	 * criptografa a senha e designa o papel do usuário,
	 * se houver erros no formulário, retorna à pagina de cadastro
	 ************************************************ */
	@PostMapping("/register/create")
	public ModelAndView create(@ModelAttribute @Valid Usuario usuario, BindingResult bindingRes) {

		if (bindingRes.hasErrors()) {
			return register();
		}


		usuario.setSenha(SecurityConfig.bcryptPasswordEncoder().encode(usuario.getSenha()));
		usuario.getPapeis().add(papelService.findByNome("ROLE_USUARIO").get());
		usuarioService.save(usuario);

		ModelAndView mv = new ModelAndView("redirect:/login");
		return mv;
	}
	
	/* **********************************************************************
	 * Faz o mapeamento do perfil do usuário (NÃO IMPLEMENTADO AINDA)
	 ********************************************************************** */
	@GetMapping("/usuarios/{id}")
	public ModelAndView detail(@PathVariable("id") Long id) {
		ModelAndView mv = new ModelAndView("/usuario/usuario");
		mv.addObject("usuario", usuarioService.findById(id));

		return mv;
	}
	
	 /* *********************************
	  * Faz o mapeamanto da tela de erro 
	  ********************************* */
	@GetMapping("/erroAutorizacao")
	public ModelAndView exibirErro() {
		ModelAndView mv = new ModelAndView("/login/erro401");
		
		return mv;
	} 

}