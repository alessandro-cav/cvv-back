package br.com.cvv.back.dtos.userSession;

import java.io.Serializable;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.cvv.back.enuns.Status;

public class UserResponseDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	private String login;

	@JsonIgnore
	private String password;

	@Enumerated(EnumType.STRING)
	private Status status;

	private PerfilResponseDTO perfil;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public PerfilResponseDTO getPerfil() {
		return perfil;
	}

	public void setPerfil(PerfilResponseDTO perfil) {
		this.perfil = perfil;
	}

}
