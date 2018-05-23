
package com.cognizant.bibliotecadigital.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cognizant.bibliotecadigital.model.Reserva;
import com.cognizant.bibliotecadigital.repository.ReservaRepository;

@Service
public class ReservaService {

	@Autowired
	private ReservaRepository reservaRepository;

	public Reserva save(Reserva reserva) {
		return reservaRepository.save(reserva);
	}

	public Iterable<Reserva> findAll() {
		return reservaRepository.findAll();
	}

	public Optional<Reserva> findById(Long id) {
		return reservaRepository.findById(id);
	}

	public Long deleteById(Long id) {
		reservaRepository.deleteById(id);
		return id;
	}

	public boolean isReservado(Long livroId, Long usuarioId) {
		return reservaRepository.countReservasByUnidadeLivroId(livroId, usuarioId) > 0L;
	}

	public Long findUnidadeIdByReservaId(Long reservaId) {

		return reservaRepository.findUnidadeIdByReservaId(reservaId);
	}

	public Long findReservaIdByEmprestimo(Long id) {

		return reservaRepository.findReservaIdByEmprestimoId(id);
	}

	public boolean isEmprestadoOuDevolvido(Long id) {

		return reservaRepository.countEmprestadoOuDevolvido(id) > 0L;
	}

	public Iterable<Reserva> findAllByUsuarioId(Long id) {

		return reservaRepository.findAllByUsuarioId(id);
	}

	public boolean countReservasPorIdLivro(Long id, Long usuarioId) {

		return reservaRepository.countReservasPorIdLivro(id, usuarioId) == 0L;
	}

	public boolean countReservaAguardandoPorUnidadeId(Long id) {

		return reservaRepository.countReservaAguardandoPorUnidadeId(id) == 0L;
	}

}

