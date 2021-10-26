package br.com.borduchi.testegeneric.usuario.funcionario;

import br.com.borduchi.testegeneric.usuario.UsuarioController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/funcionario")
public class FuncionarioController extends UsuarioController<Funcionario> {

    public FuncionarioController(FuncionarioRepository funcionarioRepository) {
        super(funcionarioRepository);
    }
}
