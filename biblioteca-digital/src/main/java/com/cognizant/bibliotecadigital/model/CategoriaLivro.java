package com.cognizant.bibliotecadigital.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
/* ******************************
 * AINDA NÃO IMPLEMENTADO
 ********************************/
@Entity
@Table(name = "categoria_livro")
public class CategoriaLivro implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long idCategoria;

	@Column(name = "nome_categoria")
	private String nomeCategoria;

	@ManyToMany(mappedBy = "categoriaLivros", fetch = FetchType.LAZY)
	Set<Livro> livros;

	// Construtor
	public CategoriaLivro() {
		this.idCategoria = 0L;
		this.livros = new HashSet<Livro>();
	}

	
	
	public CategoriaLivro(Long idCategoria, String nomeCategoria) {
		this.idCategoria = idCategoria;
		this.nomeCategoria = nomeCategoria;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (idCategoria ^ (idCategoria >>> 32));
		result = prime * result + ((nomeCategoria == null) ? 0 : nomeCategoria.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CategoriaLivro other = (CategoriaLivro) obj;
		if (idCategoria != other.idCategoria)
			return false;
		if (nomeCategoria == null) {
			if (other.nomeCategoria != null)
				return false;
		} else if (!nomeCategoria.equals(other.nomeCategoria))
			return false;
		return true;
	}

	public Long getIdCat() {
		return idCategoria;
	}

	public void setIdCat(Long idCat) {
		this.idCategoria = idCat;
	}

	public String getNomeCat() {
		return nomeCategoria;
	}

	public void setNomeCat(String nomeCat) {
		this.nomeCategoria = nomeCat;
	}

	public Set<Livro> getLivros() {
		return livros;
	}

	public void setLivros(Set<Livro> livros) {
		this.livros = livros;
	}

	public void setIdCat(long idCat) {
		this.idCategoria = idCat;
	}

	@Override
	public String toString() {
		return "categoriaLivro [idCat=" + idCategoria + ", nomeCat=" + nomeCategoria + "]";
	}

}
