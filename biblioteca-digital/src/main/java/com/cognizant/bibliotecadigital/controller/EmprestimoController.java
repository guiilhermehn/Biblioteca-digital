package com.cognizant.bibliotecadigital.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

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

import com.cognizant.bibliotecadigital.model.Emprestimo;
import com.cognizant.bibliotecadigital.model.Livro;
import com.cognizant.bibliotecadigital.model.Mail;
import com.cognizant.bibliotecadigital.model.Reserva;
import com.cognizant.bibliotecadigital.model.Status;
import com.cognizant.bibliotecadigital.model.StatusLivro;
import com.cognizant.bibliotecadigital.model.UnidadeLivro;
import com.cognizant.bibliotecadigital.model.Usuario;
import com.cognizant.bibliotecadigital.service.EmailService;
import com.cognizant.bibliotecadigital.service.EmprestimoService;
import com.cognizant.bibliotecadigital.service.LivroService;
import com.cognizant.bibliotecadigital.service.ReservaService;
import com.cognizant.bibliotecadigital.service.UnidadeLivroService;
import com.cognizant.bibliotecadigital.service.UsuarioService;

@Controller
public class EmprestimoController {

	@Autowired
	private EmprestimoService emprestimoService;
	@Autowired
	private UnidadeLivroService unidadeService;
	@Autowired
	private ReservaService reservaService;
	@Autowired
	private EmailService emailService;
	@Autowired
	private LivroService livroService;
	@Autowired
	private UsuarioService usuarioService;

	@GetMapping("/emprestimos")
	public ModelAndView findAll() {
		ModelAndView mv = new ModelAndView("/emprestimos/emprestimo");

		Usuario usuario = null;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			String email = auth.getName();
			usuario = usuarioService.findByEmail(email).orElse(null);
		}

		mv.addObject("emprestimos", emprestimoService.findAllByUsuarioId(usuario.getId()));
		return mv;
	}

	@PostMapping("/emprestimos/efetuarEmprestimo")
	public ModelAndView save(@RequestParam("unidadeId") Long unidadeId, RedirectAttributes redirectAttributes)
			throws MessagingException, IOException {

		UnidadeLivro unidade = unidadeService.findById(unidadeId).get();

		GregorianCalendar agora = new GregorianCalendar();

		String template = "email";

		GregorianCalendar prazo = new GregorianCalendar();
		prazo.add(Calendar.DAY_OF_MONTH, 7);

		Usuario usuario = null;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			String email = auth.getName();
			usuario = usuarioService.findByEmail(email).orElse(null);
		}

		unidade.getLivro().setStatusLivro(StatusLivro.COM_EMPRESTIMO);

		unidade.setLivro(unidade.getLivro());

		unidadeService.save(unidade);

		Emprestimo emprestimo = new Emprestimo(0L, agora.getTime(), null, prazo.getTime(), unidade, usuario);

		String assunto = "O " + emprestimo.getUnidadeLivro().getLivro().getTitulo() + " foi emprestado com sucesso !";
		emprestimoService.save(emprestimo);

		Mail email = emailService.enviarEmail(emprestimo.getUsuario(), emprestimo.getUnidadeLivro(), assunto);

		emailService.sendSimpleMessage(email, template);

		return new ModelAndView("redirect:/emprestimos");
	}

	@PostMapping("/emprestimos/efetuarDevolucao")
	public ModelAndView deletar(@RequestParam("id") Long id, RedirectAttributes redirectAttributes)
			throws MessagingException, IOException {

		Emprestimo emprestimo = emprestimoService.findById(id).get();

		emprestimo.setDataDevolucao(new Date());
		Livro livro = emprestimo.getUnidadeLivro().getLivro();
		livro.setStatusLivro(StatusLivro.EM_ANALISE);
		livroService.save(livro);

		emprestimoService.save(emprestimo);

		Long idReserva = reservaService.findReservaIdByEmprestimo(id);
		if (idReserva != null) {
			Reserva reserva = reservaService.findById(idReserva).get();

			reserva.setStatus(Status.EM_ANALISE);

			reservaService.save(reserva);

		}

		// TODO Fazer Span Para Notificar Que a Devolucao Está sob Analise

		return new ModelAndView("redirect:/emprestimos");
	}

	@GetMapping("/emprestimos/livrosDevolvidos")
	public ModelAndView findAllDevolucoes() throws MessagingException, IOException {
		ModelAndView mv = new ModelAndView("emprestimos/livrosDevolvidos");

		List<Emprestimo> emprestimos = (List<Emprestimo>) emprestimoService.findAll();
		List<Emprestimo> devolucoesEmAnalise = new ArrayList<>();
		if (!emprestimos.isEmpty()) {
			for (Emprestimo emprestimo : emprestimos) {
				Livro livro = emprestimo.getUnidadeLivro().getLivro();
				if (emprestimo.getDataDevolucao() != null) {
					if (livro.getStatusLivro().equals(StatusLivro.EM_ANALISE)) {
						emprestimo.setHabilita(false);
					} else {
						emprestimo.setHabilita(true);
					}
					devolucoesEmAnalise.add(emprestimo);

				}
			}
		}
		mv.addObject("emprestimos", devolucoesEmAnalise);

		return mv;
	}

	@PostMapping("/emprestimos/confirmaDevolucao")
	public ModelAndView confirmaDevolucao(@RequestParam("id") Long id, RedirectAttributes redirectAttributes)
			throws MessagingException, IOException {
		String template = "email-devolucao";

		Emprestimo emprestimo = emprestimoService.findById(id).get();

		String assunto = "O " + emprestimo.getUnidadeLivro().getLivro().getTitulo() + " foi devolvido com sucesso !";

		Livro livro = emprestimo.getUnidadeLivro().getLivro();
		livro.setStatusLivro(StatusLivro.SEM_EMPRESTIMO);
		livroService.save(livro);

		emprestimoService.save(emprestimo);

		Long idReserva = reservaService.findReservaIdByEmprestimo(id);
		if (idReserva != null) {
			Reserva reserva = reservaService.findById(idReserva).get();

			reserva.setStatus(Status.AGUARDANDO);

			reservaService.save(reserva);

		}

		Mail email = emailService.enviarEmail(emprestimo.getUsuario(), emprestimo.getUnidadeLivro(), assunto);

		emailService.sendSimpleMessage(email, template);

		return new ModelAndView("redirect:/emprestimos");
	}

	public void prazoDevolucaoEmail() {
		List<Emprestimo> emprestimos = (List<Emprestimo>) emailService.prazoDevolucao();
		String livro = "", dataDev = "";
		Date dataAtual = new Date();
		Long id;
		String template = "";
		for (int i = 0; i < emprestimos.size(); i++) {
			try {
				Usuario usuario = emprestimos.get(i).getUsuario();
				Date data = emprestimos.get(i).getPrazoDevolucao();

				dataDev = formatarData(data);

				id = emprestimos.get(i).getUnidadeLivro().getId();
				UnidadeLivro unidade = unidadeService.findById(id).get();
				livro = unidade.getLivro().getTitulo().toString();

				if (dataAtual.before(data)) {
					template = "email-lembrete";
				} else if (dataAtual.after(data)) {
					template = "email-esquecer";
				}
				Mail mail = emailService.lembreteDevolucao(usuario, livro, dataDev);
				emailService.sendSimpleMessage(mail, template);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public String formatarData(Date data) {
		String dataDev = "", mes = "", dia = "";
		dataDev = data.toString();
		mes = dataDev.substring(5, 7);
		dia = dataDev.substring(8, 10);
		dataDev = dia + "/" + mes;

		return dataDev;
	}
}
