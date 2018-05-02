package com.cognizant.bibliotecadigital.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cognizant.bibliotecadigital.model.Livro;
import com.cognizant.bibliotecadigital.repository.LivroRepository;

@Service
public class LivroService {

	@Autowired
	private LivroRepository livroRepository;
	
	public Iterable<Livro> findAll() {
		return livroRepository.findAll();
	}
	
	public Optional<Livro> findById(long id) {
		return livroRepository.findById(id);
	}
	
	public Livro save(Livro livro) {
		return livroRepository.save(livro);
	}
	
	public Livro deleteById(long id) {
		return livroRepository.deleteById(id);
	}
}