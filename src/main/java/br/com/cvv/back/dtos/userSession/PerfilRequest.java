package br.com.cvv.back.dtos.userSession;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class PerfilRequest implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;

	@NotBlank(message = "{nome.not.blank}")
	@NotNull(message = "{nome.not.null}")
	private String nome;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

}
