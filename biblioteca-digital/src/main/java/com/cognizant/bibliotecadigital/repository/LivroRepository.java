package com.cognizant.bibliotecadigital.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.cognizant.bibliotecadigital.model.Livro;

@Repository
public interface LivroRepository extends CrudRepository<Livro, Long> {

	// Query para a barra de pesquisa de livros, faz a busca por Titulo, Autor ou Descrição(sinopse)
	Iterable<Livro> findByTituloContainingOrSinopseContainingOrAutorContaining(String titulo, String sinopse, String autor);
	
	Optional<Livro> findById(long id);
	
	@SuppressWarnings("unchecked")
	Livro save(Livro livro);
	
	Livro deleteById(long id);
	
	Livro findByIsbn13(String isbn13);
	
	
	
	
}
