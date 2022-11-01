package br.com.cvv.back.config.jwt;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import br.com.cvv.back.dtos.userSession.AutenticacaoDTO;
import br.com.cvv.back.entities.Usuario;
import br.com.cvv.back.service.user.UserService;

public class JWTValidarFilter extends BasicAuthenticationFilter {

	private UserService service;

	public JWTValidarFilter(AuthenticationManager authenticationManager, UserService service) {
		super(authenticationManager);
		this.service = service;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		String authorization = request.getHeader(JWTConstants.HEADER_ATRIBUTO);

		if (authorization != null && !authorization.startsWith(JWTConstants.ATRIBUTO_PREFIXO)) {
			chain.doFilter(request, response);
			return;
		}

		String token = authorization.replace(JWTConstants.ATRIBUTO_PREFIXO, "");

		UsernamePasswordAuthenticationToken authenticationToken = getAuthenticationToken(token);

		String login = JWT.decode(token).getSubject();
		Usuario usuario = this.service.buscarUsuarioPeloLogin(login);
		// Optional<Empresa> empresa =
		// this.service.buscarEmpresaPeloCnpjUsuario(usuario.getCnpj());

		AutenticacaoDTO autenticacaoDTO = new AutenticacaoDTO();

		/*
		 * if (empresa.isPresent()) {
		 * autenticacaoDTO.setIdEmpresa(empresa.get().getId());
		 * autenticacaoDTO.setEmailEmpresa(empresa.get().getEmail());
		 * autenticacaoDTO.setCnpj(empresa.get().getCnpj());
		 * autenticacaoDTO.setRazaoSocial(empresa.get().getRazaoSocial());
		 * autenticacaoDTO.setNomeFantasia(empresa.get().getNomeFantasia());
		 * autenticacaoDTO.setTipoEmpresa(empresa.get().getTipoEmpresa());
		 * autenticacaoDTO.setCnae(empresa.get().getCnae()); }
		 */

		autenticacaoDTO.setIdUsuario(usuario.getId());
		autenticacaoDTO.setLogin(usuario.getLogin());
		autenticacaoDTO.setStatus(usuario.getStatus());

		autenticacaoDTO.setIdPerfil(usuario.getPerfil().getId());
		autenticacaoDTO.setNomePerfil(usuario.getPerfil().getNome());

		autenticacaoDTO.setToken(token);

		authenticationToken.setDetails(autenticacaoDTO);

		SecurityContextHolder.getContext().setAuthentication(authenticationToken);
		chain.doFilter(request, response);
	}

	private UsernamePasswordAuthenticationToken getAuthenticationToken(String token) {
		String usuario = JWT.require(Algorithm.HMAC512(JWTConstants.CHAVE_ASSINATURA)).build().verify(token)
				.getSubject();

		if (usuario == null) {
			return null;
		}
		return new UsernamePasswordAuthenticationToken(usuario, null, new ArrayList<>());
	}

}
