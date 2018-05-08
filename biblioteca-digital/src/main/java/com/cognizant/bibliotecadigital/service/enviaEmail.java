package com.cognizant.bibliotecadigital.service;

import java.util.List;

import com.cognizant.bibliotecadigital.model.Emprestimo;
import com.cognizant.bibliotecadigital.repository.EmprestimoRepository;

public class enviaEmail extends Thread {
	EmprestimoRepository emprestimoRepository;
	
	public void run() {
//		List<Emprestimo> emprestimos = (List<Emprestimo>) emprestimoRepository.prazoDevolucao();
//		
//		String email = "", nome = "", livro = "", data = "";
//		for(int i = 0;i<emprestimos.size();i++) {
//			nome = emprestimos.get(i).getUsuario().getNome().toString();
//			livro = emprestimos.get(i).getUnidadeLivro().getLivro().toString();
//			data = emprestimos.get(i).getPrazoDevolucao().toString();
//			System.out.println(nome);
//			System.out.println(email);
//			System.out.println(livro);
//		}
	}
}
