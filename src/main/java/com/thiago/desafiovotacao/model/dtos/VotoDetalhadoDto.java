package com.thiago.desafiovotacao.model.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(
        name = "VotoDetalhado",
        description = "Lista de votos feita por um associado em diferentes sessões")
public class VotoDetalhadoDto {

    @Schema(
            description = "ID do associado",
            example = "42",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    private Long idAssociado;

    @Schema(
            description = "Nome completo do associado",
            example = "João da Silva",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    private String nomeAssociado;

    @Schema(
            description = "Detalhes de cada voto registrado pelo associado",
            implementation = ItemVotoDto.class
    )
    List<ItemVotoDto> votos;
}
