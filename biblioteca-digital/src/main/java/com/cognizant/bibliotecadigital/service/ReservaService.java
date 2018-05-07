package com.cognizant.bibliotecadigital.service;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cognizant.bibliotecadigital.model.Emprestimo;
import com.cognizant.bibliotecadigital.model.Reserva;
import com.cognizant.bibliotecadigital.repository.EmprestimoRepository;
import com.cognizant.bibliotecadigital.repository.ReservaRepository;

@Service
public class ReservaService {

	@Autowired
	private ReservaRepository reservaRepository;
	
	@Autowired
	private EmprestimoRepository emprestimoRepository;
	
	public Reserva save(Reserva reserva) {
		return reservaRepository.save(reserva);
	}


	public Iterable<Reserva> findAll() {
		return reservaRepository.findAll();
	}

	public Object findById(Long id) {
		return reservaRepository.findById(id);
	}

	public Long deleteById(Long id) {
		reservaRepository.deleteById(id);
		return id;
	}

	public GregorianCalendar getDataDisponibilidade(Long livroId) {
		Date date;
		List<Emprestimo> result = (List<Emprestimo>) emprestimoRepository.findAllByUsuarioId(livroId);
		if(result.isEmpty()) {
			date = new Date();
		}else {
		 date = result.get(livroId.intValue()).getPrazoDevolucao();
		}
		GregorianCalendar data = new GregorianCalendar();
		data.setTime(date);
		
		
		return data;
	}


	public boolean isReservado(Long livroId) {
		return reservaRepository.countReservasByUnidadeLivroId(livroId)> 0L;
	}

	
		
		
	
	

	
}
