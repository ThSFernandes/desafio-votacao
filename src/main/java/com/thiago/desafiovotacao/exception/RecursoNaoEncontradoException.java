package com.thiago.desafiovotacao.exception;

import org.springframework.http.HttpStatus;

public class RecursoNaoEncontradoException extends ApiException {
    public RecursoNaoEncontradoException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
