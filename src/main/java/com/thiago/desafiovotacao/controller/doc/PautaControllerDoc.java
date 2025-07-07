package com.thiago.desafiovotacao.controller.doc;

import com.thiago.desafiovotacao.model.dtos.PautaDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;

@Tag(name = "Pauta", description = "API para gerenciamento de pautas de votação")
public interface PautaControllerDoc {

    @Operation(summary = "Cria uma nova pauta", description = "Cria uma nova pauta de votação no sistema.",
            requestBody = @RequestBody(description = "Dados da pauta a ser criada", required = true,
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PautaDto.class))),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Pauta criada com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = PautaDto.class))),
                    @ApiResponse(responseCode = "400", description = "Requisição inválida (dados da pauta incompletos/inválidos)")
            })
    ResponseEntity<PautaDto> criarPauta(@Valid PautaDto pauta);

    @Operation(summary = "Busca uma pauta pelo ID", description = "Retorna os detalhes de uma pauta específica com base no seu ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Pauta encontrada com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = PautaDto.class))),
                    @ApiResponse(responseCode = "404", description = "Pauta não encontrada")
            })
    ResponseEntity<PautaDto> buscarPauta(
            @Parameter(description = "ID da pauta a ser buscada", required = true)
            Long id);

    @Operation(summary = "Atualiza uma pauta existente", description = "Atualiza os dados de uma pauta existente com base no seu ID.",
            requestBody = @RequestBody(description = "Novos dados da pauta para atualização", required = true,
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PautaDto.class))),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Pauta atualizada com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = PautaDto.class))),
                    @ApiResponse(responseCode = "400", description = "Requisição inválida (dados da pauta incompletos/inválidos)"),
                    @ApiResponse(responseCode = "404", description = "Pauta não encontrada")
            })
    ResponseEntity<PautaDto> atualizarPauta(
            @Parameter(description = "ID da pauta a ser atualizada", required = true)
            Long id,
            PautaDto pautaAtualizar);

    @Operation(summary = "Deleta uma pauta", description = "Deleta uma pauta do sistema com base no seu ID.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Pauta deletada com sucesso (No Content)"),
                    @ApiResponse(responseCode = "404", description = "Pauta não encontrada")
            })
    ResponseEntity<Void> deletarPauta(
            @Parameter(description = "ID da pauta a ser deletada", required = true)
            Long id);
}
