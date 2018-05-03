package com.cognizant.bibliotecadigital.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
	
	//Cria o formulário para execução do banco
	@GetMapping("/livros/cadastro")
	public ModelAndView cadastro(){
		ModelAndView modelAndView = new ModelAndView("/livro/livroCadastro");
		modelAndView.addObject("livro", new Livro());
		return modelAndView;
	}
	
	//Insere os dados no banco
	@PostMapping("/livros/salvar")
	public ModelAndView create(@Valid @ModelAttribute Livro livro, BindingResult bindingRes, RedirectAttributes redAttributes){
		if (bindingRes.hasErrors()){
			return new ModelAndView("/livro/livroCadastro");
		}
		livroService.save(livro);
		
		redAttributes.addFlashAttribute("mensagem", "Livro cadastrado com sucesso!");
		
		return new ModelAndView("redirect:/livros");
	}
	
	@GetMapping("/livros/edit/{id}")
	public ModelAndView edit(@PathVariable("id")  long id){
		
		return new ModelAndView();
	}
}
