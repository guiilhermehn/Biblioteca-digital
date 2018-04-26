package com.cognizant.bibliotecadigital.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cognizant.bibliotecadigital.model.Reserva;
import com.cognizant.bibliotecadigital.repository.ReservaRepository;

@Service
public class ReservaService {

	@Autowired
	private ReservaRepository reservaRepository;

	public Iterable<Reserva> findAll() {
		return reservaRepository.findAll();
	}

}
