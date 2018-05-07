package com.cognizant.bibliotecadigital.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.cognizant.bibliotecadigital.model.Reserva;

@Repository
public interface ReservaRepository extends CrudRepository<Reserva, Long> {
	
		
}
