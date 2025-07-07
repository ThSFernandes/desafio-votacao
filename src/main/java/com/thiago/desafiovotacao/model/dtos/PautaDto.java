package com.thiago.desafiovotacao.model.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(
        name = "Pauta",
        description = "Dados de uma pauta")
public class PautaDto {

    @Schema(
            description = "Identificador único da pauta",
            example = "1",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    private Long id;

    @NotBlank(message = "Título é obrigatório")
    @Size(max = 120, message = "Título deve ter no máximo 120 caracteres")
    @Schema(
            description = "Título da pauta",
            example = "Reforma da sede",
            maxLength = 120,
            required = true
    )
    private String titulo;

    @NotBlank(message = "Descrição é obrigatória")
    @Size(max = 200, message = "Descrição deve ter no máximo 200 caracteres")
    @Schema(
            description = "Descrição detalhada da pauta",
            example = "Deliberação sobre reforma do prédio administrativo",
            maxLength = 200,
            required = true
    )
    private String descricao;

    private LocalDateTime dataCriacao;
}
