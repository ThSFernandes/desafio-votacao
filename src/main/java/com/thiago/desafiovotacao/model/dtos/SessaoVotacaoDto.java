package com.thiago.desafiovotacao.model.dtos;

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
public class SessaoVotacaoDto {

    private Long id;
    private LocalDateTime dataDeCriacao;
    private LocalDateTime dataDeTermino;
    private Long idPauta;
    private List<VotacaoDto> votos;
}
