package com.thiago.desafiovotacao.controller.doc;

import com.thiago.desafiovotacao.model.dtos.CriacaoSessaoVotacaoDto;
import com.thiago.desafiovotacao.model.dtos.ResultadoSessaoDto;
import com.thiago.desafiovotacao.model.dtos.SessaoVotacaoDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

@Tag(name = "Sessão de Votação", description = "API para gerenciamento de sessões de votação")
public interface SessaoVotacaoControllerDoc {

    @Operation(summary = "Cria uma nova sessão de votação para uma pauta",
            description = "Associa e inicia uma nova sessão de votação a uma pauta existente. " +
                          "Pode-se definir uma duração para a sessão; se não informada, a duração padrão será aplicada.",
            parameters = {
                    @Parameter(name = "idPauta", description = "ID da pauta à qual a sessão de votação será associada", required = true)
            },
            requestBody = @RequestBody(description = "Dados para criação da sessão de votação (duração opcional)", required = true,
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CriacaoSessaoVotacaoDto.class))),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Sessão de votação criada com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = SessaoVotacaoDto.class))),
                    @ApiResponse(responseCode = "400", description = "Requisição inválida (pauta não encontrada ou dados inválidos)"),
                    @ApiResponse(responseCode = "404", description = "Pauta não encontrada")
            })
    ResponseEntity<SessaoVotacaoDto> criarSessaoVotacao(
            Long idPauta,
            @Valid CriacaoSessaoVotacaoDto dto);

    @Operation(summary = "Busca detalhes de uma sessão de votação",
            description = "Retorna os detalhes completos de uma sessão de votação específica, incluindo a pauta associada e o status da sessão.",
            parameters = {
                    @Parameter(name = "id", description = "ID da sessão de votação a ser buscada", required = true)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Sessão de votação encontrada com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = SessaoVotacaoDto.class))),
                    @ApiResponse(responseCode = "404", description = "Sessão de votação não encontrada")
            })
    ResponseEntity<SessaoVotacaoDto> buscarSessaoVotacaoDetalhada(Long id);

    @Operation(summary = "Busca o resultado de uma sessão de votação",
            description = "Retorna o resultado da votação para uma sessão específica, incluindo o número de votos 'Sim' e 'Não'. " +
                          "Se a sessão ainda estiver aberta, o resultado parcial será exibido.",
            parameters = {
                    @Parameter(name = "id", description = "ID da sessão de votação para apuração do resultado", required = true)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Resultado da sessão de votação apurado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResultadoSessaoDto.class))),
                    @ApiResponse(responseCode = "404", description = "Sessão de votação não encontrada")
            })
    ResponseEntity<ResultadoSessaoDto> buscarSessaoVotacaoApurada(Long id);
}
