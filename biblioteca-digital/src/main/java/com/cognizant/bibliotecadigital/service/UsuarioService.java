package com.cognizant.bibliotecadigital.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cognizant.bibliotecadigital.model.Usuario;
import com.cognizant.bibliotecadigital.repository.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	
	
	public Iterable<Usuario> findAll() {
		return usuarioRepository.findAll();
	}

	public Optional<Usuario> findById(Long id) {
		return usuarioRepository.findById(id);
	}


}
