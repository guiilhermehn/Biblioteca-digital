
package com.cognizant.bibliotecadigital.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.cognizant.bibliotecadigital.model.Reserva;

@Repository
public interface ReservaRepository extends CrudRepository<Reserva, Long> {
	// Faz a contagem de reservas que estão com status EM_ESPERA ou AGUARDANDO
	@Query(value = "SELECT count(*) FROM reserva WHERE livro_id = ? AND (status = 'EM_ESPERA' OR status = 'AGUARDANDO') and usuario_id = ?", nativeQuery = true)
	long countReservasByUnidadeLivroId(Long livroId, Long usuarioId);

	// Busca as unidades de livro reservadas
	@Query(value="select ul.id from reserva r join unidade_livro ul on r.livro_id = ul.livro_id where r.id = ?  ",nativeQuery = true)
	Long findUnidadeIdByReservaId(Long reservaId);
	
	// Busca a reserva mais antiga do livro
	@Query(value="select id from reserva where data_reserva in( select min(data_reserva) "
			+ "from reserva r where r.livro_id = ?) and status = 'EM_ESPERA'",nativeQuery= true)
	Long findReservaIdByEmprestimoId(Long id);

	// Faz a contagem de reservas com status "FINALIZADO"
	@Query(value="SELECT count(*) FROM reserva WHERE livro_id = ? AND status = 'FINALIZADO'",nativeQuery = true)
	Long countEmprestadoOuDevolvido(Long id);

	// Busca as reservas do usuário
	@Query(value=" select * from reserva where usuario_id = ?",nativeQuery = true)
	Iterable<Reserva> findAllByUsuarioId(Long id);

	// Faz a contagem de reservas por livro
	@Query(value="select count(*) from reserva r  where r.livro_id = ? and r.usuario_id = ?",nativeQuery=true)
	long countReservasPorIdLivro(Long id,Long usuarioId);

	// Faz a contagem de reservas com status "AGUARDANDO"
	@Query(value=" select COUNT(*) from reserva where livro_id = ? and status = 'AGUARDANDO' OR status = 'EM_ESPERA'",nativeQuery = true)
	long countReservaAguardandoPorUnidadeId(Long id);

	@Query(value=" select COUNT(*) from reserva where livro_id = ? and status ",nativeQuery = true)
	long countReservaPorLivro(Long livroId);
}