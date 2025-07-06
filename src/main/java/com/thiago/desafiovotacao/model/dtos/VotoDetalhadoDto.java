package com.thiago.desafiovotacao.model.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VotoDetalhadoDto {
    private Long idAssociado;
    private String nomeAssociado;
    List<ItemVotoDto> votos;
}
