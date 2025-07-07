package com.thiago.desafiovotacao.service;

import com.thiago.desafiovotacao.cpf.dto.ValidadorCpfDto;
import com.thiago.desafiovotacao.cpf.enums.ValidaVoto;
import com.thiago.desafiovotacao.cpf.external.ValidadorDeCpfClient;
import com.thiago.desafiovotacao.exception.CpfUnableToVoteException;
import com.thiago.desafiovotacao.model.mapper.CpfMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CpfService {

    private final ValidadorDeCpfClient validaCpf;
    private final CpfMapper mapper;

    public ValidadorCpfDto verificaPermissaoDeVoto(String cpf) {

        ValidaVoto resultado = validaCpf.validarVoto(cpf);

        if (resultado.equals(ValidaVoto.UNABLE_TO_VOTE)) {
            throw new CpfUnableToVoteException(cpf);
        }
        return mapper.toEntity(resultado);
    }

}
