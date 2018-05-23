package com.cognizant.bibliotecadigital.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.cognizant.bibliotecadigital.model.Autor;
import com.cognizant.bibliotecadigital.service.AutorService;

/* ********************************
 * NECESSARIO IMPLEMENTACÃO
 **********************************/

// @Controller
public class AutorController {
	
	@Autowired
	private AutorService autorService;
	
	/* ****************************************
	 * Faz o mapeamento da página de autores
	 ******************************************/
	// @GetMapping("/autores")
	private ModelAndView findAll() {
		ModelAndView mv = new ModelAndView("/autor/autores");
		mv.addObject("autores",autorService.findAll());
		
		return mv;
	}
	
	/* ************************************************
	 * Faz o cadastro da página de cadastro de autores
	 **************************************************/
	// @GetMapping("/autores/novoAutor")
	public ModelAndView mostrarFormAutor() {
		ModelAndView mv = new ModelAndView("/autor/autorCadastro");
		mv.addObject("autor", new Autor());
		return mv;
	}
	
	/* ****************************************************
	 * Faz o cadastro de um novo autor no banco de dados
	 ******************************************************/
	// @PostMapping("/autores/create")
	public ModelAndView create(@Valid @ModelAttribute Autor autor) {
		
		autorService.save(autor);
		ModelAndView mv = new ModelAndView("redirect:/autores");
		return mv;
	}
	
	/* ***********************
	 * Faz a exclusão do autor
	 *************************/
	// @PostMapping("/autores/deletarAutor")
	public ModelAndView deletar(@RequestParam("id") long id) {
		autorService.deleteById(id);
		ModelAndView redirect = new ModelAndView("redirect:/autores");
		return redirect;
	}
	
}
