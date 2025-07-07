package com.thiago.desafiovotacao.model.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(
        name = "Associado",
        description = "DTO de Associação com exemplos de uso")
public class AssociadoDto {

    @Schema(
            description = "Identificador único do associado",
            example     = "1",
            accessMode  = Schema.AccessMode.READ_ONLY
    )
    private Long id;

    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 50, message = "Nome pode ter no máximo 50 caracteres")
    @Schema(
            description = "Nome completo do associado",
            example     = "João da Silva",
            maxLength   = 50,
            required    = true
    )
    private String nome;

    @NotBlank(message = "CPF é obrigatório")
    @Pattern(regexp = "\\d{11}", message = "CPF deve conter 11 dígitos numéricos")
    @Schema(
            description = "CPF do associado, apenas dígitos (11 caracteres)",
            example     = "12345678901",
            pattern     = "\\d{11}",
            required    = true
    )
    private String cpf;
}
