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
	
	@Query(value = "SELECT * FROM emprestimo WHERE data_devolucao IS NULL and (prazo_devolucao - current_date()) <= 3", nativeQuery = true)
	Iterable<Emprestimo> prazoDevolucao();
	
	Iterable<Emprestimo> findAllByUsuarioId(Long usuarioId);
}
