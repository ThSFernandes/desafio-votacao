package com.thiago.desafiovotacao.model.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.thiago.desafiovotacao.model.enums.TipoVoto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(
        name = "Votacao",
        description = "Detalhes de um voto registrado")
public class VotacaoDto {

    @Schema(
            description = "Identificador único do voto",
            example = "123",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    private Long id;

    @Schema(
            description = "Tipo de voto registrado",
            example = "NAO",
            required = true
    )
    private TipoVoto tipoVoto;

    @Schema(
            description = "Data e hora em que o voto foi registrado",
            example = "2025-07-07T15:00:00",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    private LocalDateTime dataCriacao;

    @Schema(
            description = "ID do associado que votou",
            example = "42",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    private Long idAssociado;

    @Schema(
            description = "ID da sessão de votação associada",
            example = "10",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    private Long idSessaoVotacao;

}
