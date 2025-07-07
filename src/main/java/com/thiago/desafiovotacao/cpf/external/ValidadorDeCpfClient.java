package com.thiago.desafiovotacao.cpf.external;

import com.thiago.desafiovotacao.cpf.client.ValidacaoCpfClient;
import com.thiago.desafiovotacao.cpf.enums.ValidaVoto;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class ValidadorDeCpfClient implements ValidacaoCpfClient {

    private final Random random = new Random();
    private static final double PROBABILIDADE_DE_RECUSAR = 0.4;

    @Override
    public ValidaVoto validarVoto(
            String cpf) {

        return random.nextDouble() < PROBABILIDADE_DE_RECUSAR
                ? ValidaVoto.UNABLE_TO_VOTE
                : ValidaVoto.ABLE_TO_VOTE;
    }
}
