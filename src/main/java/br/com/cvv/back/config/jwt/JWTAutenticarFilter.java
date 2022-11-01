package br.com.cvv.back.config.jwt;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import br.com.cvv.back.dtos.userSession.PerfilResponseDTO;
import br.com.cvv.back.dtos.userSession.UserRequestDTO;
import br.com.cvv.back.dtos.userSession.UserResponseDTO;
import br.com.cvv.back.dtos.userSession.UserTokenResponseDTO;
import br.com.cvv.back.entities.Usuario;
import br.com.cvv.back.service.user.UserService;

public class JWTAutenticarFilter extends UsernamePasswordAuthenticationFilter {

	private AuthenticationManager authenticationManager;

	private UserService service;

	public JWTAutenticarFilter(AuthenticationManager authenticationManager, UserService service) {
		this.authenticationManager = authenticationManager;
		this.service = service;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		try {
			UserRequestDTO userRequestDTO = new ObjectMapper().readValue(request.getInputStream(),
					UserRequestDTO.class);

			return this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					userRequestDTO.getLogin(), userRequestDTO.getPassword(), new ArrayList<>()

			));
		} catch (IOException e) {
			throw new RuntimeException("Falha ao autenticar o usuario" + e);
		}

	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {

		User user = (User) authResult.getPrincipal();

		String token = JWT.create().withSubject(user.getUsername())
				.withExpiresAt(new Date(System.currentTimeMillis() + JWTConstants.TOKEN_EXPIRADO))
				.sign(Algorithm.HMAC512(JWTConstants.CHAVE_ASSINATURA));

		Usuario usuario = this.service.buscarUsuarioPeloLogin(user.getUsername());

		UserResponseDTO userResposta = new UserResponseDTO();
		userResposta.setId(usuario.getId());
		userResposta.setLogin(usuario.getLogin());
		userResposta.setStatus(usuario.getStatus());

		PerfilResponseDTO perfilResponseDTO = new PerfilResponseDTO();
		perfilResponseDTO.setId(usuario.getPerfil().getId());
		perfilResponseDTO.setNome(usuario.getPerfil().getNome());
		userResposta.setPerfil(perfilResponseDTO);

		UserTokenResponseDTO tokenResponseDTO = new UserTokenResponseDTO();
		tokenResponseDTO.setUsuario(userResposta);
		tokenResponseDTO.setToken(token);

		String json = new Gson().toJson(tokenResponseDTO);
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json);
		response.getWriter().flush();

	}

}
