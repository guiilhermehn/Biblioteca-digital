package com.cognizant.bibliotecadigital;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.cognizant.bibliotecadigital.model.CategoriaLivro;


@Repository
public interface CategoriaLivroRepository extends CrudRepository<CategoriaLivro, Long> {

}
