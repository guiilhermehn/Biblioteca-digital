package com.cognizant.bibliotecadigital.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.cognizant.bibliotecadigital.model.Papel;

public interface PapelRepository extends CrudRepository<Papel, Long> {
	
	Optional<Papel> findByNome(String nome);
}
