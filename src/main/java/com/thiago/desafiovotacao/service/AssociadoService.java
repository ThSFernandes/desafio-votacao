package com.thiago.desafiovotacao.service;

import com.thiago.desafiovotacao.exception.RecursoNaoEncontradoException;
import com.thiago.desafiovotacao.model.dtos.AssociadoDto;
import com.thiago.desafiovotacao.model.entity.Associado;
import com.thiago.desafiovotacao.model.mapper.AssociadoMapper;
import com.thiago.desafiovotacao.repository.AssociadoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j

public class AssociadoService {

    private final AssociadoRepository associadoRepository;
    private final AssociadoMapper mapper;

    public AssociadoDto criarAssociado(AssociadoDto dto) {
        log.info("DTO do associado recebido: {}", dto);
        Associado entidade = mapper.toEntity(dto);
        entidade = associadoRepository.save(entidade);
        return mapper.toDto(entidade);
    }

    public AssociadoDto buscarAssociado(Long id) {
        return associadoRepository.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() ->
                        new RecursoNaoEncontradoException("Associado não encontrado (id=" + id + ")"));
    }

    public void deletarAssociado(Long id) {
        if (!associadoRepository.existsById(id)) {
            throw new RecursoNaoEncontradoException("Associado não encontrado (id=" + id + ")");
        }
        associadoRepository.deleteById(id);
        log.info("Associado deletado com sucesso ! (id=" + id + ")");
    }

}
