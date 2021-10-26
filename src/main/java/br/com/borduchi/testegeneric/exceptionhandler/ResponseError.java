package br.com.borduchi.testegeneric.exceptionhandler;

import java.io.Serializable;

public class ResponseError implements Serializable {

    private String mensagem;

    private ResponseError() {
    }

    public ResponseError(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getMensagem() {
        return mensagem;
    }

}
