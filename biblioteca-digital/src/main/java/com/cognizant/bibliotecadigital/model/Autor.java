package com.cognizant.bibliotecadigital.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "autor")
public class Autor implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long idAutor;

	@Column(name = "nome")
	private String nome;
	
	@Transient
	private List<String> nomeAutores;

	/*@ManyToMany(mappedBy = "autores", fetch = FetchType.LAZY)
	Set<Livro> livros = new HashSet<Livro>();
*/
	// Construtor
	public Autor() {
		super();
	}

	

	public Long getId() {
		return idAutor;
	}

	public void setId(Long id) {
		this.idAutor = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}


	@Override
	public String toString() {
		return "Autor [id=" + idAutor + ", nome=" + nome + "]";
	}

}
