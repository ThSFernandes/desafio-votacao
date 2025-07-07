package com.thiago.desafiovotacao.service;

import com.thiago.desafiovotacao.cpf.dto.ValidadorCpfDto;
import com.thiago.desafiovotacao.cpf.enums.ValidaVoto;
import com.thiago.desafiovotacao.cpf.external.ValidadorDeCpfClient;
import com.thiago.desafiovotacao.exception.CpfUnableToVoteException;
import com.thiago.desafiovotacao.model.mapper.CpfMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CpfService {

    private final ValidadorDeCpfClient validaCpf;
    private final CpfMapper mapper;

    public ValidadorCpfDto verificaPermissaoDeVoto(String cpf) {
        log.info("Iniciando verificação de permissão de voto para um CPF.");

        ValidaVoto resultado = validaCpf.validarVoto(cpf);
        log.debug("Resultado da validação do CPF: {}", resultado); // sem exibir o CPF

        if (resultado.equals(ValidaVoto.UNABLE_TO_VOTE)) {
            log.warn("CPF não autorizado para votar.");
            throw new CpfUnableToVoteException("CPF não autorizado para votar.");
        }

        log.info("CPF autorizado para votar.");
        return mapper.toEntity(resultado);
    }
}

