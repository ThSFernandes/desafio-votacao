package com.thiago.desafiovotacao.model.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PautaDto {

    private Long id;
    private String titulo;
    private String descricao;
    private LocalDateTime dataCriacao;
}
