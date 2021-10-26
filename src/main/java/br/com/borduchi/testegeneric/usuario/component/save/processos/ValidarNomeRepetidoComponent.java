package br.com.borduchi.testegeneric.usuario.component.save.processos;

import br.com.borduchi.testegeneric.usuario.GenericUsuario;
import br.com.borduchi.testegeneric.usuario.UsuarioRepositoryJpa;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityExistsException;
import java.util.Optional;
import java.util.function.Consumer;

@RequiredArgsConstructor
public abstract class ValidarNomeRepetidoComponent<T extends GenericUsuario<T>> implements ProcessosSalvarUsuarioChain<T> {

    public static final String USUARIO_EXISTENTE = "USUÁRIO %s JÁ EXISTE.";

    private final UsuarioRepositoryJpa<T> usuarioRepository;

    public void process(T usuario) {
        Optional<T> usuarioEncontrado = usuarioRepository.findByNome(usuario.getNome());
        usuarioEncontrado.ifPresent(throwsException());
    }

    private Consumer<T> throwsException() {
        return usuario -> {
            String mensagem = String.format(USUARIO_EXISTENTE, usuario.getNome());
            throw new EntityExistsException(mensagem);
        };
    }
}
