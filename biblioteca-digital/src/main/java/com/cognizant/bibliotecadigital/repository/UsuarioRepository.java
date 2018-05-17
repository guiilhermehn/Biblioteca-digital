package com.cognizant.bibliotecadigital.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.cognizant.bibliotecadigital.model.Usuario;

@Repository
public interface UsuarioRepository extends CrudRepository<Usuario, Long> {
	Optional<Usuario> findByEmail(String email);
	
	@Query(value="select u.id  from usuario u\r\n" + 
			"join usuario_papel up on u.id = up.usuario_id\r\n" + 
			"join papel p on p.id = up.papel_id where p.nome = 'ROLE_ADMIN'", nativeQuery = true)
	Long findIdUsuarioByRole();
	
	@Query(value= "select u.email from usuario u join usuario_papel up  join papel p where p.nome = 'ROLE_ADMIN'",nativeQuery = true)
	Optional<Usuario> emailAdm();
	
	

/*	@Query(value="insert into usuario_papel values(2,?)")
	Long registerRoleToUser(Long idUser);*/
}
