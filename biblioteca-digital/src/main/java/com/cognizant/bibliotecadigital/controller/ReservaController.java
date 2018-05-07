package com.cognizant.bibliotecadigital.controller;

import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cognizant.bibliotecadigital.model.Livro;
import com.cognizant.bibliotecadigital.model.Reserva;
import com.cognizant.bibliotecadigital.model.Status;
import com.cognizant.bibliotecadigital.model.Usuario;
import com.cognizant.bibliotecadigital.service.LivroService;
import com.cognizant.bibliotecadigital.service.ReservaService;
import com.cognizant.bibliotecadigital.service.UnidadeLivroService;

@Controller
public class ReservaController {

	@Autowired
	private ReservaService reservaService;

	@Autowired
	private LivroService livroService;

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
	
	@PostMapping("/reservas/efetuarReserva")
	public ModelAndView save(@RequestParam("livroId") Long livroId,
			RedirectAttributes redirectAttributes) throws MessagingException, IOException {

		Livro livro = livroService.findById(livroId).get();

		GregorianCalendar dataReserva = new GregorianCalendar();

		if (reservaService.isReservado(livroId)) {
			redirectAttributes.addFlashAttribute("message", "Livro já reservado!");
			return new ModelAndView("redirect:/reservas");
		}

		GregorianCalendar dataDisponibilidade = reservaService.getDataDisponibilidade(livroId);

		dataDisponibilidade.add(Calendar.DAY_OF_MONTH, 1);

		Usuario usuario = null;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			usuario = (Usuario) auth.getPrincipal();
		}

		Reserva reserva = new Reserva(usuario, dataReserva.getTime(), Status.AGUARDANDO, livro);
		reservaService.save(reserva);

		return new ModelAndView("redirect:/reservas");
	}

}