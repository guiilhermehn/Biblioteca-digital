package com.cognizant.bibliotecadigital.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cognizant.bibliotecadigital.model.Papel;
import com.cognizant.bibliotecadigital.repository.PapelRepository;

@Service
public class PapelService {
	
	@Autowired
	private PapelRepository papelRepository;
	
	public Optional<Papel> findByNome(String nome) {
		return papelRepository.findByNome(nome);
	}
}
