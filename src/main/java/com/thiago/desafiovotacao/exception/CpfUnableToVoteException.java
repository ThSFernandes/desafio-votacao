package com.thiago.desafiovotacao.exception;

import org.springframework.http.HttpStatus;

public class CpfUnableToVoteException extends ApiException {
    public CpfUnableToVoteException(String cpf) {
        super("CPF não habilitado a votar: " + cpf, HttpStatus.FORBIDDEN);
    }
}
