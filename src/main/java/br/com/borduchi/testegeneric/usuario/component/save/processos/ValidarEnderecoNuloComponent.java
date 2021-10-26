package br.com.borduchi.testegeneric.usuario.component.save.processos;

import br.com.borduchi.testegeneric.usuario.GenericUsuario;
import br.com.borduchi.testegeneric.usuario.UsuarioRepositoryJpa;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;

import static java.util.Objects.isNull;

@RequiredArgsConstructor
public abstract class ValidarEnderecoNuloComponent<T extends GenericUsuario<T>> implements ProcessosSalvarUsuarioChain<T> {

    public static final String ENDERECO_NULO = "O ENDEREÇO NÃO PODE SER NULO";

    private final UsuarioRepositoryJpa<T> usuarioRepository;

    public void process(T usuario) {
        if (isNull(usuario.getEndereco())) {
            throw new DataIntegrityViolationException(ENDERECO_NULO);
        }
    }

}
