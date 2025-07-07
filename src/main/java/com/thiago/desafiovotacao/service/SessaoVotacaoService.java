package com.thiago.desafiovotacao.service;

import com.thiago.desafiovotacao.exception.RecursoNaoEncontradoException;
import com.thiago.desafiovotacao.model.dtos.CriacaoSessaoVotacaoDto;
import com.thiago.desafiovotacao.model.dtos.ResultadoSessaoDto;
import com.thiago.desafiovotacao.model.dtos.SessaoVotacaoDto;
import com.thiago.desafiovotacao.model.entity.Pauta;
import com.thiago.desafiovotacao.model.entity.SessaoVotacao;
import com.thiago.desafiovotacao.model.enums.StatusVotacao;
import com.thiago.desafiovotacao.model.enums.TipoVoto;
import com.thiago.desafiovotacao.model.mapper.SessaoVotacaoMapper;
import com.thiago.desafiovotacao.repository.PautaRepository;
import com.thiago.desafiovotacao.repository.SessaoVotacaoRepository;
import com.thiago.desafiovotacao.repository.VotoRepository;
import jakarta.transaction.Transactional;
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
    private final VotoRepository votoRepository;

    @Transactional
    public SessaoVotacaoDto criarSessaoVotacao(Long idPauta, CriacaoSessaoVotacaoDto dto) {
        log.info("Iniciando criação de sessão de votação para pauta ID={}", idPauta);

        Pauta pauta = pautaRepository.findById(idPauta)
                .orElseThrow(() -> {
                    log.warn("Pauta não encontrada para ID={}", idPauta);
                    return new RecursoNaoEncontradoException("Pauta não encontrada (id=" + idPauta + ")");
                });

        int duracao = DURACAO_EM_MINUTOS;
        Integer minutos = dto.getDuracaoMinutos();
        if (minutos != null) {
            duracao = minutos;
        }

        LocalDateTime inicio = LocalDateTime.now();
        LocalDateTime termino = inicio.plusMinutes(duracao);

        SessaoVotacao sessao = mapper.toEntity(dto);
        sessao.setDataDeCriacao(inicio);
        sessao.setDataDeTermino(termino);
        sessao.setPauta(pauta);

        sessao = sessaoRepository.save(sessao);
        log.info("Sessão de votação criada com sucesso. ID={}, Término={}", sessao.getId(), sessao.getDataDeTermino());

        return mapper.toDto(sessao);
    }

    public SessaoVotacaoDto buscarSessaoPorIdDetalhado(Long idSessao) {
        log.info("Buscando sessão de votação detalhada. ID={}", idSessao);
        SessaoVotacao entidade = buscarSessao(idSessao);
        validarStatusDaSessao(entidade);
        return mapper.toDto(entidade);
    }

    public ResultadoSessaoDto buscarSessaoPorIdParaResultado(Long idSessao) {
        log.info("Buscando resultado da sessão de votação. ID={}", idSessao);
        SessaoVotacao sessao = buscarSessao(idSessao);
        validarStatusDaSessao(sessao);

        long votosSim = buscarQuantidadePorTipo(sessao, TipoVoto.SIM);
        long votosNao = buscarQuantidadePorTipo(sessao, TipoVoto.NAO);
        long totalVotos = votosSim + votosNao;

        log.info("Resultado parcial: SIM={}, NAO={}, TOTAL={}", votosSim, votosNao, totalVotos);

        return mapper.toResultadoDto(sessao, votosSim, votosNao, totalVotos, sessao.getStatusVotacao());
    }

    public void validarStatusDaSessao(SessaoVotacao sessaoVotacao) {
        if (sessaoVotacao.getStatusVotacao() == StatusVotacao.EM_ANDAMENTO &&
                LocalDateTime.now().isAfter(sessaoVotacao.getDataDeTermino())) {
            log.info("Sessão de votação ID={} expirou. Iniciando apuração...", sessaoVotacao.getId());
            apurarResultado(sessaoVotacao);
        }
    }

    public void apurarResultado(SessaoVotacao sessao) {
        long votosSim = buscarQuantidadePorTipo(sessao, TipoVoto.SIM);
        long votosNao = buscarQuantidadePorTipo(sessao, TipoVoto.NAO);

        StatusVotacao statusFinal;

        if (votosSim > votosNao) {
            statusFinal = StatusVotacao.APROVADO;
        } else if (votosNao > votosSim) {
            statusFinal = StatusVotacao.REPROVADO;
        } else {
            statusFinal = StatusVotacao.EMPATE;
        }

        sessao.setStatusVotacao(statusFinal);
        sessaoRepository.save(sessao);

        log.info("Apuração concluída para sessão ID={}. Resultado: {}", sessao.getId(), statusFinal);
    }

    private SessaoVotacao buscarSessao(Long idSessao) {
        return sessaoRepository.findById(idSessao)
                .orElseThrow(() -> {
                    log.warn("Sessão de votação não encontrada. ID={}", idSessao);
                    return new RecursoNaoEncontradoException("Sessão de votação não encontrada (id=" + idSessao + ")");
                });
    }

    private long buscarQuantidadePorTipo(SessaoVotacao sessao, TipoVoto tipoVoto) {
        return votoRepository.countBySessaoVotacaoAndTipoVoto(sessao, tipoVoto);
    }
}
