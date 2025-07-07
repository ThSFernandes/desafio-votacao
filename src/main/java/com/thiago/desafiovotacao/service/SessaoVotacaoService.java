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

        Pauta pauta = pautaRepository.findById(idPauta)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Pauta não encontrada (id=" + idPauta + ")"));

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

    public SessaoVotacaoDto buscarSessaoPorIdDetalhado(Long idSessao) {
        SessaoVotacao entidade = buscarSessao(idSessao);

        validarStatusDaSessao(entidade);
        return mapper.sessaoVotacaoParaSessaoDto(entidade);
    }

    public ResultadoSessaoDto buscarSessaoPorIdParaResultado(Long idSessao) {
        SessaoVotacao sessao = buscarSessao(idSessao);

        validarStatusDaSessao(sessao);

        long votosSim = buscarQuantidadePorTipo(sessao, TipoVoto.SIM);
        long votosNao = buscarQuantidadePorTipo(sessao, TipoVoto.NAO);
        long totalVotos = votosSim + votosNao;

        return mapper.toResultadoSessaoDto(sessao, votosSim, votosNao, totalVotos, sessao.getStatusVotacao());
    }

    public void validarStatusDaSessao(SessaoVotacao sessaoVotacao) {
        if (sessaoVotacao.getStatusVotacao() == StatusVotacao.EM_ANDAMENTO &&
                LocalDateTime.now().isAfter(sessaoVotacao.getDataDeTermino())) {
            apurarResultado(sessaoVotacao);
        }
    }

    public void apurarResultado(SessaoVotacao sessao) {
        long votosSim = buscarQuantidadePorTipo(sessao, TipoVoto.SIM);
        long votosNao = buscarQuantidadePorTipo(sessao, TipoVoto.NAO);

        if (votosSim > votosNao) {
            sessao.setStatusVotacao(StatusVotacao.APROVADO);
        } else if (votosNao > votosSim) {
            sessao.setStatusVotacao(StatusVotacao.REPROVADO);
        } else {
            sessao.setStatusVotacao(StatusVotacao.EMPATE);
        }
        sessaoRepository.save(sessao);

    }

    private SessaoVotacao buscarSessao(Long idSessao) {
        return sessaoRepository.findById(idSessao)
                .orElseThrow(() ->
                        new RecursoNaoEncontradoException("Sessão de votação não encontrada (id=" + idSessao + ")"));
    }

    private long buscarQuantidadePorTipo(SessaoVotacao sessao, TipoVoto tipoVoto) {
        return votoRepository.countBySessaoVotacaoAndTipoVoto(sessao, tipoVoto);
    }


}

