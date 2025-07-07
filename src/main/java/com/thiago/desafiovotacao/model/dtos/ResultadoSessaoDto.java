package com.thiago.desafiovotacao.model.dtos;

import com.thiago.desafiovotacao.model.enums.StatusVotacao;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(
        name = "ResultadoSessao",
        description = "Resumo dos resultados de uma sessão de votação")
public class ResultadoSessaoDto {

    @Schema(
            description = "Identificador único da sessão de votação",
            example = "10",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    private Long id;

    @Schema(
            description = "Data e hora de início da sessão",
            example = "2025-07-07T14:30:00",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    private LocalDateTime dataDeCriacao;

    @Schema(
            description = "Data e hora de término da sessão",
            example = "2025-07-07T14:31:00",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    private LocalDateTime dataDeTermino;

    @Schema(
            description = "Quantidade total de votos SIM",
            example = "7"
    )
    private long totalSim;

    @Schema(
            description = "Quantidade total de votos NÃO",
            example = "3"
    )
    private long totalNao;

    @Schema(
            description = "Quantidade total de votos",
            example = "10"
    )
    private long totalVotos;

    @Schema(
            description = "Resultado final da votação",
            example = "APROVADO",
            implementation = StatusVotacao.class
    )
    private StatusVotacao resultado;
}