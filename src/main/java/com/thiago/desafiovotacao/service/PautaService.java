package com.thiago.desafiovotacao.service;

import com.thiago.desafiovotacao.model.dtos.PautaDto;
import com.thiago.desafiovotacao.model.entity.Pauta;
import com.thiago.desafiovotacao.model.mapper.PautaMapper;
import com.thiago.desafiovotacao.repository.PautaRepository;
import jakarta.persistence.EntityNotFoundException;
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
        Pauta pauta = mapper.pautaDtoParaPauta(pautaDto);
        pautaRepository.save(pauta);
        log.info("Pauta criada (id={})", pauta.getId());
        return mapper.pautaParaPautaDto(pauta);
    }

    public PautaDto buscarPautaPeloId(Long id) {
        return pautaRepository.findById(id).map(mapper::pautaParaPautaDto)
                .orElseThrow(() -> new EntityNotFoundException("Pauta não encontrada (id=" + id + ")"));
    }

    public PautaDto atualizarPauta(Long id, PautaDto pautaDto) {
        Pauta pautaRecuperada = buscarEntidadePorId(id);
        pautaRecuperada.setTitulo(pautaDto.getTitulo());
        pautaRecuperada.setDescricao(pautaDto.getDescricao());
        log.info("Pauta atualizada (id={})", id);

        return mapper.pautaParaPautaDto(pautaRepository.save(pautaRecuperada));
    }

    public void deletarPauta(Long id) {
        pautaRepository.delete(buscarEntidadePorId(id));
    }

    private Pauta buscarEntidadePorId(Long id) {
        return pautaRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Pauta não encontrada (id=" + id + ")"));
    }
}
