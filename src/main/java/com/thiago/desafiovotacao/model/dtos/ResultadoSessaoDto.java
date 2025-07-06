package com.thiago.desafiovotacao.model.dtos;

import com.thiago.desafiovotacao.model.enums.StatusVotacao;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResultadoSessaoDto {
    private Long id;
    private LocalDateTime dataDeCriacao;
    private LocalDateTime dataDeTermino;
    private long totalSim;
    private long totalNao;
    private long totalVotos;
    private StatusVotacao resultado;
}