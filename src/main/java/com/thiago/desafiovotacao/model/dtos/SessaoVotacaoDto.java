package com.thiago.desafiovotacao.model.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(
        name = "SessaoVotacao",
        description = "Detalhes de uma sessão de votação, incluindo votos registrados")
public class SessaoVotacaoDto {

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
            example = "2025-07-07T14:45:00",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    private LocalDateTime dataDeTermino;

    @Schema(
            description = "Identificador da pauta associada a esta sessão",
            example = "5",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    private Long idPauta;

    @Schema(
            description = "Lista de votos lançados nesta sessão",
            implementation = VotacaoDto.class
    )
    private List<VotacaoDto> votos;
}
