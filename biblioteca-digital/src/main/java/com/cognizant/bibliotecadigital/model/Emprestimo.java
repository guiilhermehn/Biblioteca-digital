package com.cognizant.bibliotecadigital.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;

@Entity
@Table(name = "emprestimo")
public class Emprestimo implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
	@Column(name = "data_retirada")
	@NotNull
	private Date dataRetirada;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
	@Column(name = "data_devolucao")
	private Date dataDevolucao;


	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
	@Column(name = "prazo_devolucao")
	@NotNull
	private Date prazoDevolucao;

	@ManyToOne(targetEntity = UnidadeLivro.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "unidade_livro_id")
	private UnidadeLivro unidadeLivro;

	@ManyToOne
	@JoinColumn(name = "usuario_id")
	private Usuario usuario;
	
	private boolean habilita;

	// construtor
	public Emprestimo() {

	}

	public Emprestimo(Long id, @NotNull Date dataRetirada, @Nullable Date dataDevolucao, @NotNull Date prazoDevolucao,
			UnidadeLivro unidadeLivro, Usuario usuario) {
		this.id = id;
		this.dataRetirada = dataRetirada;
		this.dataDevolucao = dataDevolucao;
		this.prazoDevolucao = prazoDevolucao;
		this.unidadeLivro = unidadeLivro;
		this.usuario = usuario;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dataDevolucao == null) ? 0 : dataDevolucao.hashCode());
		result = prime * result + ((dataRetirada == null) ? 0 : dataRetirada.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((prazoDevolucao == null) ? 0 : prazoDevolucao.hashCode());
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
		Emprestimo other = (Emprestimo) obj;
		if (dataDevolucao == null) {
			if (other.dataDevolucao != null)
				return false;
		} else if (!dataDevolucao.equals(other.dataDevolucao))
			return false;
		if (dataRetirada == null) {
			if (other.dataRetirada != null)
				return false;
		} else if (!dataRetirada.equals(other.dataRetirada))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (prazoDevolucao == null) {
			if (other.prazoDevolucao != null)
				return false;
		} else if (!prazoDevolucao.equals(other.prazoDevolucao))
			return false;
		return true;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDataRetirada() {
		return dataRetirada;
	}

	public void setDataRetirada(Date dataRetirada) {
		this.dataRetirada = dataRetirada;
	}

	public Date getDataDevolucao() {
		return dataDevolucao;
	}

	public void setDataDevolucao(Date dataDevolucao) {
		this.dataDevolucao = dataDevolucao;
	}

	public Date getPrazoDevolucao() {
		return prazoDevolucao;
	}

	public void setPrazoDevolucao(Date prazoDevolucao) {
		this.prazoDevolucao = prazoDevolucao;
	}
	
	

	public UnidadeLivro getUnidadeLivro() {
		return unidadeLivro;
	}

	public void setUnidadeLivro(UnidadeLivro unidadeLivro) {
		this.unidadeLivro = unidadeLivro;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public boolean isHabilita() {
		return habilita;
	}

	public void setHabilita(boolean habilita) {
		this.habilita = habilita;
	}

	@Override
	public String toString() {
		return "Emprestimo [id=" + id + ", dataRetirada=" + dataRetirada + ", dataDevolucao=" + dataDevolucao
				+ ", prazoDevolucao=" + prazoDevolucao + "]";
	}
}
