package com.cognizant.bibliotecadigital.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.cognizant.bibliotecadigital.model.Livro;
import com.cognizant.bibliotecadigital.model.Usuario;
import com.cognizant.bibliotecadigital.service.EmprestimoService;
import com.cognizant.bibliotecadigital.service.LivroService;
import com.cognizant.bibliotecadigital.service.UsuarioService;

@Controller
@RequestMapping
public class PesquisaController {
	
	@Autowired
	private LivroService livroService;
	
	//@Autowired
	//private UsuarioService usuarioService;
	
	@Autowired
	private EmprestimoService emprestadoService;
	
	@GetMapping({"", "/consulta"})
	public ModelAndView index(@RequestParam(value = "q", required = false, defaultValue = "") String query) {
		ModelAndView mav = new ModelAndView("consulta/consulta");
		Usuario usuario = null;
		
		/*Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			usuario = (Usuario) auth.getPrincipal();
		}*/
		
		if (query.equals("")) {
			mav.addObject("livros", livroService.findAll());
		} else {
			mav.addObject("livros", livroService.search(query));
		}
		
		
		return mav;
	}

	@GetMapping("/consulta/{id}")
	public ModelAndView detail(@PathVariable("id") Long id) {
		ModelAndView mav = new ModelAndView("consulta/detalhes");
		
		Livro livro = livroService.findById(id).get();
		
		livro.getUnidadeLivros().forEach(unidade -> {
			unidade.setEmprestado(emprestadoService.isEmprestado(unidade.getId()));
		});
		
		mav.addObject("livro", livro);
		
		return mav;
	}
}
