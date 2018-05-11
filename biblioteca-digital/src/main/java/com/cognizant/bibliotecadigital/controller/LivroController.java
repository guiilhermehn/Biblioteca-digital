package com.cognizant.bibliotecadigital.controller;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cognizant.bibliotecadigital.model.Livro;
import com.cognizant.bibliotecadigital.model.UnidadeLivro;
import com.cognizant.bibliotecadigital.service.LivroService;
import com.cognizant.bibliotecadigital.service.UnidadeLivroService;

@Controller
public class LivroController {

	@Autowired
	private LivroService livroService;
	@Autowired
	private UnidadeLivroService unidadeLivroService;

	/*
	@GetMapping("/livros")
	public ModelAndView findAll() {
		ModelAndView mv = new ModelAndView("/livro/livroPesquisa");
		mv.addObject("livros", livroService.findAll());

		return mv;
	}
	*/

	@GetMapping("/livros")
	public ModelAndView findAll(@RequestParam(value = "q", required = false, defaultValue = "") String query) {
		ModelAndView mav = new ModelAndView("/livro/livroPesquisa");

		if (query.equals("")) {
			mav.addObject("livros", livroService.findAll());
		} else {
			mav.addObject("livros", livroService.search(query));
		}

		return mav;
	}

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
	public ModelAndView save(@Valid @ModelAttribute Livro livro, BindingResult bindingRes,
			RedirectAttributes redAttributes) {

		if (bindingRes.hasErrors()) {
			return new ModelAndView("/livro/livroCadastro");
		}

		Livro salvo = livroService.save(livro);
		unidadeLivroService.save(new UnidadeLivro(0L, null, livroService.findById(salvo.getId()).get()));

		redAttributes.addFlashAttribute("mensagem", "Livro cadastrado com sucesso!");

		ModelAndView mv = new ModelAndView("redirect:/livros");
		return mv;
	}

	@PostMapping("/livros/update")
	public ModelAndView update(@ModelAttribute Livro livro) {

		//livroService.findByIsbn13(livro.getIsbn13());

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
