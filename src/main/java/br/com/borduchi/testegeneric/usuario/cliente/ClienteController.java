package br.com.borduchi.testegeneric.usuario.cliente;

import br.com.borduchi.testegeneric.usuario.UsuarioController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cliente")
public class ClienteController extends UsuarioController<Cliente> {

    public ClienteController(ClienteRepository clienteRepository) {
        super(clienteRepository);
    }
}
