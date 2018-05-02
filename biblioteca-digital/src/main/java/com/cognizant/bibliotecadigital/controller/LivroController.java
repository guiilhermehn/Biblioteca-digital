package com.cognizant.bibliotecadigital.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cognizant.bibliotecadigital.model.Livro;
import com.cognizant.bibliotecadigital.service.LivroService;

@Controller
public class LivroController {

	@Autowired
	private LivroService livroService;
	
	@GetMapping("/livros")
	public ModelAndView findAll() {
		ModelAndView mv = new ModelAndView("/livro/livroPesquisa");
		mv.addObject("livros",livroService.findAll());
		
		return mv;
	}
	
	@GetMapping("/livros/edit/{id}")
	public ModelAndView edit(@PathVariable("id")  long id){
		ModelAndView mv = new ModelAndView("/livro/livroEditar");
		mv.addObject("livro",livroService.findById(id).get());
		
		return mv;
	}
	
	@GetMapping("/livros/new")
    public ModelAndView create() {
		ModelAndView mv = new ModelAndView("/livro/livroCadastro");
        return mv;
    }
	
	@PostMapping("/livros/create")
	public ModelAndView create(@ModelAttribute Livro livro) {
		Livro l1;
		l1 = livroService.save(livro);
		
		ModelAndView mv = new ModelAndView("redirect:" + l1.getId());
		return mv;
	}
	
	@PostMapping(path = {"/livros/{id}/destroy", "/livros/destroy/{id}"})
    public ModelAndView destroy(@PathVariable("id") long id) {
        livroService.deleteById(id);
        ModelAndView redirect = new ModelAndView("redirect:/livros");
        return redirect;
    }
	
}
