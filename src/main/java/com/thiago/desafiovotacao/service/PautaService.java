package com.thiago.desafiovotacao.service;

import com.thiago.desafiovotacao.exception.RecursoNaoEncontradoException;
import com.thiago.desafiovotacao.model.dtos.PautaDto;
import com.thiago.desafiovotacao.model.entity.Pauta;
import com.thiago.desafiovotacao.model.mapper.PautaMapper;
import com.thiago.desafiovotacao.repository.PautaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PautaService {

    private final PautaRepository pautaRepository;
    private final PautaMapper mapper;

    public PautaDto criarPauta(PautaDto pautaDto) {
        log.info("Iniciando criação de pauta.");

        Pauta pauta = mapper.toEntity(pautaDto);
        pauta = pautaRepository.save(pauta);

        log.info("Pauta criada com sucesso. ID={}", pauta.getId());
        return mapper.toDto(pauta);
    }

    public PautaDto buscarPautaPeloId(Long id) {
        log.info("Buscando pauta por ID={}", id);

        return pautaRepository.findById(id)
                .map(pauta -> {
                    log.info("Pauta encontrada. ID={}", id);
                    return mapper.toDto(pauta);
                })
                .orElseThrow(() -> {
                    log.warn("Pauta não encontrada. ID={}", id);
                    return new RecursoNaoEncontradoException("Pauta não encontrada (id=" + id + ")");
                });
    }

    public PautaDto atualizarPauta(Long id, PautaDto pautaDto) {
        log.info("Atualizando pauta. ID={}", id);

        Pauta pautaRecuperada = buscarEntidadePorId(id);
        pautaRecuperada.setTitulo(pautaDto.getTitulo());
        pautaRecuperada.setDescricao(pautaDto.getDescricao());

        pautaRecuperada = pautaRepository.save(pautaRecuperada);
        log.info("Pauta atualizada com sucesso. ID={}", id);

        return mapper.toDto(pautaRecuperada);
    }

    public void deletarPauta(Long id) {
        log.info("Solicitação de exclusão da pauta. ID={}", id);

        if (!pautaRepository.existsById(id)) {
            log.warn("Tentativa de deletar pauta inexistente. ID={}", id);
            throw new RecursoNaoEncontradoException("Pauta não encontrada (id=" + id + ")");
        }

        pautaRepository.deleteById(id);
        log.info("Pauta deletada com sucesso. ID={}", id);
    }

    private Pauta buscarEntidadePorId(Long id) {
        return pautaRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Pauta não encontrada ao tentar buscar entidade. ID={}", id);
                    return new RecursoNaoEncontradoException("Pauta não encontrada (id=" + id + ")");
                });
    }
}

