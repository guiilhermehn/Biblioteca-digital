package com.cognizant.bibliotecadigital.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.cognizant.bibliotecadigital.model.Reserva;

@Repository
public interface ReservaRepository extends CrudRepository<Reserva, Long> {
	@Query(value = "SELECT count(*) FROM reserva WHERE livro_id = ? AND status = 'AGUARDANDO'", nativeQuery = true)
	long countReservasByUnidadeLivroId(Long unidadeLivroId);
		
}
