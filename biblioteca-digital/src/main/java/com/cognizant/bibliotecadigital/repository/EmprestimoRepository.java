package com.cognizant.bibliotecadigital.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.cognizant.bibliotecadigital.model.Emprestimo;

@Repository
public interface EmprestimoRepository extends CrudRepository<Emprestimo, Long> {

	// Faz a contagem com empréstimos em andamento
	@Query(value = "SELECT count(*) FROM emprestimo e WHERE  e.unidade_livro_id = ? && e.data_devolucao is null", nativeQuery = true)
	long countEmprestimosByUsuarioId(Long unidadeLivroId);

	Iterable<Emprestimo> findAllByUsuarioId(Long usuarioId);

	// Faz a busca de livros emprestados que estão reservados
	@Query(value = " SELECT * from emprestimo WHERE unidade_livro_id IN(\r\n"
			+ " SELECT ul.livro_id FROM reserva AS r \r\n" + " JOIN livro AS l ON l.id = r.livro_id \r\n"
			+ " JOIN unidade_livro AS ul ON ul.livro_id = l.id WHERE r.id = ?) ", nativeQuery = true)
	Iterable<Emprestimo> findEmprestimoByReservaId(Long reservaId);

	// Faz a contagem de livros emprestados para o usuário
	@Query(value = "select count(*) from livro l  \r\n" + " join unidade_livro ul\r\n" + " on ul.livro_id = l.id \r\n"
			+ " join emprestimo e on ul.id = e.unidade_livro_id\r\n"
			+ " where  e.usuario_id = ? and e.data_devolucao is null OR l.status_livro = 2", nativeQuery = true)
	long countEmprestimoPorUsuarioId(Long id);

}
