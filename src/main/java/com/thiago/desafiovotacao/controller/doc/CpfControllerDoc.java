package com.thiago.desafiovotacao.controller.doc;

import com.thiago.desafiovotacao.cpf.dto.ValidadorCpfDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Tag(name = "CPF", description = "API para validação de CPF e permissão de voto")
public interface CpfControllerDoc {

    @Operation(
            summary = "Verifica a permissão de voto de um CPF",
            description = "Retorna se o CPF está habilitado a votar (ABLE_TO_VOTE) ou não (UNABLE_TO_VOTE). " +
                    "Se inválido ou não habilitado, retorna 404 com UNABLE_TO_VOTE.",
            parameters = {
                    @Parameter(
                            name = "cpf",
                            in = ParameterIn.PATH,
                            description = "Número do CPF a ser verificado (somente dígitos)",
                            required = true,
                            example = "12345678901"
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "CPF habilitado a votar",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ValidadorCpfDto.class),
                                    examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                                            value = "{ \"statusValidacao\": \"ABLE_TO_VOTE\" }"
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "CPF inválido ou não habilitado a votar",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ValidadorCpfDto.class),
                                    examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                                            value = "{ \"statusValidacao\": \"UNABLE_TO_VOTE\" }"
                                    )
                            )
                    )
            }
    )
    @GetMapping("/{cpf}")
    ResponseEntity<ValidadorCpfDto> verificaCpf(
            @PathVariable("cpf") String cpf
    );
}
