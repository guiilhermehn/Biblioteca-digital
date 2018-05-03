package com.cognizant.bibliotecadigital.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.cognizant.bibliotecadigital.model.Livro;

@Repository
public interface LivroRepository extends CrudRepository<Livro, Long>{

	Optional<Livro> findById(long id);
	
	Livro save(Livro livro);
	
	Livro deleteById(long id);
	
	Livro findByIsbn13(String isbn13);
}
