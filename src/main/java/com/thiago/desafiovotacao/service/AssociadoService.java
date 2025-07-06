package com.thiago.desafiovotacao.service;

import com.thiago.desafiovotacao.model.dtos.AssociadoDto;
import com.thiago.desafiovotacao.model.entity.Associado;
import com.thiago.desafiovotacao.model.mapper.AssociadoMapper;
import com.thiago.desafiovotacao.repository.AssociadoRepository;
import jakarta.persistence.EntityNotFoundException;
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
        Associado entidade = mapper.associadoDtoParaAssociado(dto);
        entidade = associadoRepository.save(entidade);
        return mapper.associadoParaAssociadoDto(entidade);
    }

    public AssociadoDto buscarAssociado(Long id) {
        return associadoRepository.findById(id)
                .map(mapper::associadoParaAssociadoDto)
                .orElseThrow(() ->
                        new EntityNotFoundException("Associado não encontrado (id=" + id + ")"));
    }

    public void deletarAssociado(Long id) {
        if (!associadoRepository.existsById(id)) {
            throw new EntityNotFoundException("Associado não encontrado (id=" + id + ")");
        }
        associadoRepository.deleteById(id);
        log.info("Associado deletado com sucesso ! (id=" + id + ")");
    }

}
