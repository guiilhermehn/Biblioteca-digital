
package com.cognizant.bibliotecadigital;

import java.util.concurrent.Executor;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.cognizant.bibliotecadigital.model.Papel;
import com.cognizant.bibliotecadigital.repository.PapelRepository;

@SpringBootApplication
@EnableScheduling
@EnableAsync
public class BibliotecaDigitalApplication extends SpringBootServletInitializer implements AsyncConfigurer  {
	
	
	/* ****************************************************
	 * Necessario para o spring rodar em Servidores como JBOSS,WILDFLY
	 ******************************************************/
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(BibliotecaDigitalApplication.class);
    }
	
	public static void main(String[] args) {
		SpringApplication.run(BibliotecaDigitalApplication.class, args);
	}
	

	/* ****************************************************
	 * Envio de email Assincrono
	 ******************************************************/

	@Override
	public Executor getAsyncExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(2);
		executor.setMaxPoolSize(2);
		executor.setQueueCapacity(500);
		executor.setThreadNamePrefix("send-mailer-");
		executor.initialize();
		return executor;
	}
	
	/* ****************************************************
	 * Criacao de Papeis para usuarios
	 ******************************************************/

	@Bean
	public CommandLineRunner mock(PapelRepository papelRepository) {
		return (String[] args) -> {
			// NÃO APAGUE
			if (!papelRepository.existsByNome("ROLE_USUARIO")) {
				papelRepository.save(new Papel("ROLE_USUARIO"));
			}
			if (!papelRepository.existsByNome("ROLE_ADMIN")) {
				papelRepository.save(new Papel("ROLE_ADMIN"));
			}

		};
	}

}
