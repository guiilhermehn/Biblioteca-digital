package com.cognizant.bibliotecadigital.service;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.cognizant.bibliotecadigital.model.CadastraUsuarioDTO;
import com.cognizant.bibliotecadigital.model.Role;
import com.cognizant.bibliotecadigital.model.Usuario;
import com.cognizant.bibliotecadigital.repository.UsuarioRepository;

@Service
public class UsuarioService implements UsuarioServiceInterface {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
    private BCryptPasswordEncoder codificadorSenha;
	
	public Iterable<Usuario> findAll() {
		return usuarioRepository.findAll();
	}

	public Optional<Usuario> findById(Long id) {
		return usuarioRepository.findById(id);
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Usuario user = usuarioRepository.findByEmail(email);
        if (user == null){
            throw new UsernameNotFoundException("Nome ou senha Invalida");
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(),
                user.getSenha(),
                mapRolesToAuthorities(user.getRoles()));
	}

	@Override
	public Usuario findByEmail(String email) {
		return usuarioRepository.findByEmail(email);
	}

	@Override
	public Usuario save(CadastraUsuarioDTO registrar) {
		 Usuario usuario = new Usuario();
	        usuario.setNome(registrar.getNome());
	        usuario.setGrade(registrar.getGrade());
	        usuario.setEmail(registrar.getEmail());
	        usuario.setSenha(codificadorSenha.encode(registrar.getSenha()));
	        usuario.setRoles(Arrays.asList(new Role("ROLE_USER")));
	        return usuarioRepository.save(usuario);
	}
	
	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles){
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getNome()))
                .collect(Collectors.toList());
    }
}
