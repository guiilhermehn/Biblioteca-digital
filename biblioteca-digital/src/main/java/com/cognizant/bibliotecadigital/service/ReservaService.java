package com.cognizant.bibliotecadigital.service;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cognizant.bibliotecadigital.model.Reserva;
import com.cognizant.bibliotecadigital.model.Status;
import com.cognizant.bibliotecadigital.model.UnidadeLivro;
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

	public boolean isReservado(Long livroId) {
		return reservaRepository.countReservasByUnidadeLivroId(livroId) > 0L;
	}



	public UnidadeLivro findUnidadeIdByReservaId(Long reservaId) {
		
		return reservaRepository.findUnidadeIdByReservaId(reservaId);
	}

}
