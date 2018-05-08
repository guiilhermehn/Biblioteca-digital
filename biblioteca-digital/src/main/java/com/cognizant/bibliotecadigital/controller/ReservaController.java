package com.cognizant.bibliotecadigital.controller;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.mail.MessagingException;

import org.apache.commons.lang.time.DateFormatUtils;
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

import com.cognizant.bibliotecadigital.model.Emprestimo;
import com.cognizant.bibliotecadigital.model.Livro;
import com.cognizant.bibliotecadigital.model.Mail;
import com.cognizant.bibliotecadigital.model.Reserva;
import com.cognizant.bibliotecadigital.model.Status;
import com.cognizant.bibliotecadigital.model.UnidadeLivro;
import com.cognizant.bibliotecadigital.model.Usuario;
import com.cognizant.bibliotecadigital.service.EmailService;
import com.cognizant.bibliotecadigital.service.EmprestimoService;
import com.cognizant.bibliotecadigital.service.LivroService;
import com.cognizant.bibliotecadigital.service.ReservaService;
import com.cognizant.bibliotecadigital.service.UnidadeLivroService;

@Controller
public class ReservaController {

	@Autowired
	private ReservaService reservaService;

	@Autowired
	private LivroService livroService;

	@Autowired
	private UnidadeLivroService unidadeService;

	@Autowired
	private EmprestimoService emprestimoService;

	@Autowired
	private EmailService emailService;

	boolean habilita;

	@GetMapping("/reservas")
	public ModelAndView findAll() {
		ModelAndView mv = new ModelAndView("/reserva/reserva");

		List<Reserva> reservas = (List<Reserva>) reservaService.findAll();
		for (Reserva reserva : reservas) {
			Date disponibilidade = calculaDisponibilidade(reserva);

			if (disponibilidade == null) {
				habilita = true;
				reserva.setDataPrevisao(null);
			} else {

				habilita(disponibilidade);

				reserva.setDataPrevisao(disponibilidade);
			}
		}

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
	public ModelAndView save(@RequestParam("livroId") Long livroId, RedirectAttributes redirectAttributes)
			throws MessagingException, IOException {

		Livro livro = livroService.findById(livroId).get();

		GregorianCalendar dataReserva = new GregorianCalendar();

		if (reservaService.isReservado(livroId)) {
			redirectAttributes.addFlashAttribute("message", "Livro já reservado!");
			return new ModelAndView("redirect:/reservas");
		}

		Usuario usuario = null;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			usuario = (Usuario) auth.getPrincipal();
		}

		Reserva reserva = new Reserva(usuario, dataReserva.getTime(), Status.EM_ESPERA, livro);

		reservaService.save(reserva);

		return new ModelAndView("redirect:/reservas");
	}

	@PostMapping("/emprestimos/efetuarEmprestimoAposReserva")
	public ModelAndView emprestimoAposReserva(@RequestParam("livroId") Long livroId,
			RedirectAttributes redirectAttributes) throws MessagingException, IOException {
		if (emprestimoService.isEmprestado(livroId)) {
			redirectAttributes.addFlashAttribute("message", "Livro já está emprestado!");
			return new ModelAndView("redirect:/emprestimos");
		}

		UnidadeLivro unidade = unidadeService.findById(livroId).get();

		GregorianCalendar agora = new GregorianCalendar();

		String template = "email-emprestimo";

		GregorianCalendar prazo = new GregorianCalendar();
		prazo.add(Calendar.DAY_OF_MONTH, 7);

		Usuario usuario = null;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			usuario = (Usuario) auth.getPrincipal();
		}

		Emprestimo emprestimo = new Emprestimo(0L, agora.getTime(), null, prazo.getTime(), unidade, usuario);

		String assunto = "O " + emprestimo.getUnidadeLivro().getLivro().getTitulo() + " foi emprestado com sucesso !";
		emprestimoService.save(emprestimo);

		Mail email = emailService.enviarEmail(emprestimo.getUsuario(), emprestimo.getUnidadeLivro(), assunto);

		emailService.sendSimpleMessage(email, template);
		
		Reserva reserva = reservaService.findById(livroId).get();

		return new ModelAndView("redirect:/emprestimos");
	}

	private Date calculaDisponibilidade(Reserva reserva) {

		Emprestimo emprestimo = emprestimoService.emprestimoPorReservaId(reserva.getId());

		if (emprestimo == null) {
			return null;
		}

		GregorianCalendar data = new GregorianCalendar();

		data.setTime(emprestimo.getDataDevolucao());

		return data.getTime();

	}

	private void habilita(Date disponibilidade) {
		Date dataAtual = new Date();

		String dataPrevisaoFormatada = DateFormatUtils.format(disponibilidade, "dd-MM-yyyy");
		String dataAtualFormatada = DateFormatUtils.format(dataAtual, "dd-MM-yyyy");

		if (!dataPrevisaoFormatada.equals(dataAtualFormatada)) {
			habilita = true;
		} else {
			habilita = false;
		}
	}

}