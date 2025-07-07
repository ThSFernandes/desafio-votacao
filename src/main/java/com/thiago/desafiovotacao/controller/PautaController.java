package com.thiago.desafiovotacao.controller;

import com.thiago.desafiovotacao.controller.doc.PautaControllerDoc;
import com.thiago.desafiovotacao.model.dtos.PautaDto;
import com.thiago.desafiovotacao.service.PautaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/pauta")
public class PautaController implements PautaControllerDoc {

    private final PautaService pautaService;

    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PautaDto> criarPauta(@Valid @RequestBody PautaDto pauta) {
        PautaDto pautaDto = pautaService.criarPauta(pauta);
        return new ResponseEntity<>(pautaDto, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PautaDto> buscarPauta(@PathVariable("id") Long id) {
        PautaDto pautaDto = pautaService.buscarPautaPeloId(id);
        return new ResponseEntity<>(pautaDto, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PautaDto> atualizarPauta(@PathVariable("id") Long id, @RequestBody PautaDto pautaAtualizar) {
        PautaDto pautaDto = pautaService.atualizarPauta(id, pautaAtualizar);
        return new ResponseEntity<>(pautaDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPauta(@PathVariable("id") Long id) {
        pautaService.deletarPauta(id);
        return ResponseEntity.noContent().build();
    }
}
