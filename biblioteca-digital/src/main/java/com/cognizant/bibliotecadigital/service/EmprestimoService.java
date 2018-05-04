package com.cognizant.bibliotecadigital.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cognizant.bibliotecadigital.model.Emprestimo;
import com.cognizant.bibliotecadigital.model.Livro;
import com.cognizant.bibliotecadigital.model.Usuario;
import com.cognizant.bibliotecadigital.repository.EmprestimoRepository;

@Service
public class EmprestimoService {

	@Autowired
	private EmprestimoRepository emprestimoRepository;

	public Iterable<Emprestimo> findAll() {
		return emprestimoRepository.findAll();
	}
	
	public Optional<Emprestimo> findById(Long id) {
		return emprestimoRepository.findById(id);
	}

	public Emprestimo save(Emprestimo emprestimo) {
		return emprestimoRepository.save(emprestimo);
	}

	public Long deleteById(Long id) {
		emprestimoRepository.deleteById(id);
		return id;
	}


	public Optional<Emprestimo> findById(Long id) {
		return emprestimoRepository.findById(id);
	}

	public boolean isEmprestado(Long unidadeLivroId) {
		return emprestimoRepository.countEmprestimosByUnidadeLivroId(unidadeLivroId) > 0L;
	}
	
	public Optional<Emprestimo> findByUnidadeLivroId(Long unidadeId) {
		return emprestimoRepository.findEmprestimosByUnidadeLivroId(unidadeId);
	}
	
	public Iterable<Emprestimo> findAllByUsuarioId(Long usuarioId) {
		return emprestimoRepository.findAllByUsuarioId(usuarioId);
	}
}

