package com.cognizant.bibliotecadigital.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import com.cognizant.bibliotecadigital.validator.FieldMatch;

@FieldMatch.List({ @FieldMatch(primeiro = "senha", segundo = "confirmaSenha", message = "Os campos devem ser iguais"),
		@FieldMatch(primeiro = "email", segundo = "confirmaEmail", message = "Os campos devem ser iguais") })
public class CadastraUsuarioDTO {

	@NotEmpty
	private String nome;

	@NotEmpty
	private String grade;

	@NotEmpty
	private String senha;

	@NotEmpty
	private String confirmaSenha;

	@Email
	@NotEmpty
	private String email;

	@Email
	@NotEmpty
	private String confirmaEmail;

	

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getConfirmaSenha() {
		return confirmaSenha;
	}

	public void setConfirmaSenha(String confirmaSenha) {
		this.confirmaSenha = confirmaSenha;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getConfirmaEmail() {
		return confirmaEmail;
	}

	public void setConfirmaEmail(String confirmaEmail) {
		this.confirmaEmail = confirmaEmail;
	}

	

}
