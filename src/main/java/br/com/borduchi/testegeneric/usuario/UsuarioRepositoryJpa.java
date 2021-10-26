package br.com.borduchi.testegeneric.usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;
import java.util.UUID;

@NoRepositoryBean
public interface UsuarioRepositoryJpa<T extends GenericUsuario<T>> extends JpaRepository<T, UUID> {

    Optional<T> findByNome(String nome);
}
