package br.com.borduchi.testegeneric.exceptionhandler.http_message_not_readable;

import lombok.NoArgsConstructor;

@NoArgsConstructor
class JsonParseErrorValidator implements Validator {

    private static final String MENSAGEM_JSON_PARSE_ERROR = "JSON parse error";
    private static final String MENSAGEM_EXCEPTION = "FORMATO DE DADOS INVÁLIDO. CONTATE O ADMINISTRADOR.";

    @Override
    public String validarRetornandoMensagem(String message) {
        return message.startsWith(MENSAGEM_JSON_PARSE_ERROR) ?
                MENSAGEM_EXCEPTION :
                null;
    }

}
