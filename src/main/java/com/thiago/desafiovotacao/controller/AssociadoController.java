package com.thiago.desafiovotacao.controller;

import com.thiago.desafiovotacao.model.dtos.AssociadoDto;
import com.thiago.desafiovotacao.service.AssociadoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/associado")
public class AssociadoController {

    private final AssociadoService associadoService;

    @GetMapping("/{id}")
    public AssociadoDto buscarAssociadoPorId (@PathVariable ("id") Long id){
        return associadoService.buscarAssociado(id);
    }

    @PostMapping
    public AssociadoDto criarAssociado(@RequestBody AssociadoDto associado) {
        return associadoService.criarAssociado(associado);
    }

    @DeleteMapping("/{id}")
    public void deletarAssociado(@PathVariable ("id") Long id){
        associadoService.deletarAssociado(id);
    }

}
