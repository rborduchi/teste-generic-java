package br.com.borduchi.testegeneric.exceptionhandler.http_message_not_readable;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
public class HttpMessageNotReadableValidator {

    private final List<Validator> validators;

    public HttpMessageNotReadableValidator() {
        this.validators = List.of(
                new JsonParseErrorValidator()
        );
    }

    public String validar(String message) {
        return validators.stream()
                .map(validator -> validator.validarRetornandoMensagem(message))
                .filter(Objects::nonNull)
                .findAny()
                .orElse(null);
    }

}
