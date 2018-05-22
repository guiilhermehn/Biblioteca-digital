package com.cognizant.bibliotecadigital.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.cognizant.bibliotecadigital.model.Autor;

@Repository
public interface AutorRepository extends CrudRepository<Autor, Long> {

}
