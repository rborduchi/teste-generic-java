package br.com.borduchi.testegeneric.usuario.component.save;

import br.com.borduchi.testegeneric.usuario.GenericUsuario;
import br.com.borduchi.testegeneric.usuario.UsuarioRepositoryJpa;
import br.com.borduchi.testegeneric.usuario.component.ProcessosFactory;
import br.com.borduchi.testegeneric.usuario.component.save.processos.ProcessosSalvarUsuarioChain;

import java.util.List;

public abstract class SalvarUsuarioComponent<T extends GenericUsuario<T>> {

    private final ProcessosFactory<T> processosFactory;

    protected SalvarUsuarioComponent(UsuarioRepositoryJpa<T> usuarioRepositoryJpa) {
        this.processosFactory = new ProcessosFactory<T>(usuarioRepositoryJpa) {
        };
    }

    public void process(T usuario) {
        List<ProcessosSalvarUsuarioChain<T>> processos = processosFactory.getProcessosCriar(usuario);
        processos.forEach(proceso -> proceso.process(usuario));
    }

}
