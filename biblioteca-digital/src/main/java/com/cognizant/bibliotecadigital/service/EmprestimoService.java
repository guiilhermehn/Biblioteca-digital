package com.cognizant.bibliotecadigital.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cognizant.bibliotecadigital.model.Emprestimo;
<<<<<<< HEAD
=======
import com.cognizant.bibliotecadigital.model.Livro;
>>>>>>> master
import com.cognizant.bibliotecadigital.repository.EmprestimoRepository;

@Service
public class EmprestimoService {

	@Autowired
	private EmprestimoRepository emprestimoRepository;

	public Iterable<Emprestimo> findAll() {
		return emprestimoRepository.findAll();
	}
<<<<<<< HEAD

	public Object findById(Long id) {
		return emprestimoRepository.findById(id);
=======
	

	public Emprestimo save(Emprestimo emprestimo) {
		return emprestimoRepository.save(emprestimo);
>>>>>>> master
	}

	public Long deleteById(Long id) {
		emprestimoRepository.deleteById(id);
		return id;
	}

	
}
