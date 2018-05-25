
package com.cognizant.bibliotecadigital.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.cognizant.bibliotecadigital.model.Usuario;

@Repository
public interface UsuarioRepository extends CrudRepository<Usuario, Long> {
	Optional<Usuario> findByEmail(String email);

	// Faz a busca dos usuários com papel "ROLE ADMIN"
	@Query(value = "select * from usuario u\r\n" + "join usuario_papel up on u.id = up.usuario_id\r\n"
			+ "join papel p on p.id = up.papel_id where p.nome = 'ROLE_ADMIN'", nativeQuery = true)
	Optional<Usuario> emailAdm();
}