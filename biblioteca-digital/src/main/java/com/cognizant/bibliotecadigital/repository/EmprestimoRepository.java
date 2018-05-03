package com.cognizant.bibliotecadigital.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.cognizant.bibliotecadigital.model.Emprestimo;

@Repository
public interface EmprestimoRepository extends CrudRepository<Emprestimo, Long> {
	
	
	
}
