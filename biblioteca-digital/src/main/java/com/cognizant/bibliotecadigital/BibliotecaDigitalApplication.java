package com.cognizant.bibliotecadigital;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.cognizant.bibliotecadigital.repository.AutorRepository;
import com.cognizant.bibliotecadigital.repository.CategoriaLivroRepository;
import com.cognizant.bibliotecadigital.repository.LivroRepository;

@SpringBootApplication
public class BibliotecaDigitalApplication {

	public static void main(String[] args) {
		SpringApplication.run(BibliotecaDigitalApplication.class, args);
	}
	
	//Bloco somente deve ser executado para popular o banco numa primeira execução
	/*
	@Bean
	public CommandLineRunner mock(CategoriaLivroRepository catRepo, AutorRepository autRepo, LivroRepository livRepo) {
		return (String[] args) -> {
			
			catRepo.save(new CategoriaLivro(0L, "categoria1"));
			catRepo.save(new CategoriaLivro(0L, "categoria2"));
			
			autRepo.save(new Autor(0L, "autor1"));
			autRepo.save(new Autor(0L, "autor2"));
			autRepo.save(new Autor(0L, "autor3"));
			
			Livro livro = new Livro(0L, "isbn13", "titulo", 2000,
					2, "sinopse", "foto", new HashSet<>(),
					new HashSet<>(), new ArrayList<>(),
					new ArrayList<>());
			
			livro.getCategoriaLivros().add(catRepo.findById(1L).get());
			livro.getCategoriaLivros().add(catRepo.findById(2L).get());
			
			livro.getAutores().add(autRepo.findById(1L).get());
			livro.getAutores().add(autRepo.findById(2L).get());
			livro.getAutores().add(autRepo.findById(3L).get());
			
			livro.getUnidadeLivros().add(new UnidadeLivro());
			
			livRepo.save(livro);
			
		};
	}*/
}
