package com.cognizant.bibliotecadigital.service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import com.cognizant.bibliotecadigital.model.Emprestimo;
import com.cognizant.bibliotecadigital.model.Livro;
import com.cognizant.bibliotecadigital.model.Mail;
import com.cognizant.bibliotecadigital.model.StatusLivro;
import com.cognizant.bibliotecadigital.model.UnidadeLivro;
import com.cognizant.bibliotecadigital.model.Usuario;
import com.cognizant.bibliotecadigital.repository.EmailRepository;


@Service
public class EmailService {
	// Serviços chamados
	@Autowired
	private JavaMailSender emailSender;
	@Autowired
	private SpringTemplateEngine templateEngine;
	@Autowired
	private EmailRepository emailRepository;
	@Autowired
	private UsuarioService usuarioService;
	
	
	
	public Iterable<Emprestimo> prazoDevolucao(){
		return emailRepository.prazoDevolucao();
	}

	/* ***************************************************************************************
	 * Faz o envio do e-mail específico para o usuário, e envia uma cópia para o usuário Admin
	 *****************************************************************************************/
	@Async
	public void sendSimpleMessage(Mail mail, String template) throws MessagingException, IOException {
		MimeMessage message = emailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
				StandardCharsets.UTF_8.name());

		Context context = new Context();
		context.setVariables(mail.getModel());
		String html = templateEngine.process(template, context);
		

		Usuario adm = usuarioService.emailAdm().get();

		String reply = mail.getReplyTo() == "" ? "" : mail.getReplyTo();
		
		helper.setTo(mail.getTo());
		helper.setReplyTo(adm.getEmail());
		helper.setText(html, true);
		helper.setReplyTo(reply);
		helper.setSubject(mail.getSubject());
		helper.setFrom(mail.getFrom());

		emailSender.send(message);
	}

	/* ****************************************************************
	 * Constrói uma estrutura de e-mail  ao pegar um livro emprestado
	 * com os dados do empréstimo
	 ******************************************************************/
	public Mail enviarEmail(Usuario usuario,UnidadeLivro unidade, String assunto) {
		Mail mail = new Mail();
		
		Livro livro = unidade.getLivro();
		
		Usuario adm = usuarioService.emailAdm().get();

		mail.setFrom("no-reply@bibliotecacognizant.com");
		
		mail.setTo(usuario.getEmail()); 

		mail.setReplyTo(adm.getEmail());

		mail.setSubject(assunto);

		Map<String, Object> model = new HashMap<String, Object>();
		
		model.put("name", usuario.getNome());
		model.put("livro", unidade.getLivro().getTitulo().toString());
		model.put("location", "Brasil");
		if (livro.getStatusLivro().equals(StatusLivro.EM_ANALISE)) {
			model.put("ADM", "Revisado por: " + adm.getNome());
		} else {
			model.put("ADM", "");
		}
		mail.setModel(model);

		return mail;
	}
	
	/* ***************************************************************
	 * Constrói uma estrutura de e-mail para o avisar o usuário
	 * que seu empréstimo está próximo do fim ou já passou do prazo
	 *****************************************************************/
	public Mail lembreteDevolucao(Usuario usuario, String livro, String data) {
		Mail mail = new Mail();
		Usuario adm = usuarioService.emailAdm().get();
		
		mail.setFrom("noreply.digitallibrary@gmail.com");
		mail.setTo(usuario.getEmail());
		mail.setReplyTo(adm.getEmail());
		mail.setSubject("Lembrete de Devolução: " + livro);
		
		Map<String, Object> model = new HashMap<String, Object>();
		
		model.put("name", usuario.getNome());
		model.put("livro", livro);
		model.put("prazo", data);
		mail.setModel(model);
		
		return mail;
	}
}
