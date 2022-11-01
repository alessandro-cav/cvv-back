package br.com.cvv.back.config.authentication;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;

import br.com.cvv.back.dtos.userSession.AutenticacaoDTO;

@Configuration
public class AutenticacaoConfig {


	public AutenticacaoDTO gerarAutenticacaoDTO(Authentication authentication) {
		if (authentication == null) {
			throw new RuntimeException("autenticação é nula.");
		}
		if (!(authentication.getDetails() instanceof AutenticacaoDTO)) {
			throw new RuntimeException("Falha ao carregar a classe de detalhes de autenticação");
		}
		return (AutenticacaoDTO) authentication.getDetails();
	}
}

