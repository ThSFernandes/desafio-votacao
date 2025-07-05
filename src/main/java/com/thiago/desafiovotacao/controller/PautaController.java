package com.thiago.desafiovotacao.controller;

import com.thiago.desafiovotacao.model.dtos.PautaDto;
import com.thiago.desafiovotacao.service.PautaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/pauta")
public class PautaController {

    private final PautaService pautaService;

    @PostMapping
    public PautaDto criarPauta(@RequestBody PautaDto pauta) {
        return pautaService.criarPauta(pauta);
    }

    @GetMapping("/{id}")
    public PautaDto buscarPauta(@PathVariable("id") Long id) {
        return pautaService.buscarPautaPeloId(id);
    }

    @PutMapping("/{id}")
    public PautaDto atualizarPauta(@PathVariable("id") Long id, @RequestBody PautaDto pautaAtualizar) {
        return pautaService.atualizarPauta(id, pautaAtualizar);
    }

    @DeleteMapping("/{id}")
    public void deletarPauta(@PathVariable("id") Long id) {
        pautaService.deletarPauta(id);
    }
}
