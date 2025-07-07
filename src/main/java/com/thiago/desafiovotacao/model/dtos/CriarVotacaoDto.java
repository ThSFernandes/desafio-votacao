package com.thiago.desafiovotacao.model.dtos;

import com.thiago.desafiovotacao.model.enums.TipoVoto;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CriarVotacaoDto {

    @NotNull(message = "Tipo de voto é obrigatório")
    private TipoVoto tipoVoto;
}
