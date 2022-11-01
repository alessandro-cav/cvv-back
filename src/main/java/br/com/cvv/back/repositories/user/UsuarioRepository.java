package br.com.cvv.back.repositories.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.cvv.back.entities.Usuario;

public interface UsuarioRepository  extends JpaRepository<Usuario, Long>{

	Optional<Usuario> findByLogin(String login);

}
