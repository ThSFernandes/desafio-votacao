package com.thiago.desafiovotacao.model.dtos;

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
public class ItemVotoDto {
    private TipoVoto tipoVoto;
    private String nomePauta;
    private LocalDateTime dataVoto;
}
