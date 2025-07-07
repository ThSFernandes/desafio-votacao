package com.thiago.desafiovotacao.controller.doc;

import com.thiago.desafiovotacao.model.dtos.AssociadoDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Associados", description = "Operações para informações dos associados")
@RequestMapping("/associado")
public interface AssociadoControllerDoc {


    @Operation(
            summary = "Busca associado por ID",
            description = "Retorna os dados de um associado existente.",
            parameters = {
                    @Parameter(
                            name = "id",
                            description = "Identificador único do associado",
                            example = "1"
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Associado encontrado",
                            content = @Content(schema = @Schema(implementation = AssociadoDto.class))
                    ),
                    @ApiResponse(responseCode = "404", description = "Associado não encontrado")
            }
    )
    @GetMapping("/{id}")
    ResponseEntity<AssociadoDto> buscarAssociadoPorId(@PathVariable("id") Long id);

    @Operation(
            summary = "Cria um novo associado",
            description = "Registra um associado com nome .",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(schema = @Schema(implementation = AssociadoDto.class))
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Associado criado com sucesso",
                            content = @Content(schema = @Schema(implementation = AssociadoDto.class))
                    ),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos")
            }
    )
    @PostMapping
    ResponseEntity<AssociadoDto> criarAssociado(@Valid @RequestBody AssociadoDto associado);


    @Operation(
            summary = "Remove associado",
            description = "Exclui definitivamente o associado e seus registros de voto.",
            parameters = @Parameter(name = "id", description = "ID do associado", example = "1"),
            responses = {
                    @ApiResponse(responseCode = "204", description = "Associado removido"),
                    @ApiResponse(responseCode = "404", description = "Associado não encontrado")
            }
    )
    @DeleteMapping("/{id}")
    ResponseEntity<Void> deletar(@PathVariable Long id);
}
