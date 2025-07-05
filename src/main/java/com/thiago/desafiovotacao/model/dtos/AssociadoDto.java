package com.thiago.desafiovotacao.model.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AssociadoDto {

    private Long id;
    private String nome;
    private String cpf;
}
