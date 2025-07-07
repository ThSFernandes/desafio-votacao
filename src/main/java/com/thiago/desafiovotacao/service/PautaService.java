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
        Pauta pauta = mapper.toEntity(pautaDto);
        pautaRepository.save(pauta);
        log.info("Pauta criada (id={})", pauta.getId());
        return mapper.toDto(pauta);
    }

    public PautaDto buscarPautaPeloId(Long id) {
        return pautaRepository.findById(id).map(mapper::toDto)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Pauta não encontrada (id=" + id + ")"));
    }

    public PautaDto atualizarPauta(Long id, PautaDto pautaDto) {
        Pauta pautaRecuperada = buscarEntidadePorId(id);
        pautaRecuperada.setTitulo(pautaDto.getTitulo());
        pautaRecuperada.setDescricao(pautaDto.getDescricao());
        log.info("Pauta atualizada (id={})", id);

        return mapper.toDto(pautaRepository.save(pautaRecuperada));
    }

    public void deletarPauta(Long id) {
        if (!pautaRepository.existsById(id)) {
            throw new RecursoNaoEncontradoException("Pauta não encontrado (id=" + id + ")");
        }
        pautaRepository.deleteById(id);
        log.info("Pauta deletada com sucesso ! (id=" + id + ")");
    }

    private Pauta buscarEntidadePorId(Long id) {
        return pautaRepository.findById(id)
                .orElseThrow(() ->
                        new RecursoNaoEncontradoException("Pauta não encontrada (id=" + id + ")"));
    }
}
