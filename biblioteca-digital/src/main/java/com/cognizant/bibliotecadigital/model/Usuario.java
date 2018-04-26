package com.cognizant.bibliotecadigital.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="usuario")
public class Usuario implements Serializable{

	private static final long serialVersionUID = 902783495L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
		
	@Column(name="id_cgz")
	@NotNull
	private Long idCgz;
	
	@Column(name="nome")
	@NotNull
	private String name;
	
	@Column(name="email")
	@NotNull
	private String email;
	
	@Column(name="grade", nullable=false)
	@NotNull
	private String grade;
	
	@Column(name="horizontal")
	private String horizontal;
	
	@Column(name="vertical")
	private String vertical;
	
	@Column(name="senha")
	@NotNull
	private String senha;
	
	//Joins com emprestimo e reserva
	
	 @OneToMany(mappedBy = "usuario", targetEntity = Emprestimo.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	 private List<Emprestimo> emprestimos;
	 
	 @OneToMany(mappedBy = "usuario", targetEntity = Reserva.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	 private List<Reserva> reservas;
	
	 @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((grade == null) ? 0 : grade.hashCode());
		result = prime * result + ((horizontal == null) ? 0 : horizontal.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + (int) (idCgz ^ (idCgz >>> 32));
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((senha == null) ? 0 : senha.hashCode());
		result = prime * result + ((vertical == null) ? 0 : vertical.hashCode());
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
		Usuario other = (Usuario) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (grade == null) {
			if (other.grade != null)
				return false;
		} else if (!grade.equals(other.grade))
			return false;
		if (horizontal == null) {
			if (other.horizontal != null)
				return false;
		} else if (!horizontal.equals(other.horizontal))
			return false;
		if (id != other.id)
			return false;
		if (idCgz != other.idCgz)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (senha == null) {
			if (other.senha != null)
				return false;
		} else if (!senha.equals(other.senha))
			return false;
		if (vertical == null) {
			if (other.vertical != null)
				return false;
		} else if (!vertical.equals(other.vertical))
			return false;
		return true;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdCgz() {
		return idCgz;
	}

	public void setIdCgz(Long idCgz) {
		this.idCgz = idCgz;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getHorizontal() {
		return horizontal;
	}

	public void setHorizontal(String horizontal) {
		this.horizontal = horizontal;
	}

	public String getVertical() {
		return vertical;
	}

	public void setVertical(String vertical) {
		this.vertical = vertical;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public List<Emprestimo> getEmprestimos() {
		return emprestimos;
	}

	public void setEmprestimos(List<Emprestimo> emprestimos) {
		this.emprestimos = emprestimos;
	}

	public List<Reserva> getReservas() {
		return reservas;
	}

	public void setReservas(List<Reserva> reservas) {
		this.reservas = reservas;
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", idCgz=" + idCgz + ", name=" + name + ", email=" + email + ", grade=" + grade
				+ ", horizontal=" + horizontal + ", vertical=" + vertical + ", senha=" + senha + ", emprestimos="
				+ emprestimos + ", reservas=" + reservas + "]";
	}
	
	
	
}
