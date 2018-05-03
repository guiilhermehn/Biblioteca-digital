package com.cognizant.bibliotecadigital.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cognizant.bibliotecadigital.model.Emprestimo;
import com.cognizant.bibliotecadigital.repository.EmprestimoRepository;

@Service
public class EmprestimoService {

	@Autowired
	private EmprestimoRepository emprestimoRepository;

	public Iterable<Emprestimo> findAll() {
		return emprestimoRepository.findAll();
	}
	

	public Emprestimo save(Emprestimo emprestimo) {
		return emprestimoRepository.save(emprestimo);
	}

	public Long deleteById(Long id) {
		emprestimoRepository.deleteById(id);
		return id;
	}

	
}
