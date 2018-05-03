package com.cognizant.bibliotecadigital.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.cognizant.bibliotecadigital.service.ReservaService;

@Controller
public class ReservaController {

	@Autowired
	private ReservaService reservaService;

	@GetMapping("/reservas")
	public ModelAndView findAll() {
		ModelAndView mv = new ModelAndView("/reserva/reserva");
		mv.addObject("reservas", reservaService.findAll());

		return mv;
	}
	
	@PostMapping("/reservas/deletarReserva")
	public ModelAndView deletar(@RequestParam("id") Long id) {
		reservaService.deleteById(id);
		ModelAndView mv = new ModelAndView("redirect:/reservas");		
		
		return mv;
	}
}