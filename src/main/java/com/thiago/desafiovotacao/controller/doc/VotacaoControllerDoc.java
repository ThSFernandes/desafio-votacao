package com.thiago.desafiovotacao.controller.doc;

import com.thiago.desafiovotacao.model.dtos.CriarVotacaoDto;
import com.thiago.desafiovotacao.model.dtos.VotacaoDto;
import com.thiago.desafiovotacao.model.dtos.VotoDetalhadoDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;

@Tag(name = "Votação", description = "API para registro e consulta de votos em sessões de votação")
public interface VotacaoControllerDoc {

    @Operation(summary = "Registra um voto de um associado em uma sessão de votação",
            description = "Permite que um associado registre seu voto ('SIM' ou 'NÃO') em uma sessão de votação específica.",
            parameters = {
                    @Parameter(name = "idSesao", description = "ID da sessão de votação", required = true),
                    @Parameter(name = "idAssociado", description = "ID do associado que está votando", required = true)
            },
            requestBody = @RequestBody(description = "Dados do voto (valor do voto)", required = true,
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CriarVotacaoDto.class))),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Voto registrado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = VotacaoDto.class))),
                    @ApiResponse(responseCode = "400", description = "Requisição inválida (sessão/associado não encontrados, voto inválido ou associado já votou)"),
                    @ApiResponse(responseCode = "404", description = "Sessão de votação ou associado não encontrados")
            })
    ResponseEntity<VotacaoDto> criarVoto(
            Long idSesao,
            Long idAssociado,
            @Valid CriarVotacaoDto dto);

    @Operation(summary = "Busca um voto específico pelo ID",
            description = "Retorna os detalhes de um voto registrado com base no seu ID.",
            parameters = {
                    @Parameter(name = "idVoto", description = "ID do voto a ser buscado", required = true)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Voto encontrado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = VotacaoDto.class))),
                    @ApiResponse(responseCode = "404", description = "Voto não encontrado")
            })
    ResponseEntity<VotacaoDto> buscarVoto(Long idVoto);

    @Operation(summary = "Lista todos os votos de um associado",
            description = "Retorna uma lista detalhada de todos os votos registrados por um associado específico.",
            parameters = {
                    @Parameter(name = "idAssociado", description = "ID do associado para listar os votos", required = true)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Votos do associado listados com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = VotoDetalhadoDto.class))),
                    @ApiResponse(responseCode = "404", description = "Associado não encontrado")
            })
    ResponseEntity<VotoDetalhadoDto> listarVotosDoAssociado(Long idAssociado);
}
