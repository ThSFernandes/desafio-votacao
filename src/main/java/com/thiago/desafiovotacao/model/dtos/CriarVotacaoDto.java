package com.thiago.desafiovotacao.model.dtos;

import com.thiago.desafiovotacao.model.enums.TipoVoto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CriarVotacaoDto {

    private TipoVoto tipoVoto;
}
