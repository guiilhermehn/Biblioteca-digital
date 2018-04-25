package com.cognizant.bibliotecadigital.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="emprestimo")
public class Emprestimo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id",nullable=false,updatable=false)
	private Long id;
	
	@Column(name="data_entrega", nullable=false)
	private Date dataEntrega;
	
	@Column(name="data_devolucao", nullable=false)
	private Date dataDevolucao;
	
	@Column(name="prazo_devolucao", nullable=false)
	private Date prazoDevolucao;
	

	 @ManyToOne
	 @JoinColumn(name="usuario_id")
	 private Usuario usuario;

	@Override
	public String toString() {
		return "Emprestimo [id=" + id + ", dataEntrega=" + dataEntrega
				+ ", dataDevolucao=" + dataDevolucao + ", prazoDevolucao="
				+ prazoDevolucao + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((dataDevolucao == null) ? 0 : dataDevolucao.hashCode());
		result = prime * result
				+ ((dataEntrega == null) ? 0 : dataEntrega.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((prazoDevolucao == null) ? 0 : prazoDevolucao.hashCode());
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
		if (dataEntrega == null) {
			if (other.dataEntrega != null)
				return false;
		} else if (!dataEntrega.equals(other.dataEntrega))
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

	public Date getDataEntrega() {
		return dataEntrega;
	}

	public void setDataEntrega(Date dataEntrega) {
		this.dataEntrega = dataEntrega;
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

	
}
