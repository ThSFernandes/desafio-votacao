package com.thiago.desafiovotacao.service;

import com.thiago.desafiovotacao.exception.BusinessException;
import com.thiago.desafiovotacao.exception.RecursoNaoEncontradoException;
import com.thiago.desafiovotacao.model.dtos.AssociadoDto;
import com.thiago.desafiovotacao.model.entity.Associado;
import com.thiago.desafiovotacao.model.mapper.AssociadoMapper;
import com.thiago.desafiovotacao.repository.AssociadoRepository;
import com.thiago.desafiovotacao.repository.VotoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AssociadoService {

    private final AssociadoRepository associadoRepository;
    private final AssociadoMapper mapper;
    private final VotoRepository votoRepository;

    public AssociadoDto criarAssociado(AssociadoDto dto) {
        log.info("Iniciando criação de associado.");

        Associado entidade = mapper.toEntity(dto);
        entidade = associadoRepository.save(entidade);

        log.info("Associado criado com sucesso. ID={}", entidade.getId());

        return mapper.toDto(entidade);
    }

    public AssociadoDto buscarAssociado(Long id) {
        log.info("Buscando associado por ID={}", id);

        return associadoRepository.findById(id)
                .map(associado -> {
                    log.info("Associado encontrado. ID={}", id);
                    return mapper.toDto(associado);
                })
                .orElseThrow(() -> {
                    log.warn("Associado não encontrado. ID={}", id);
                    return new RecursoNaoEncontradoException("Associado não encontrado (id=" + id + ")");
                });
    }

    public void deletarAssociado(Long id) {
        log.info("Solicitação de exclusão do associado. ID={}", id);

        if (!associadoRepository.existsById(id)) {
            log.warn("Tentativa de deletar associado inexistente. ID={}", id);
            throw new RecursoNaoEncontradoException("Associado não encontrado (id=" + id + ")");
        }

        if (votoRepository.existsByAssociadoId(id)) {
            log.warn("Tentativa de remover um associado que votou id=" + id );
            throw new BusinessException("Não é possível remover o associado pois ele possui votos registrados.");
        }

        associadoRepository.deleteById(id);
        log.info("Associado deletado com sucesso. ID={}", id);
    }
}

