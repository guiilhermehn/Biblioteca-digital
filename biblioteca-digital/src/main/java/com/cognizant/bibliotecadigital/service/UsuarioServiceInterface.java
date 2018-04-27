package com.cognizant.bibliotecadigital.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.cognizant.bibliotecadigital.model.Usuario;
import com.cognizant.bibliotecadigital.model.CadastraUsuarioDTO;

public interface UsuarioServiceInterface extends UserDetailsService {
	
	Usuario findByEmail(String email);
	
	Usuario save(CadastraUsuarioDTO usuarioDTO);
	}
