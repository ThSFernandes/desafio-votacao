package com.thiago.desafiovotacao.exception;

import org.springframework.http.HttpStatus;

public class CpfNotFoundException extends ApiException {
    public CpfNotFoundException(String cpf) {
        super("CPF inválido: " + cpf, HttpStatus.BAD_REQUEST);
    }
}
