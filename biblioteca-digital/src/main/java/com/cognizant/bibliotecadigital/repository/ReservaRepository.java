
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
	@Query(value="select r.id from emprestimo e \r\n" + 
			" join unidade_livro ul on e.unidade_livro_id = ul.id\r\n" + 
			" join livro l on ul.livro_id = l.id\r\n" + 
			" join reserva r on r.livro_id = l.id\r\n" + 
			" where e.unidade_livro_id = ? \r\n" + 
			" and data_reserva in( select min(data_reserva) from reserva  where e.unidade_livro_id = e.unidade_livro_id);",nativeQuery= true)
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
	@Query(value=" select COUNT(*) from reserva where livro_id = ? and status = 'AGUARDANDO'",nativeQuery = true)
	long countReservaAguardandoPorUnidadeId(Long id);

	@Query(value=" select COUNT(*) from reserva where livro_id = ? ",nativeQuery = true)
	long countReservaPorLivro(Long livroId);
}