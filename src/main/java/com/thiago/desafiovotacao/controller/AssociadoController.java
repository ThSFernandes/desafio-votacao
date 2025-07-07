package com.thiago.desafiovotacao.controller;

import com.thiago.desafiovotacao.controller.doc.AssociadoControllerDoc;
import com.thiago.desafiovotacao.model.dtos.AssociadoDto;
import com.thiago.desafiovotacao.service.AssociadoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/v1/associado")
public class AssociadoController implements AssociadoControllerDoc {

    private final AssociadoService associadoService;

    @GetMapping("/{id}")
    public ResponseEntity<AssociadoDto> buscarAssociadoPorId(@PathVariable("id") Long id) {
        AssociadoDto associadoDto = associadoService.buscarAssociado(id);
        return new ResponseEntity<>(associadoDto, HttpStatus.OK);
    }

    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AssociadoDto> criarAssociado(@Valid @RequestBody AssociadoDto associado) {
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
