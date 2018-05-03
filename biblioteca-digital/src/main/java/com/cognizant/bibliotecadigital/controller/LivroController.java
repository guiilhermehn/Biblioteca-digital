package com.cognizant.bibliotecadigital.controller;

<<<<<<< HEAD
import java.util.ArrayList;
import java.util.List;
=======
import javax.validation.Valid;
>>>>>>> master

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

import com.cognizant.bibliotecadigital.model.Autor;
import com.cognizant.bibliotecadigital.model.Livro;
import com.cognizant.bibliotecadigital.service.LivroService;

@Controller
public class LivroController {

	@Autowired
	private LivroService livroService;

	@GetMapping("/livros")
	public ModelAndView findAll() {
		ModelAndView mv = new ModelAndView("/livro/livroPesquisa");
		mv.addObject("livros", livroService.findAll());

		return mv;
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
<<<<<<< HEAD
		Livro l1= new Livro();
		//l1.getAutores().add(new Autor());
//		List<Autor> autores = new ArrayList<Autor>();
//		autores.add(new Autor());
//		mv.addObject("autores",autores);
		mv.addObject("livro",l1 );
        return mv;
    }
	
	@PostMapping("/livros/create")
	public ModelAndView save(@ModelAttribute("livro") Livro livro, @ModelAttribute("autores") List<Autor> autores ) {
		Livro l1;
		l1 = livroService.save(livro);
=======
		mv.addObject("livro", new Livro());
		return mv;
	}

	@PostMapping("/livros/create")
	public ModelAndView create(@Valid @ModelAttribute Livro livro, BindingResult bindingRes,
			RedirectAttributes redAttributes) {

		if (bindingRes.hasErrors()) {
			return new ModelAndView("/livro/livroCadastro");
		}

>>>>>>> master
		
		livroService.save(livro);

		redAttributes.addFlashAttribute("mensagem", "Livro cadastrado com sucesso!");

		ModelAndView mv = new ModelAndView("redirect:/livros");
		return mv;
	}

	@PostMapping("/livros/update")
<<<<<<< HEAD
	public ModelAndView update(@ModelAttribute Livro livro){
		Livro l1;
		l1 = livroService.save(livro);

		
		ModelAndView mv = new ModelAndView("redirect:/livros");
		
=======
	public ModelAndView update(@ModelAttribute Livro livro) {

		livroService.findByIsbn13(livro.getIsbn13());

		ModelAndView mv = new ModelAndView("/livro/livroPesquisa");

>>>>>>> master
		return mv;

	}

	@PostMapping("/livros/deletarLivro")
	public ModelAndView deletar(@RequestParam("id") long id) {
		livroService.deleteById(id);
		ModelAndView redirect = new ModelAndView("redirect:/livros");
		return redirect;
	}

}
