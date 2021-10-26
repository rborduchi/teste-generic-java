package br.com.borduchi.testegeneric.usuario.cliente;

import br.com.borduchi.testegeneric.usuario.GenericUsuario;
import br.com.borduchi.testegeneric.usuario.Usuario;
import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Getter
@Entity
@Table(name = "tb_cliente", schema = "teste_generic")
public class Cliente extends Usuario implements Serializable, GenericUsuario<Cliente> {

    public Cliente() {
        super();
    }

    @Override
    public Cliente create() {
        Cliente cliente = new Cliente();
        cliente.update(this);
        return cliente;
    }

    @Override
    public void update(Cliente cliente) {
        super.update(cliente);
    }

}
