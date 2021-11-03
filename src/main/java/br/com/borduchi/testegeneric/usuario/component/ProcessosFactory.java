package br.com.borduchi.testegeneric.usuario.component;

import br.com.borduchi.testegeneric.usuario.GenericUsuario;
import br.com.borduchi.testegeneric.usuario.UsuarioRepositoryJpa;
import br.com.borduchi.testegeneric.usuario.cliente.Cliente;
import br.com.borduchi.testegeneric.usuario.component.save.processos.ProcessosSalvarUsuarioChain;
import br.com.borduchi.testegeneric.usuario.component.save.processos.ValidarEnderecoNuloComponent;
import br.com.borduchi.testegeneric.usuario.component.save.processos.ValidarMaioridadeComponent;
import br.com.borduchi.testegeneric.usuario.component.save.processos.ValidarNomeRepetidoComponent;
import br.com.borduchi.testegeneric.usuario.funcionario.Funcionario;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public abstract class ProcessosFactory<T extends GenericUsuario<T>> {

    public static final String TIPO_DE_USUARIO_NAO_ENCONTRADO = "TIPO DE USUÁRIO NÃO ENCONTRADO.";
    private final ValidarNomeRepetidoComponent<T> validarNomeRepetidoComponent;
    private final ValidarMaioridadeComponent<T> validarMaioridadeComponent;
    private final ValidarEnderecoNuloComponent<T> validarEnderecoNuloComponent;

    protected ProcessosFactory(UsuarioRepositoryJpa<T> usuarioRepositoryJpa) {
        this.validarNomeRepetidoComponent = new ValidarNomeRepetidoComponent<>(usuarioRepositoryJpa) {
        };
        this.validarMaioridadeComponent = new ValidarMaioridadeComponent<>(usuarioRepositoryJpa) {
        };
        this.validarEnderecoNuloComponent = new ValidarEnderecoNuloComponent<>(usuarioRepositoryJpa) {
        };
    }

    public List<ProcessosSalvarUsuarioChain<T>> getProcessosCriar(T usuario) {
        List<ProcessosSalvarUsuarioChain<T>> processos = new ArrayList<>();
        if (usuario instanceof Funcionario) {
            processos.add(validarNomeRepetidoComponent);
            processos.add(validarMaioridadeComponent);
            return processos;
        } else if (usuario instanceof Cliente) {
            processos.add(validarNomeRepetidoComponent);
            processos.add(validarEnderecoNuloComponent);
            return processos;
        } else {
            throw new DataIntegrityViolationException(TIPO_DE_USUARIO_NAO_ENCONTRADO);
        }
    }

}
