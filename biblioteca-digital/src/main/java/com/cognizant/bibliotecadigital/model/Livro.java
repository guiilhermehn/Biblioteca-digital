package com.cognizant.bibliotecadigital.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Transactional
@Entity
@Table(name = "livro")
public class Livro implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "isbn13", unique = true)
	@Size(min=8, max=13)
	private String isbn13;

	@NotNull
	@Size(min=4, max=255)
	@Column(name = "titulo")
	private String titulo;

	@Column(name = "ano_publicacao")
	private int anoPublicacao;

	@Column(name = "edicao")
	private int edicao;

	@Size(min=4, max=255)
	@Column(name = "sinopse")
	private String sinopse;

	@Column(name = "foto")
	private String foto;
	
	@Column(name="autor")
	private String autor;
	
	@Transient
	private boolean reservado;



	@ManyToMany(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
	@JoinTable(name = "livro_categoriaLivro", joinColumns = { @JoinColumn(name = "livro_id") }, inverseJoinColumns = {
			@JoinColumn(name = "categoriaLivro_id") })
	Set<CategoriaLivro> categoriaLivros = new HashSet<CategoriaLivro>();

	@Transient
	@OneToMany(mappedBy = "livro", targetEntity = Reserva.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Reserva> reservas;

	@OneToMany(mappedBy = "livro", targetEntity = UnidadeLivro.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<UnidadeLivro> unidadeLivros;

	
	// Construtor
	public Livro( ) {
		this.categoriaLivros = new HashSet<>();
		this.reservas = new ArrayList<>();
		this.unidadeLivros = new ArrayList<>();
	}

	
	// Joins com autor,categoriaLivro,reserva e unidadeLivro

	public Livro(Long id, String isbn13, String titulo, int anoPublicacao,
			int edicao, String sinopse, String foto, String autor,
			Set<CategoriaLivro> categoriaLivros, List<Reserva> reservas,
			List<UnidadeLivro> unidadeLivros) {
		this.id = id;
		this.isbn13 = isbn13;
		this.titulo = titulo;
		this.anoPublicacao = anoPublicacao;
		this.autor = autor;
		this.edicao = edicao;
		this.sinopse = sinopse;
		this.foto = foto;
		this.categoriaLivros = categoriaLivros;
		this.reservas = reservas;
		this.unidadeLivros = unidadeLivros;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + anoPublicacao;
		result = prime * result + ((autor == null) ? 0 : autor.hashCode());
		result = prime * result + ((categoriaLivros == null) ? 0 : categoriaLivros.hashCode());
		result = prime * result + edicao;
		result = prime * result + ((foto == null) ? 0 : foto.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((isbn13 == null) ? 0 : isbn13.hashCode());
		result = prime * result + ((reservas == null) ? 0 : reservas.hashCode());
		result = prime * result + ((sinopse == null) ? 0 : sinopse.hashCode());
		result = prime * result + ((titulo == null) ? 0 : titulo.hashCode());
		result = prime * result + ((unidadeLivros == null) ? 0 : unidadeLivros.hashCode());
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
		Livro other = (Livro) obj;
		if (anoPublicacao != other.anoPublicacao)
			return false;
		if (autor == null) {
			if (other.autor != null)
				return false;
		} else if (!autor.equals(other.autor))
			return false;
		if (categoriaLivros == null) {
			if (other.categoriaLivros != null)
				return false;
		} else if (!categoriaLivros.equals(other.categoriaLivros))
			return false;
		if (edicao != other.edicao)
			return false;
		if (foto == null) {
			if (other.foto != null)
				return false;
		} else if (!foto.equals(other.foto))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (isbn13 == null) {
			if (other.isbn13 != null)
				return false;
		} else if (!isbn13.equals(other.isbn13))
			return false;
		if (reservas == null) {
			if (other.reservas != null)
				return false;
		} else if (!reservas.equals(other.reservas))
			return false;
		if (sinopse == null) {
			if (other.sinopse != null)
				return false;
		} else if (!sinopse.equals(other.sinopse))
			return false;
		if (titulo == null) {
			if (other.titulo != null)
				return false;
		} else if (!titulo.equals(other.titulo))
			return false;
		if (unidadeLivros == null) {
			if (other.unidadeLivros != null)
				return false;
		} else if (!unidadeLivros.equals(other.unidadeLivros))
			return false;
		return true;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIsbn13() {
		return isbn13;
	}

	public void setIsbn13(String isbn13) {
		this.isbn13 = isbn13;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public int getAnoPublicacao() {
		return anoPublicacao;
	}

	public void setAnoPublicacao(int anoPublicacao) {
		this.anoPublicacao = anoPublicacao;
	}

	public int getEdicao() {
		return edicao;
	}

	public void setEdicao(int edicao) {
		this.edicao = edicao;
	}

	public String getSinopse() {
		return sinopse;
	}

	public void setSinopse(String sinopse) {
		this.sinopse = sinopse;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public Set<CategoriaLivro> getCategoriaLivros() {
		return categoriaLivros;
	}

	public void setCategoriaLivros(Set<CategoriaLivro> categoriaLivros) {
		this.categoriaLivros = categoriaLivros;
	}

	public List<Reserva> getReservas() {
		return reservas;
	}

	public void setReservas(List<Reserva> reservas) {
		this.reservas = reservas;
	}

	public List<UnidadeLivro> getUnidadeLivros() {
		return unidadeLivros;
	}

	public void setUnidadeLivros(List<UnidadeLivro> unidadeLivros) {
		this.unidadeLivros = unidadeLivros;
	}
	
	public String getAutor() {
		return autor;
	}


	public void setAutor(String autor) {
		this.autor = autor;
	}
	

	public boolean isReservado() {
		return reservado;
	}


	public void setReservado(boolean reservado) {
		this.reservado = reservado;
	}


	@Override
	public String toString() {
		return "Livro [id=" + id + ", isbn13=" + isbn13 + ", titulo=" + titulo + ", anoPublicacao=" + anoPublicacao
				+ ", edicao=" + edicao + ", sinopse=" + sinopse + ", foto=" + foto + "]";
	}

}
