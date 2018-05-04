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
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import com.cognizant.bibliotecadigital.model.Mail;
import com.cognizant.bibliotecadigital.model.UnidadeLivro;
import com.cognizant.bibliotecadigital.model.Usuario;

@Service
public class EmailService {

	@Autowired
	private JavaMailSender emailSender;

	@Autowired
	private SpringTemplateEngine templateEngine;

	public void sendSimpleMessage(Mail mail, String template) throws MessagingException, IOException {
		MimeMessage message = emailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
				StandardCharsets.UTF_8.name());

		helper.addAttachment("logo.png", new ClassPathResource("Logo_Cognizant.png"));

		Context context = new Context();
		context.setVariables(mail.getModel());
		String html = templateEngine.process(template, context);

		helper.setTo(mail.getTo());
		helper.setText(html, true);
		helper.setSubject(mail.getSubject());
		helper.setFrom(mail.getFrom());

		emailSender.send(message);
	}

	public Mail enviarEmail(Usuario usuario,UnidadeLivro unidade, String assunto) {
		Mail mail = new Mail();
		mail.setFrom("no-reply@bibliotecacognizant.com");
		mail.setTo(usuario.getEmail()); 
		mail.setSubject(assunto);

		Map<String, Object> model = new HashMap<String, Object>();
		
		model.put("name", usuario.getNome());
		model.put("livro", unidade.getLivro().getTitulo().toString());
		model.put("location", "Brasil");
		mail.setModel(model);

		return mail;
	}

}
