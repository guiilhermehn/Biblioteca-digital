
package com.cognizant.bibliotecadigital.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.cognizant.bibliotecadigital.model.Reserva;

@Repository
public interface ReservaRepository extends CrudRepository<Reserva, Long> {
	@Query(value = "SELECT count(*) FROM reserva WHERE livro_id = ? AND (status = 'EM_ESPERA' OR status = 'AGUARDANDO') and usuario_id = ?", nativeQuery = true)
	long countReservasByUnidadeLivroId(Long livroId, Long usuarioId);

	@Query(value="select ul.id from reserva r join unidade_livro ul where r.id = ? ",nativeQuery = true)
	Long findUnidadeIdByReservaId(Long reservaId);
	
	@Query(value="select r.id from emprestimo e \r\n" + 
			" join unidade_livro ul \r\n" + 
			" join livro l \r\n" + 
			" join reserva r \r\n" + 
			" where e.unidade_livro_id = ?\r\n" + 
			" and data_reserva in( select min(data_reserva) from reserva  where e.unidade_livro_id = e.unidade_livro_id);",nativeQuery= true)
	Long findReservaIdByEmprestimoId(Long id);

	@Query(value="SELECT count(*) FROM reserva WHERE livro_id = ? AND status = 'FINALIZADO'",nativeQuery = true)
	Long countEmprestadoOuDevolvido(Long id);

	@Query(value=" select * from reserva where usuario_id = ?",nativeQuery = true)
	Iterable<Reserva> findAllByUsuarioId(Long id);

	@Query(value="select count(*) from reserva r  where r.livro_id = ? and r.usuario_id = ?",nativeQuery=true)
	long countReservasPorIdLivro(Long id,Long usuarioId);

	@Query(value=" select COUNT(*) from reserva where livro_id = ? and status = 'AGUARDANDO'",nativeQuery = true)
	long countReservaAguardandoPorUnidadeId(Long id);
	
	
		
}

