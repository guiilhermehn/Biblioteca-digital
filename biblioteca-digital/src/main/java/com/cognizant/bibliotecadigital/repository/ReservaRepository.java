package com.cognizant.bibliotecadigital.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.cognizant.bibliotecadigital.model.Reserva;
import com.cognizant.bibliotecadigital.model.UnidadeLivro;

@Repository
public interface ReservaRepository extends CrudRepository<Reserva, Long> {
	@Query(value = "SELECT count(*) FROM reserva WHERE livro_id = ? AND status = 'EM_ESPERA'", nativeQuery = true)
	long countReservasByUnidadeLivroId(Long livroId);

	@Query(value="select * from unidade_livro where id in(select l.* from reserva r join unidade_livro l where r.id = ?)",nativeQuery = true)
	UnidadeLivro findUnidadeIdByReservaId(Long reservaId);
	
	
		
}
