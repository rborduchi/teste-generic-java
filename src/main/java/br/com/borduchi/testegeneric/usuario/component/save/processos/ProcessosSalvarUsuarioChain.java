package br.com.borduchi.testegeneric.usuario.component.save.processos;

import br.com.borduchi.testegeneric.usuario.GenericUsuario;

public interface ProcessosSalvarUsuarioChain<T extends GenericUsuario<T>> {

    void process(T usuario);

}
