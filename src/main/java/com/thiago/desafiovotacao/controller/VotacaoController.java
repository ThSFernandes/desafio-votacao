package com.thiago.desafiovotacao.controller;

import com.thiago.desafiovotacao.controller.doc.VotacaoControllerDoc;
import com.thiago.desafiovotacao.model.dtos.CriarVotacaoDto;
import com.thiago.desafiovotacao.model.dtos.VotacaoDto;
import com.thiago.desafiovotacao.model.dtos.VotoDetalhadoDto;
import com.thiago.desafiovotacao.service.VotacaoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/votacao")
public class VotacaoController implements VotacaoControllerDoc {

    private final VotacaoService votacaoService;

    @PostMapping(path = "/{idSesao}/associados/{idAssociado}/votos", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<VotacaoDto> criarVoto(@PathVariable Long idSesao,
                                                @PathVariable Long idAssociado,
                                                @Valid @RequestBody CriarVotacaoDto dto) {
        VotacaoDto votacaoDto = votacaoService.criarVoto(idSesao, idAssociado, dto);
        return new ResponseEntity<>(votacaoDto, HttpStatus.CREATED);
    }

    @GetMapping("/votos/{idVoto}")
    public ResponseEntity<VotacaoDto> buscarVoto(@PathVariable Long idVoto) {
        VotacaoDto votacaoDto = votacaoService.buscarVotoPorId(idVoto);
        return new ResponseEntity<>(votacaoDto, HttpStatus.OK);
    }

    @GetMapping("/associado/{idAssociado}/votos")
    public ResponseEntity<VotoDetalhadoDto> listarVotosDoAssociado(@PathVariable Long idAssociado) {
        VotoDetalhadoDto votoDetalhadoDto = votacaoService.listarVotosDoAssociado(idAssociado);
        return new ResponseEntity<>(votoDetalhadoDto, HttpStatus.OK);

    }

}
