package com.thiago.desafiovotacao.controller;

import com.thiago.desafiovotacao.model.dtos.AssociadoDto;
import com.thiago.desafiovotacao.service.AssociadoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/associado")
public class AssociadoController {

    private final AssociadoService associadoService;

    @GetMapping("/{id}")
    public ResponseEntity<AssociadoDto> buscarAssociadoPorId(@PathVariable("id") Long id) {
        AssociadoDto associadoDto = associadoService.buscarAssociado(id);
        return new ResponseEntity<>(associadoDto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<AssociadoDto> criarAssociado(@RequestBody AssociadoDto associado) {
        AssociadoDto associadoDto = associadoService.criarAssociado(associado);
        return new ResponseEntity<>(associadoDto, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        associadoService.deletarAssociado(id);
        log.info("Associado id={} removido", id);
        return ResponseEntity.noContent().build();
    }
}
