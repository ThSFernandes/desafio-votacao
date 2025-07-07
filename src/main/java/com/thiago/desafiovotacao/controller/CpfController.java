package com.thiago.desafiovotacao.controller;

import com.thiago.desafiovotacao.controller.doc.CpfControllerDoc;
import com.thiago.desafiovotacao.cpf.dto.ValidadorCpfDto;
import com.thiago.desafiovotacao.service.CpfService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cpf")
@Validated
public class CpfController implements CpfControllerDoc {

    private final CpfService cpfService;

    @GetMapping("/{cpf}")
    public ResponseEntity<ValidadorCpfDto> verificaCpf(@PathVariable("cpf") String cpf) {
        ValidadorCpfDto validadorCpfDto = cpfService.verificaPermissaoDeVoto(cpf);
        return new ResponseEntity<>(validadorCpfDto, HttpStatus.OK);
    }
}
