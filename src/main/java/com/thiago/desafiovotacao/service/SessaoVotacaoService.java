package com.thiago.desafiovotacao.service;

import com.thiago.desafiovotacao.model.dtos.CriacaoSessaoVotacaoDto;
import com.thiago.desafiovotacao.model.dtos.SessaoVotacaoDto;
import com.thiago.desafiovotacao.model.entity.Pauta;
import com.thiago.desafiovotacao.model.entity.SessaoVotacao;
import com.thiago.desafiovotacao.model.mapper.SessaoVotacaoMapper;
import com.thiago.desafiovotacao.repository.PautaRepository;
import com.thiago.desafiovotacao.repository.SessaoVotacaoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class SessaoVotacaoService {

    private static final int DURACAO_EM_MINUTOS = 1;
    private final SessaoVotacaoRepository sessaoRepository;
    private final PautaRepository pautaRepository;
    private final SessaoVotacaoMapper mapper;

    public SessaoVotacaoDto criarSessaoVotacao(Long idPauta, CriacaoSessaoVotacaoDto dto) {

        Pauta pauta = pautaRepository.findById(idPauta)
                .orElseThrow(() -> new EntityNotFoundException("Pauta não encontrada (id=" + idPauta + ")"));

        int duracao = DURACAO_EM_MINUTOS;
        Integer minutos = dto.getDuracaoMinutos();
        if (minutos != null && minutos > 0) {
            duracao = minutos;
        }

        LocalDateTime inicio = LocalDateTime.now();
        LocalDateTime termino = inicio.plusMinutes(duracao);

        SessaoVotacao sessao = mapper.sessaodtoParaSessaoVotacao(dto);
        sessao.setDataDeCriacao(inicio);
        sessao.setDataDeTermino(termino);
        sessao.setPauta(pauta);

        return mapper.sessaoVotacaoParaSessaoDto(sessaoRepository.save(sessao));
    }

    public SessaoVotacaoDto buscarSessaoPorId(Long sessaoId) {

        SessaoVotacao entidade = sessaoRepository.findById(sessaoId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Sessão de votação não encontrada (id=" + sessaoId + ")"));

        return mapper.sessaoVotacaoParaSessaoDto(entidade);
    }
}

