package com.cognizant.bibliotecadigital.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;



import com.cognizant.bibliotecadigital.model.Autor;
import com.cognizant.bibliotecadigital.service.AutorService;

@Controller
@RequestMapping("/autores")
public class AutorController {
	
	@Autowired
	private AutorService autorService;
	//Busca todos os autores
	@GetMapping("/")
	public ModelAndView findAll() {

		ModelAndView mv = new ModelAndView("/autores/autor");
		mv.addObject("autores", autorService.findAll());

		return mv;
	}
	//Cria o formulário para execução do banco
	public ModelAndView autorForm(){
		ModelAndView mv = new ModelAndView("/autor/autor_form");
		mv.addObject("action", "create");
		mv.addObject("autor", new Autor());
		
		return mv;
	}
	
	//Insere dados dos autores no banco
	@PostMapping(path = "/cadastro/create")
	public ModelAndView create(@ModelAttribute Autor autor){
		autorService.save(autor);
		ModelAndView redirect = new ModelAndView("redirect" +autor.getId());
		
		return redirect;
	}
	

}
