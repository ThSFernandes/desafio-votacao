package com.thiago.desafiovotacao.controller;

import com.thiago.desafiovotacao.model.dtos.CriacaoSessaoVotacaoDto;
import com.thiago.desafiovotacao.model.dtos.ResultadoSessaoDto;
import com.thiago.desafiovotacao.model.dtos.SessaoVotacaoDto;
import com.thiago.desafiovotacao.service.SessaoVotacaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sessao-votacao")
public class SessaoVotacaoController {

    private final SessaoVotacaoService sessaoVotacaoService;

    @PostMapping("/{idPauta}/sessao")
    public SessaoVotacaoDto criarSessaoVotacao(
            @PathVariable Long idPauta,
            @RequestBody CriacaoSessaoVotacaoDto dto) {

        return sessaoVotacaoService.criarSessaoVotacao(idPauta, dto);
    }

    @GetMapping("/{id}")
    public SessaoVotacaoDto buscarSessaoVotacaoDetalhada(@PathVariable("id") Long id){
        return sessaoVotacaoService.buscarSessaoPorIdDetalhado(id);
    }

    @GetMapping("/resultado/{id}")
    public ResultadoSessaoDto buscarSessaoVotacaoApurada(@PathVariable("id") Long id){
        return sessaoVotacaoService.buscarSessaoPorIdParaResultado(id);
    }

}
