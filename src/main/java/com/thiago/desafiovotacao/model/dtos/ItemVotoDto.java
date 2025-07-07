package com.thiago.desafiovotacao.model.dtos;

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
@Schema(
        name = "ItemVoto",
        description = "Representa um único voto detalhado de um associado, com informações sobre a pauta e o momento do voto")
public class ItemVotoDto {

    @Schema(
            description = "Tipo de voto registrado pelo associado",
            example = "SIM",
            required = true
    )
    private TipoVoto tipoVoto;

    @Schema(
            description = "Título da pauta em que o voto foi registrado",
            example = "Reforma da sede",
            required = true
    )
    private String nomePauta;

    @Schema(
            description = "Data e hora em que o associado votou",
            example = "2025-07-07T15:00:00",
            required = true
    )
    private LocalDateTime dataVoto;
}
