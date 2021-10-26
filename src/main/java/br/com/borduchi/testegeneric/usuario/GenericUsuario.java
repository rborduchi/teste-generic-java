package br.com.borduchi.testegeneric.usuario;

import java.time.LocalDate;
import java.util.UUID;

public interface GenericUsuario<T> {

    UUID getId();

    String getNome();

    String getSobrenome();

    String getEndereco();

    LocalDate getDataNascimento();

    T create();

    void update(T usuario);

}
