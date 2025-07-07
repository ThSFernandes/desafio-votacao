package com.thiago.desafiovotacao.exception;

import org.springframework.http.HttpStatus;

public class SessaoException extends ApiException {
    public SessaoException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}

