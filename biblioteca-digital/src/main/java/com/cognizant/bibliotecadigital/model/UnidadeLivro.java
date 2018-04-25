package com.cognizant.bibliotecadigital.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="unidadeLivro")
public class UnidadeLivro implements Serializable{

	private static final long serialversionUID = 1L;
	
	@Id
	@GeneratedValue
	private long id;
	
	@Column(name="avarias")
	private String avarias;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((avarias == null) ? 0 : avarias.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
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
		UnidadeLivro other = (UnidadeLivro) obj;
		if (avarias == null) {
			if (other.avarias != null)
				return false;
		} else if (!avarias.equals(other.avarias))
			return false;
		if (id != other.id)
			return false;
		return true;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getAvarias() {
		return avarias;
	}

	@Override
	public String toString() {
		return "UnidadeLivro [id=" + id + ", avarias=" + avarias + "]";
	}
	
}
