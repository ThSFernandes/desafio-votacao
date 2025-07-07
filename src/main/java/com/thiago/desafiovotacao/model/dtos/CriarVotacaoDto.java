package com.thiago.desafiovotacao.model.dtos;

import com.thiago.desafiovotacao.model.enums.TipoVoto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(
        name = "CriarVotacao",
        description = "Payload para registrar um voto numa sessão")
public class CriarVotacaoDto {

    @NotNull(message = "Tipo de voto é obrigatório")
    @Schema(
            description = "Tipo de voto: SIM ou NAO",
            example = "SIM",
            required = true
    )
    private TipoVoto tipoVoto;
}
