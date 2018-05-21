package com.cognizant.bibliotecadigital.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.cognizant.bibliotecadigital.model.Livro;

@Repository
public interface LivroRepository extends CrudRepository<Livro, Long> {

	Iterable<Livro> findByTituloContainingOrSinopseContainingOrAutorContaining(String titulo, String sinopse, String autor);
	
	Optional<Livro> findById(long id);
	
	@SuppressWarnings("unchecked")
	Livro save(Livro livro);
	
	Livro deleteById(long id);
	
	Livro findByIsbn13(String isbn13);
	
	@Query(value=" select count(*) from livro l  \r\n" + 
			" join unidade_livro ul\r\n" + 
			" on ul.livro_id = l.id \r\n" + 
			" join emprestimo e on ul.id = e.unidade_livro_id\r\n" + 
			" where e.usuario_id = ?",nativeQuery = true)
	Long findIdUsuarioComEmprestimo(Long idUsuario);
	
	
}
