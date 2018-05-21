package com.cognizant.bibliotecadigital.model;

import java.io.Serializable;
import java.util.Collection;
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
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "usuario")
@Transactional
public class Usuario implements Serializable, UserDetails {

	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "id_cgz")
	@Size(min = 6, max = 6)
	@NotNull
	private String idCgz;

	@Column(name = "nome")
	@Size(min = 4, max = 80)
	@NotNull
	@NotEmpty
	private String nome;

	@Column(name = "email",unique = true)
	@Size(max=255)
	@Email
	@NotNull
	@NotEmpty
	private String email;

	@Column(name = "grade", nullable = false)
	@NotNull
	private String grade;

	@Column(name = "horizontal")
	private String horizontal;

	@Column(name = "vertical")
	private String vertical;

	@Column(name = "senha")
	@NotNull
	@NotEmpty
	private String senha;

	@Transient
	private String confirmaSenha;

	@Transient
	private Boolean verificaRole = false;

	@ManyToMany(cascade= {CascadeType.ALL}, fetch = FetchType.EAGER)
	@JoinTable(name = "usuario_papel", joinColumns = { @JoinColumn(name = "usuario_id", unique=true) }, inverseJoinColumns = {@JoinColumn(name = "papel_id") }) 
	public Set<Papel> papeis;

	// Joins com emprestimo e reserva

	@OneToMany(mappedBy = "usuario", targetEntity = Emprestimo.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Emprestimo> emprestimos;

	@OneToMany(mappedBy = "usuario", targetEntity = Reserva.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Reserva> reservas;

	// Construtor

	public Usuario(@NotNull String nome, @NotNull String email, @NotNull String grade, @NotNull String senha,
			Set<Papel> papeis) {
		super();
		this.nome = nome;
		this.email = email;
		this.grade = grade;
		this.senha = senha;
		this.papeis = papeis;
	}

	public Usuario() {
		this.id = 0L;
		this.papeis = new HashSet<>();
	}

	

	public Boolean getVerificaRole() {
		return verificaRole;
	}

	public void setVerificaRole(Boolean verificaRole) {
		this.verificaRole = verificaRole;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((emprestimos == null) ? 0 : emprestimos.hashCode());
		result = prime * result + ((grade == null) ? 0 : grade.hashCode());
		result = prime * result + ((horizontal == null) ? 0 : horizontal.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((idCgz == null) ? 0 : idCgz.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((papeis == null) ? 0 : papeis.hashCode());
		result = prime * result + ((reservas == null) ? 0 : reservas.hashCode());
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
		if (emprestimos == null) {
			if (other.emprestimos != null)
				return false;
		} else if (!emprestimos.equals(other.emprestimos))
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
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (idCgz == null) {
			if (other.idCgz != null)
				return false;
		} else if (!idCgz.equals(other.idCgz))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (papeis == null) {
			if (other.papeis != null)
				return false;
		} else if (!papeis.equals(other.papeis))
			return false;
		if (reservas == null) {
			if (other.reservas != null)
				return false;
		} else if (!reservas.equals(other.reservas))
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

	public String getIdCgz() {
		return idCgz;
	}

	public void setIdCgz(String idCgz) {
		this.idCgz = idCgz;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
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

	public Set<Papel> getPapeis() {
		return papeis;
	}

	public void setPapeis(Set<Papel> papeis) {
		this.papeis = papeis;
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

	public String getConfirmaSenha() {
		return confirmaSenha;
	}

	public void setConfirmaSenha(String confirmaSenha) {
		this.confirmaSenha = confirmaSenha;
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", idCgz=" + idCgz + ", nome=" + nome + ", email=" + email + ", grade=" + grade
				+ ", horizontal=" + horizontal + ", vertical=" + vertical + ", senha=" + senha + ", papeis=" + papeis
				+ ", emprestimos=" + emprestimos + ", reservas=" + reservas + "]";
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		return null;
	}

	@Override
	public String getPassword() {
		
		return null;
	}

	@Override
	public String getUsername() {
		
		return null;
	}

	@Override
	public boolean isAccountNonExpired() {
		
		return false;
	}

	@Override
	public boolean isAccountNonLocked() {
		
		return false;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		
		return false;
	}

	@Override
	public boolean isEnabled() {
		
		return false;
	}

}
