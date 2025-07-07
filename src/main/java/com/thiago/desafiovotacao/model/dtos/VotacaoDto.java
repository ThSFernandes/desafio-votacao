package com.thiago.desafiovotacao.model.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.thiago.desafiovotacao.model.enums.TipoVoto;
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
public class VotacaoDto {

    private Long id;
    private TipoVoto tipoVoto;
    private LocalDateTime dataCriacao;
    private Long idAssociado;
    private Long idSessaoVotacao;

}
