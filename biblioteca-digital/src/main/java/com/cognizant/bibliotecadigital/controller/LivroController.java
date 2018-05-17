package com.cognizant.bibliotecadigital.controller;


import java.io.Console;

import javax.validation.Valid;

import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cognizant.bibliotecadigital.model.Livro;
import com.cognizant.bibliotecadigital.model.StatusLivro;
import com.cognizant.bibliotecadigital.model.UnidadeLivro;
import com.cognizant.bibliotecadigital.service.LivroService;
import com.cognizant.bibliotecadigital.service.UnidadeLivroService;

@Controller
public class LivroController{

	private static final Logger logger = LoggerFactory.getLogger(LivroController.class);
	@Autowired
	private LivroService livroService;
	@Autowired
	private UnidadeLivroService unidadeLivroService;
	
	//Retorna todos os livros cadastrados
	@GetMapping("/livros")
	public ModelAndView findAll(@RequestParam(value = "q", required = false, defaultValue = "") String query) {
		ModelAndView mv = new ModelAndView("/livro/livroPesquisa");
		
		if (query.equals("")) {
			mv.addObject("livros", livroService.findAll());
		} else {
			mv.addObject("livros", livroService.search(query));
		}
		
		return mv;
	}
	
	//Retorna o livro pela ID informada
	@GetMapping("/livros/edit/{id}")
	public ModelAndView edit(@PathVariable("id") long id) {
		ModelAndView mv = new ModelAndView("/livro/livroEditar");
		mv.addObject("livro", livroService.findById(id).get());

		return mv;
	}
	
	
	@GetMapping("/livros/new")
	public ModelAndView create() {
		ModelAndView mv = new ModelAndView("/livro/livroCadastro");
		
		mv.addObject("livro", new Livro());
		return mv;
	}

	@PostMapping("/livros/create")
	public ModelAndView save(@Valid @ModelAttribute("livro") Livro livro, BindingResult bindingRes,
			RedirectAttributes redAttributes) {
		
		
		if (bindingRes.hasErrors()) {
			logger.info("Erro na validação ao submeter o formulário!");
			ModelAndView mv = new ModelAndView("/livro/livroCadastro");
			return mv;			
			
		}
		try {
			Livro salvo = livroService.save(livro);
			unidadeLivroService.save(new UnidadeLivro(0L, null, livroService.findById(salvo.getId()).get()));


			livro.setStatusLivro(StatusLivro.SEM_EMPRESTIMO);
			redAttributes.addFlashAttribute("mensagem", "Livro cadastrado com sucesso!");
			logger.info("Sucesso ao submiter o formulário!");

			ModelAndView mv = new ModelAndView("redirect:/livros");
			return mv;
		} catch (Exception e) {
			System.out.println("Error= " + e);
			ModelAndView mv = new ModelAndView("/livro/livroCadastro");
			mv.addObject("ErrorKey", "ISBN já cadastrado!");
			mv.addObject("key_warning_cond", "true");
			return mv;
		}
	}

	@PostMapping("/livros/update")
	public ModelAndView update(@ModelAttribute Livro livro) {

		livroService.save(livro);

		ModelAndView mv = new ModelAndView("redirect:/livros");

		return mv;
	}

	@PostMapping("/livros/deletarLivro")
	public ModelAndView deletar(@RequestParam("id") long id) {
		livroService.deleteById(id);
		ModelAndView redirect = new ModelAndView("redirect:/livros");
		return redirect;
	}
	
	@PostMapping("/livro/unidade/edit")
	/*
	 * name="id" 
	 * name="livroId" 
 		name="avarias"
	 * */
	public ModelAndView mudarAvarias(@RequestParam("id") long id, @RequestParam("livroId") long livroId, @RequestParam("avarias") String avarias) {
		UnidadeLivro unidade = new UnidadeLivro(id, avarias, livroService.findById(livroId).get());
		unidadeLivroService.save(unidade);
		
		return new ModelAndView("redirect:/livros/edit/" + unidade.getLivro().getId());
	}
	
	@PostMapping("/livros/unidade/deletar")
	public ModelAndView deletarUnidade(@RequestParam("unidadeId") long unidadeId, 
			@RequestParam("livroId") long livroId ) {
		livroService.deleteById(unidadeId);
		return new ModelAndView("redirect:/livros/edit/" + livroId);
	}
	
	@PostMapping("/livros/unidade/create")
	public ModelAndView adicionarUnidade(@ModelAttribute UnidadeLivro unidade) {
		unidadeLivroService.save(unidade);
		return new ModelAndView("redirect:/livros/edit/" + unidade.getLivro().getId());
	}
}
