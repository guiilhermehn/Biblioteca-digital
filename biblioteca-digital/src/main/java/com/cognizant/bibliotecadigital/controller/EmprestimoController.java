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
import com.cognizant.bibliotecadigital.service.PapelService;
import com.cognizant.bibliotecadigital.service.ReservaService;
import com.cognizant.bibliotecadigital.service.UnidadeLivroService;
import com.cognizant.bibliotecadigital.service.UsuarioService;

@Controller
public class EmprestimoController {
	//Serviços chamados
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
	
	@Autowired
	private PapelService papelService;

	/* ******************************************
	 * Faz o mapeamento da tela de empréstimos 
	 ********************************************/
	@GetMapping("/emprestimos")
	public ModelAndView findAll() {
		ModelAndView mv = new ModelAndView("/emprestimos/emprestimo");

		Usuario usuario = null;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			String email = auth.getName();
			usuario = usuarioService.findByEmail(email).orElse(null);
		}
		
		boolean isAdmin = usuario.getPapeis().contains(papelService.findByNome("ROLE_ADMIN").get());
		mv.addObject("isAdmin", isAdmin);

		mv.addObject("emprestimos", emprestimoService.findAllByUsuarioId(usuario.getId()));
		return mv;
	}

	/* *****************************************************************************************************
	 * Efetua o empréstimo do livro
	 * Faz as atualizações no banco de dados, como a adição da data da retirada e prazo para entrega do livro
	 * E envia um e-mail para o usuário, confirmando a retirada do livro
	 *******************************************************************************************************/
	@PostMapping("/emprestimos/efetuarEmprestimo")
	public ModelAndView save(@RequestParam("unidadeId") Long unidadeId, RedirectAttributes redirectAttributes)
			throws MessagingException, IOException {

		UnidadeLivro unidade = unidadeService.findById(unidadeId).get();

		GregorianCalendar agora = new GregorianCalendar();

		String template = "email-emprestimo";

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

		Emprestimo emprestimo = new Emprestimo(0L, agora.getTime(), null, prazo.getTime(), unidade, usuario,Status.ATIVO);

		String assunto = "Emprestimo do livro: " + emprestimo.getUnidadeLivro().getLivro().getTitulo();
		emprestimoService.save(emprestimo);

		Mail email = emailService.enviarEmail(emprestimo.getUsuario(), emprestimo.getUnidadeLivro(), assunto);

		emailService.sendSimpleMessage(email, template);

		return new ModelAndView("redirect:/emprestimos");
	}
	
	/* ************************************************************************************
	 * Efetua a devolução do livro, após empréstimo
	 * O livro fica no status "EM_ANALISE", aguardando a confirmação de um usuário Admin
	 * para confirmar se o livro foi realmente entregue
	 ***************************************************************************************/
	@PostMapping("/emprestimos/efetuarDevolucao")
	public ModelAndView deletar(@RequestParam("id") Long id, RedirectAttributes redirectAttributes)
			throws MessagingException, IOException {

		Emprestimo emprestimo = emprestimoService.findById(id).get();

		emprestimo.setDataDevolucao(new Date());
		Livro livro = emprestimo.getUnidadeLivro().getLivro();
		livro.setStatusLivro(StatusLivro.EM_ANALISE);
		livroService.save(livro);
		emprestimo.setEmprestimoStatus(Status.EM_ANALISE);
		emprestimoService.save(emprestimo);

		// TODO Fazer Span Para Notificar Que a Devolucao Está sob Analise

		return new ModelAndView("redirect:/emprestimos");
	}

	/* ****************************************************************
	 * Faz o mapeamento da página de livros devolvidos
	 * Essa é uma página que apenas o usuário Admin tem acesso
	 * Todos os livros devolvidos por usuários, não confirmados
	 * são listados nessa página, para que o usuário Admin possa fazer
	 * a confirmação
	 ******************************************************************/
	@GetMapping("/emprestimos/livrosDevolvidos")
	public ModelAndView findAllDevolucoes() throws MessagingException, IOException {
		ModelAndView mv = new ModelAndView("emprestimos/livrosDevolvidos");

		List<Emprestimo> emprestimos = (List<Emprestimo>) emprestimoService.findAll();
		List<Emprestimo> devolucoesEmAnalise = new ArrayList<>();
			
		if (!emprestimos.isEmpty()) {
			for (Emprestimo emprestimo : emprestimos) {
				Livro livro = emprestimo.getUnidadeLivro().getLivro();
				if (emprestimo.getDataDevolucao() != null) {
					if (livro.getStatusLivro().equals(StatusLivro.EM_ANALISE) 
							&& emprestimo.getEmprestimoStatus().equals(Status.EM_ANALISE) ) {
						emprestimo.setHabilita(false);
						emprestimo.setEmprestimoStatus(Status.FINALIZADO);

					} else {
						emprestimo.setHabilita(true);
					}
					emprestimoService.save(emprestimo);
					devolucoesEmAnalise.add(emprestimo);
				}
			}
		}
		mv.addObject("emprestimos", devolucoesEmAnalise);

		return mv;
	}

	/* **************************************************************************************
	 * O usuário Admin faz a confirmação da entrega do livro
	 * O usuário que fez  entrega receberá um e-mail, sobre a confirmação da entrega do livro
	 * O status do livro será atualizado para "SEM_EMPRESTIMO"
	 * E caso já houvesse uma reserva para esse livro,
	 * o status da reserva será atualizado para "AGUARDANDO"
	 ****************************************************************************************/
	@PostMapping("/emprestimos/confirmaDevolucao")
	public ModelAndView confirmaDevolucao(@RequestParam("id") Long id, RedirectAttributes redirectAttributes)
			throws MessagingException, IOException {
		String template = "email-devolucao";

		Emprestimo emprestimo = emprestimoService.findById(id).get();

		String assunto = "Devolucao do livro: " + emprestimo.getUnidadeLivro().getLivro().getTitulo();

		Livro livro = emprestimo.getUnidadeLivro().getLivro();
		livro.setStatusLivro(StatusLivro.SEM_EMPRESTIMO);
		livroService.save(livro);

		emprestimoService.save(emprestimo);

		Long idReserva = reservaService.findReservaIdByEmprestimo(id);
		if (idReserva != null) {
			Reserva reserva = reservaService.findById(idReserva).get();

			reserva.setStatus(Status.AGUARDANDO);

			reservaService.save(reserva);
			String assuntoReservaDisponivel = "Reserva Disponivel!";
			String templateReservaDisponivel = "email-disponibilidade-reserva";
			Mail email = emailService.enviarEmail(reserva.getUsuario(), emprestimo.getUnidadeLivro(), assuntoReservaDisponivel);

			emailService.sendSimpleMessage(email, templateReservaDisponivel);

		}
		Mail email = emailService.enviarEmail(emprestimo.getUsuario(), emprestimo.getUnidadeLivro(), assunto);

		emailService.sendSimpleMessage(email, template);

		return new ModelAndView("redirect:/em"
				+ "prestimos");
	}

	/* *********************************************************************************************
	 * E-mail de notificação de prazo de entrega do livro
	 * Antes do prazo, o usuário receberá um e-mail de lembrete sobre o término do seu empréstimo
	 * Após o prazo, o usuário receberá um e-mail de atraso de entrega
	 ************************************************************************************************/
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

	
	   // Formatação da data
	public String formatarData(Date data) {
		String dataDev = "", mes = "", dia = "";
		dataDev = data.toString();
		mes = dataDev.substring(5, 7);
		dia = dataDev.substring(8, 10);
		dataDev = dia + "/" + mes;

		return dataDev;
	}
}
