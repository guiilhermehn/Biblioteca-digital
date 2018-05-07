package com.cognizant.bibliotecadigital.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cognizant.bibliotecadigital.model.UnidadeLivro;
import com.cognizant.bibliotecadigital.repository.UnidadeLivroRepository;

@Service
public class UnidadeLivroService {
	@Autowired
	private UnidadeLivroRepository unidadeLivroRepository;
	
	public Optional<UnidadeLivro> findById(Long id) {
		return unidadeLivroRepository.findById(id);
	}
	
	public UnidadeLivro save(UnidadeLivro unidadeLivro) {
		return unidadeLivroRepository.save(unidadeLivro);
	}

	
}
