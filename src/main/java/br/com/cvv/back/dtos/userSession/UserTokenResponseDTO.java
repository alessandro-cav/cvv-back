package br.com.cvv.back.dtos.userSession;

import java.io.Serializable;

public class UserTokenResponseDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private UserResponseDTO usuario;

	private String token;

	public UserResponseDTO getUsuario() {
		return usuario;
	}

	public void setUsuario(UserResponseDTO usuario) {
		this.usuario = usuario;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
