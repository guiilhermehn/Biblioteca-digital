package com.cognizant.bibliotecadigital.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cognizant.bibliotecadigital.model.Autor;
import com.cognizant.bibliotecadigital.repository.AutorRepository;

@Service
public class AutorService {
	
	@Autowired
	private AutorRepository autorRepository;
	
	public Autor save(Autor autor){
		return autorRepository.save(autor);
	}

	public Iterable <Autor> findAll() {
		return autorRepository.findAll();
	}

	public Long deleteById(long id) {
		autorRepository.deleteById(id);
		return id;
	}
}