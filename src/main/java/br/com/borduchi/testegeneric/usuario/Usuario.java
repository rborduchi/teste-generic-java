package br.com.borduchi.testegeneric.usuario;

import lombok.Getter;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@MappedSuperclass
public abstract class Usuario {

    @Id
    private final UUID id;
    private String nome;
    private String sobrenome;
    private String endereco;
    private LocalDate dataNascimento;

    protected Usuario() {
        this.id = UUID.randomUUID();
    }

    public void update(Usuario usuario) {
        this.nome = usuario.getNome();
        this.sobrenome = usuario.getSobrenome();
        this.endereco = usuario.getEndereco();
        this.dataNascimento = usuario.getDataNascimento();
    }

}
