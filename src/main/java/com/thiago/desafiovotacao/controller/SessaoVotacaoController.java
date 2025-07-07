package com.thiago.desafiovotacao.controller;

import com.thiago.desafiovotacao.controller.doc.SessaoVotacaoControllerDoc;
import com.thiago.desafiovotacao.model.dtos.CriacaoSessaoVotacaoDto;
import com.thiago.desafiovotacao.model.dtos.ResultadoSessaoDto;
import com.thiago.desafiovotacao.model.dtos.SessaoVotacaoDto;
import com.thiago.desafiovotacao.service.SessaoVotacaoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sessao-votacao")
public class SessaoVotacaoController implements SessaoVotacaoControllerDoc {

    private final SessaoVotacaoService sessaoVotacaoService;

    @PostMapping("/{idPauta}/sessao")
    public ResponseEntity<SessaoVotacaoDto> criarSessaoVotacao(
            @PathVariable Long idPauta,
            @Valid @RequestBody CriacaoSessaoVotacaoDto dto) {

        SessaoVotacaoDto sessaoVotacaoDto = sessaoVotacaoService.criarSessaoVotacao(idPauta, dto);
        return new ResponseEntity<>(sessaoVotacaoDto, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SessaoVotacaoDto> buscarSessaoVotacaoDetalhada(@PathVariable("id") Long id) {
        SessaoVotacaoDto sessaoVotacaoDto = sessaoVotacaoService.buscarSessaoPorIdDetalhado(id);
        return new ResponseEntity<>(sessaoVotacaoDto, HttpStatus.OK);
    }

    @GetMapping("/resultado/{id}")
    public ResponseEntity<ResultadoSessaoDto> buscarSessaoVotacaoApurada(@PathVariable("id") Long id) {
        ResultadoSessaoDto resultadoSessaoDto = sessaoVotacaoService.buscarSessaoPorIdParaResultado(id);
        return new ResponseEntity<>(resultadoSessaoDto, HttpStatus.OK);
    }

}
