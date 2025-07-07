package com.thiago.desafiovotacao.model.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(
        name = "CriacaoSessaoVotacao",
        description = "DTO de entrada para criação de sessão de votação, informando duração em minutos")
public class CriacaoSessaoVotacaoDto {

    @Positive(message = "Duração deve ser maior que zero")
    @Schema(
            description = "Duração da sessão em minutos; se não informado, usa valor padrão no service",
            example = "15",
            minimum = "1",
            required = true
    )
    private Integer duracaoMinutos;

}