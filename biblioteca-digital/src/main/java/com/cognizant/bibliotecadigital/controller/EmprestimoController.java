package com.cognizant.bibliotecadigital.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.cognizant.bibliotecadigital.service.EmprestimoService;

@Controller
public class EmprestimoController {

	@Autowired
	private EmprestimoService emprestimoService;

	@GetMapping("/emprestimos")
	public ModelAndView findAll() {
		ModelAndView mv = new ModelAndView("/emprestimos/emprestimos");
		mv.addObject("emprestimos", emprestimoService.findAll());

		return mv;
	}
	
	@PostMapping("/emprestimos/deletarEmprestimo")
	public ModelAndView deletar(@RequestParam("id") Long id) {
		emprestimoService.deleteById(id);
		ModelAndView mv = new ModelAndView("redirect:/emprestimos");		
		
		return mv;
	}
	/* TODO Adicionar melhoria realizada pela Raquel/Francisco
	@PostMapping("/emprestimos/deletarEmprestimo")
	public ModelAndView save(@RequestParam("id") Long id) {
		emprestimoService.deleteById(id);
		ModelAndView mv = new ModelAndView("redirect:/emprestimos");		
		
		return mv;
	}*/
}


