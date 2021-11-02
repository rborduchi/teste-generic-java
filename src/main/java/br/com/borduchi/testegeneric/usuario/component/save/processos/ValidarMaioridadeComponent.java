package br.com.borduchi.testegeneric.usuario.component.save.processos;

import br.com.borduchi.testegeneric.usuario.GenericUsuario;
import br.com.borduchi.testegeneric.usuario.UsuarioRepositoryJpa;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;

import java.time.LocalDate;
import java.time.Period;

import static java.util.Objects.nonNull;

@RequiredArgsConstructor
public abstract class ValidarMaioridadeComponent<T extends GenericUsuario<T>> implements ProcessosSalvarUsuarioChain<T> {

    public static final int MAIORIDADE = 18;
    public static final String USUARIO_MENOR_DE_IDADE = "O USUÁRIO NÃO PODE SER MENOR DE IDADE.";

    private final UsuarioRepositoryJpa<T> usuarioRepository;

    public void process(T usuario) {
        int idade = getIdade(usuario.getDataNascimento());
        if (idade < MAIORIDADE) {
            throw new DataIntegrityViolationException(USUARIO_MENOR_DE_IDADE);
        }
    }

    public int getIdade(LocalDate datNascimento) {
        return nonNull(datNascimento) ? Period.between(datNascimento, LocalDate.now()).getYears() : 0;
    }

}
