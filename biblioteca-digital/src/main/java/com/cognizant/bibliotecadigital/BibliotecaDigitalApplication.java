package com.cognizant.bibliotecadigital;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.scheduling.annotation.EnableScheduling;

import com.cognizant.bibliotecadigital.model.Papel;
import com.cognizant.bibliotecadigital.repository.PapelRepository;
import com.cognizant.bibliotecadigital.service.PapelService;




@SpringBootApplication
@EnableScheduling
public class BibliotecaDigitalApplication {

	public static void main(String[] args) {
		SpringApplication.run(BibliotecaDigitalApplication.class, args);
	}

	
	//Bloco somente deve ser executado para popular o banco numa primeira execução
	// CategoriaLivroRepository catRepo, AutorRepository autRepo, LivroRepository livRepo, UnidadeLivroRepository unidadeRepo EmailService email
	//
	@Bean
	public CommandLineRunner mock(PapelRepository papelRepository) {
		return (String[] args) -> {
			// NÃO APAGUE
			if(!papelRepository.existsByNome("ROLE_USUARIO")) {
				papelRepository.save(new Papel("ROLE_USUARIO"));
			}
			if(!papelRepository.existsByNome("ROLE_ADMIN")) {
				papelRepository.save(new Papel("ROLE_ADMIN"));
			}
			
			/*
			//Mail mail = email.lembreteDevolucao();
			//System.out.println(emprestimo.prazoDevolucao());
			//email.sendSimpleMessage(mail, "email-lembrete");
			/*
			catRepo.save(new CategoriaLivro(0L, "categoria1"));
			catRepo.save(new CategoriaLivro(0L, "categoria2"));
			
			autRepo.save(new Autor(0L, "autor1"));
			autRepo.save(new Autor(0L, "autor2"));
			autRepo.save(new Autor(0L, "autor3"));
			
			Livro livro = new Livro(0L, "1234567890123", "titulo", 2000,
					2, "sinopse", "foto", "Autor do livro",
					new HashSet<>(), new ArrayList<>(),
					new ArrayList<>());
			
			livro.getCategoriaLivros().add(catRepo.findById(1L).get());
			livro.getCategoriaLivros().add(catRepo.findById(2L).get());
			// livro.getUnidadeLivros().add(new UnidadeLivro());
			
			Livro salvo = livRepo.save(livro);
			unidadeRepo.save(new UnidadeLivro(0L, null, livRepo.findById(salvo.getId()).get()));
			*/
		};
		}
}
