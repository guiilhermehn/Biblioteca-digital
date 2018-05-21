package com.cognizant.bibliotecadigital.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.cognizant.bibliotecadigital.model.Emprestimo;

@Repository
public interface EmailRepository extends CrudRepository<Emprestimo, Long> {
	@Query(value = "SELECT e.*, u.*, l.*, ul.* FROM emprestimo e\r\n" + 
			"join usuario u\n" + 
			"on u.id = e.usuario_id\n" + 
			"join unidade_livro ul\r\n" + 
			"on ul.id = e.unidade_livro_id\n" + 
			"join livro l\n" + 
			"on l.id = ul.livro_id\n"
			+ "WHERE data_devolucao IS NULL and (prazo_devolucao - current_date()) <= 3", nativeQuery = true)
	Iterable<Emprestimo> prazoDevolucao();
}
