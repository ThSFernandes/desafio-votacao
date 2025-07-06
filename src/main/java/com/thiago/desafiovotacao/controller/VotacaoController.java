package com.thiago.desafiovotacao.controller;

import com.thiago.desafiovotacao.model.dtos.CriarVotacaoDto;
import com.thiago.desafiovotacao.model.dtos.VotacaoDto;
import com.thiago.desafiovotacao.model.dtos.VotoDetalhadoDto;
import com.thiago.desafiovotacao.service.VotacaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/votacao")
public class VotacaoController {

    private final VotacaoService votacaoService;

    @PostMapping("/{idSesao}/associados/{idAssociado}/votos")
    public VotacaoDto criarVoto(@PathVariable Long idSesao,
                                @PathVariable Long idAssociado,
                                @RequestBody CriarVotacaoDto dto) {
        return votacaoService.criarVoto(idSesao, idAssociado, dto);
    }

    @GetMapping("/votos/{idVoto}")
    public VotacaoDto buscarVoto(@PathVariable Long idVoto) {
        return votacaoService.buscarVotoPorId(idVoto);
    }

    @GetMapping("/associado/{idAssociado}/votos")
    public VotoDetalhadoDto listarVotosDoAssociado(@PathVariable Long idAssociado) {
        return votacaoService.listarVotosDoAssociado(idAssociado);

    }

}
