package com.thiago.desafiovotacao.cpf.dto;

import com.thiago.desafiovotacao.cpf.enums.ValidaVoto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Schema(
        name = "ValidadorCpfDto",
        description = "DTO de resposta da API de validação de CPF — informa se o CPF está habilitado a votar ou não.",
        implementation = ValidadorCpfDto.class
)
public class ValidadorCpfDto {

    @Schema(
            description = "Resultado da validação do CPF",
            example = "ABLE_TO_VOTE",
            required = true
    )
    private final ValidaVoto statusValidacao;
}
