package com.cognizant.bibliotecadigital.service;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.stereotype.Service;

<<<<<<< HEAD
import com.cognizant.bibliotecadigital.model.Autor;
=======
import com.cognizant.bibliotecadigital.model.Papel;
>>>>>>> aa6887729be9552d8dd8fbb96ab71c6255b43848
import com.cognizant.bibliotecadigital.model.Usuario;
import com.cognizant.bibliotecadigital.repository.UsuarioRepository;
import com.cognizant.bibliotecadigital.security.SecurityConfig;

@Service
public class UsuarioService implements UserDetailsService {

	@Autowired
	private UsuarioRepository usuarioRepository;
<<<<<<< HEAD
	
<<<<<<< HEAD
	//Iury - Cadastrar usuário
	public Usuario save(Usuario usuario){
		return usuarioRepository.save(usuario);
	}
=======
>>>>>>> aa6887729be9552d8dd8fbb96ab71c6255b43848
	
=======

	public Usuario save(Usuario usuario) {
		return usuarioRepository.save(usuario);

	}

>>>>>>> philipe
	public Iterable<Usuario> findAll() {
		return usuarioRepository.findAll();
	}

	public Optional<Usuario> findById(Long id) {
		return usuarioRepository.findById(id);
	}

	private static final Map<String, Usuario> USUARIOS;

	static {
		USUARIOS = new LinkedHashMap<>();

		Usuario u1 = new Usuario("fulano", "fulano@fulano", "programador",
				SecurityConfig.bcryptPasswordEncoder().encode("abcd1234"),
				new LinkedHashSet<>(Arrays.asList(new Papel("ROLE_ADMIN"))));
		USUARIOS.put(u1.getEmail(), u1);

		Usuario u2 = new Usuario("ciclano", "ciclano@ciclano", "programador",
				SecurityConfig.bcryptPasswordEncoder().encode("abcd1234"),
				new LinkedHashSet<>(Arrays.asList(new Papel("ROLE_COMUM"))));
		USUARIOS.put(u2.getEmail(), u2);

	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		return usuarioRepository.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException("Usuario não Encontrado"));
	}

}
