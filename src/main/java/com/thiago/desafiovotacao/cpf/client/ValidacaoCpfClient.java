package com.thiago.desafiovotacao.cpf.client;

import com.thiago.desafiovotacao.cpf.enums.ValidaVoto;

public interface ValidacaoCpfClient {

    ValidaVoto validarVoto(String cpf);
}
