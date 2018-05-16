package com.cognizant.bibliotecadigital.repository;

import org.springframework.data.repository.CrudRepository;

import com.cognizant.bibliotecadigital.model.Papel;

public interface PapelRepository extends CrudRepository<Papel, Long> {
	
	Long findByNome(String nome);
}
