package com.cognizant.bibliotecadigital.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="categoriaLivro")

public class CategoriaLivro implements Serializable{
private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idCat",unique=true)
	private long idCat;
	@Column(name="nomeCat")
	private String nomeCat;
		
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (idCat ^ (idCat >>> 32));
		result = prime * result + ((nomeCat == null) ? 0 : nomeCat.hashCode());
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
		if (idCat != other.idCat)
			return false;
		if (nomeCat == null) {
			if (other.nomeCat != null)
				return false;
		} else if (!nomeCat.equals(other.nomeCat))
			return false;
		return true;
	}

public long getIdCat() {
	return idCat;
}

public void setIdCat(int idCat) {
	this.idCat = idCat;
}

public String getNomeCat() {
	return nomeCat;
}

public void setNomeCat(String nomeCat) {
	this.nomeCat = nomeCat;
}

@Override
public String toString() {
	return "categoriaLivro [idCat=" + idCat + ", nomeCat=" + nomeCat + "]";
}


}
