package com.thiago.desafiovotacao.model.dtos;

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
public class PautaDto {

    private Long id;

    @NotBlank(message = "Título é obrigatório")
    @Size(max = 120, message = "Título deve ter no máximo 120 caracteres")
    private String titulo;

    @NotBlank(message = "Descrição é obrigatória")
    @Size(max = 200, message = "Descrição deve ter no máximo 200 caracteres")
    private String descricao;

    private LocalDateTime dataCriacao;
}
