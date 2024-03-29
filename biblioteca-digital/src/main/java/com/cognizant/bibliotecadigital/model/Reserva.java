package com.cognizant.bibliotecadigital.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

@Transactional
@Entity
@Table(name = "Reserva")
public class Reserva implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "usuario_id")
	private Usuario usuario;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
	@Column(name = "data_reserva")
	private Date dataReserva;

	@Enumerated(EnumType.STRING)
	@Column(name = "status")
	private Status status;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	@Column(name = "data_modifica_status")
	private Date dataModificaStatus;

	@ManyToOne
	@JoinColumn(name = "livro_id")
	private Livro livro;

	@Transient
	private String dataPrevisao = "";

	@Transient
	private Boolean habilita = false;

	@Transient
	private Boolean habilitaApagarReserva = false;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Reserva() {

	}

	public Reserva(Usuario usuario, @NotNull Date dataReserva, @NotNull Status status, @NotNull Livro livro,
			@NotNull Date dataModificaStatus) {
		super();
		this.usuario = usuario;
		this.dataReserva = dataReserva;
		this.status = status;
		this.livro = livro;
		this.dataModificaStatus = dataModificaStatus;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dataReserva == null) ? 0 : dataReserva.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((livro == null) ? 0 : livro.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((usuario == null) ? 0 : usuario.hashCode());
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
		Reserva other = (Reserva) obj;
		if (dataReserva == null) {
			if (other.dataReserva != null)
				return false;
		} else if (!dataReserva.equals(other.dataReserva))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (livro == null) {
			if (other.livro != null)
				return false;
		} else if (!livro.equals(other.livro))
			return false;
		if (status != other.status)
			return false;
		if (usuario == null) {
			if (other.usuario != null)
				return false;
		} else if (!usuario.equals(other.usuario))
			return false;
		return true;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Date getDataReserva() {
		return dataReserva;
	}

	public void setDataReserva(Date dataReserva) {
		this.dataReserva = dataReserva;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Livro getLivro() {
		return livro;
	}

	public void setLivro(Livro livro) {
		this.livro = livro;
	}

	public String getDataPrevisao() {
		return dataPrevisao;
	}

	public void setDataPrevisao(String dataPrevisao) {
		this.dataPrevisao = dataPrevisao;
	}

	public Boolean getHabilita() {
		return habilita;
	}

	public void setHabilita(Boolean habilita) {
		this.habilita = habilita;
	}

	public Date getDataModificaStatus() {
		return dataModificaStatus;
	}

	public void setDataModificaStatus(Date dataModificaStatus) {
		this.dataModificaStatus = dataModificaStatus;
	}
	
	

	public Boolean getHabilitaApagarReserva() {
		return habilitaApagarReserva;
	}

	public void setHabilitaApagarReserva(Boolean habilitaApagarReserva) {
		this.habilitaApagarReserva = habilitaApagarReserva;
	}

	@Override
	public String toString() {
		return "Reserva [id=" + id + ", usuario=" + usuario + ", dataReserva=" + dataReserva + ", status=" + status
				+ ", livro=" + livro + "]";
	}

	public boolean isValidaStatusEmEspera(Reserva reserva, Emprestimo emprestimo) {
		if (reserva.getStatus().equals(Status.EM_ESPERA) && emprestimo.getDataDevolucao() != null) {
			return true;
		}
		return false;
	}

}
