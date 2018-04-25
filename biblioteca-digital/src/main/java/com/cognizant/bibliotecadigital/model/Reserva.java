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

@Entity
@Table(name="Reserva")
public class Reserva implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	@Column(name="data_reserva")
	private Date data_reserva;
	
	@Enumerated(EnumType.STRING)
	@Column(name="status")
	private Status status;
	
	
	@ManyToOne
	@JoinColumn(name="livro_id")
	private Livro livro;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Reserva [id=" + id + ", data_rserva=" + data_reserva + ", status=" + status + "]";
	}

	public Date getData_rserva() {
		return data_reserva;
	}

	public void setData_rserva(Date data_rserva) {
		this.data_reserva = data_rserva;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
	
	

	public Date getData_reserva() {
		return data_reserva;
	}

	public void setData_reserva(Date data_reserva) {
		this.data_reserva = data_reserva;
	}

	public Livro getLivro() {
		return livro;
	}

	public void setLivro(Livro livro) {
		this.livro = livro;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((data_reserva == null) ? 0 : data_reserva.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
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
		if (data_reserva == null) {
			if (other.data_reserva != null)
				return false;
		} else if (!data_reserva.equals(other.data_reserva))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (status != other.status)
			return false;
		return true;
	}
	
	
	
	
}
