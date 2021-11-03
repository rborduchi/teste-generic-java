package br.com.borduchi.testegeneric.usuario.funcionario;

import br.com.borduchi.testegeneric.usuario.GenericUsuario;
import br.com.borduchi.testegeneric.usuario.Usuario;
import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Entity
@Table(name = "tb_funcionario", schema = "teste_generic")
public class Funcionario extends Usuario implements GenericUsuario<Funcionario> {

    public Funcionario() {
        super();
    }

    @Override
    public Funcionario create() {
        Funcionario funcionario = new Funcionario();
        funcionario.update(this);
        return funcionario;
    }

    @Override
    public void update(Funcionario funcionario) {
        super.update(funcionario);
    }

}
