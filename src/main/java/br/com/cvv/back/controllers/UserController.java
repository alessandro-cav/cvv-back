package br.com.cvv.back.controllers;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.cvv.back.dtos.userSession.LoginRequestDTO;
import br.com.cvv.back.dtos.userSession.MensagemResponseDTO;
import br.com.cvv.back.dtos.userSession.SenhasRequestDTO;
import br.com.cvv.back.service.user.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

	private final UserService service;

	public UserController(UserService service) {
		this.service = service;
	}

	@PostMapping("/forgot_password")
	public ResponseEntity<Void> esqueciMinhaSenha(@RequestBody @Valid LoginRequestDTO loginRequestDTO) {
		this.service.esqueciMinhaSenha(loginRequestDTO);
		return ResponseEntity.ok().build();
	}

	@PostMapping("/reset_password")
	public ResponseEntity<MensagemResponseDTO> resetarSenha(@RequestParam String token,
			@RequestBody @Valid SenhasRequestDTO senhasRequestDTO) {
		return ResponseEntity.ok(this.service.resetarSenha(token, senhasRequestDTO));
	}
}
