package com.cognizant.bibliotecadigital.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.cognizant.bibliotecadigital.model.Emprestimo;

@Repository
public interface EmprestimoRepository extends CrudRepository<Emprestimo, Long> {
	@Query(value = "SELECT count(*) FROM emprestimo WHERE unidade_livro_id = ?1 AND data_devolucao IS NULL", nativeQuery = true)
	long countEmprestimosByUnidadeLivroId(Long unidadeLivroId);
	
	@Query(value = "SELECT * FROM emprestimo WHERE unidade_livro_id = ?1 AND data_devolucao IS NULL", nativeQuery = true)
	Optional<Emprestimo> findEmprestimosByUnidadeLivroId(Long unidadeLivroId);
	
	Iterable<Emprestimo> findAllByUsuarioId(Long usuarioId);
	
	@Query(value = " SELECT * from emprestimo WHERE unidade_livro_id IN(\r\n" + 
			" SELECT ul.livro_id FROM reserva AS r \r\n" + 
			" JOIN livro AS l ON l.id = r.livro_id \r\n" + 
			" JOIN unidade_livro AS ul ON ul.livro_id = l.id WHERE r.id = ?)", nativeQuery = true)
	Emprestimo findEmprestimoByReservaId(Long reservaId);
}
