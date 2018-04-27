package com.cognizant.bibliotecadigital.repository;


import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.cognizant.bibliotecadigital.model.Livro;

@Repository
public interface LivroRepository extends CrudRepository<Livro, Long>{

}
