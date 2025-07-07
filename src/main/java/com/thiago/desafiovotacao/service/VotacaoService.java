package com.thiago.desafiovotacao.service;

import com.thiago.desafiovotacao.exception.RecursoNaoEncontradoException;
import com.thiago.desafiovotacao.exception.SessaoException;
import com.thiago.desafiovotacao.model.dtos.CriarVotacaoDto;
import com.thiago.desafiovotacao.model.dtos.ItemVotoDto;
import com.thiago.desafiovotacao.model.dtos.VotacaoDto;
import com.thiago.desafiovotacao.model.dtos.VotoDetalhadoDto;
import com.thiago.desafiovotacao.model.entity.Associado;
import com.thiago.desafiovotacao.model.entity.SessaoVotacao;
import com.thiago.desafiovotacao.model.entity.Voto;
import com.thiago.desafiovotacao.model.enums.StatusVotacao;
import com.thiago.desafiovotacao.model.mapper.VotoMapper;
import com.thiago.desafiovotacao.repository.AssociadoRepository;
import com.thiago.desafiovotacao.repository.SessaoVotacaoRepository;
import com.thiago.desafiovotacao.repository.VotoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class VotacaoService {

    private final VotoRepository votoRepository;
    private final SessaoVotacaoRepository sessaoRepository;
    private final AssociadoRepository associadoRepository;
    private final VotoMapper mapper;
    private final SessaoVotacaoService sessaoVotacaoService;

    @Transactional
    public VotacaoDto criarVoto(Long idSessao, Long idAssociado, CriarVotacaoDto dto) {
        log.info("Iniciando registro de voto. SessãoID={}, AssociadoID={}", idSessao, idAssociado);

        SessaoVotacao sessao = buscarSessao(idSessao);
        verificarStatusDaSessao(sessao);

        Associado associado = buscarAssociado(idAssociado);

        if (votoRepository.existsByAssociadoAndSessaoVotacao(associado, sessao)) {
            log.warn("Associado já votou nesta sessão. SessãoID={}, AssociadoID={}", idSessao, idAssociado);
            throw new SessaoException("Associado já registrou voto nesta sessão.");
        }

        Voto voto = mapper.toEntity(dto);
        voto.setAssociado(associado);
        voto.setSessaoVotacao(sessao);
        voto = votoRepository.save(voto);

        log.info("Voto registrado com sucesso. VotoID={}, SessãoID={}, AssociadoID={}",
                voto.getId(), idSessao, idAssociado);

        return mapper.toDto(voto);
    }

    public VotacaoDto buscarVotoPorId(Long votoId) {
        log.info("Buscando voto por ID. VotoID={}", votoId);

        Voto voto = votoRepository.findById(votoId)
                .orElseThrow(() -> {
                    log.warn("Voto não encontrado. VotoID={}", votoId);
                    return new RecursoNaoEncontradoException("Voto não encontrado (id=" + votoId + ")");
                });

        return mapper.toDto(voto);
    }

    public VotoDetalhadoDto listarVotosDoAssociado(Long idAssociado) {
        log.info("Listando votos do associado. AssociadoID={}", idAssociado);

        Associado associado = buscarAssociado(idAssociado);
        List<Voto> votos = votoRepository.findByAssociado(associado);
        List<ItemVotoDto> itens = mapper.toItemDtoList(votos);

        log.info("Total de votos encontrados para AssociadoID={}: {}", idAssociado, itens.size());

        return new VotoDetalhadoDto(associado.getId(), associado.getNome(), itens);
    }

    private SessaoVotacao buscarSessao(Long id) {
        return sessaoRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Sessão não encontrada. SessãoID={}", id);
                    return new RecursoNaoEncontradoException("Sessão não encontrada (id=" + id + ")");
                });
    }

    private Associado buscarAssociado(Long id) {
        return associadoRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Associado não encontrado. AssociadoID={}", id);
                    return new RecursoNaoEncontradoException("Associado não encontrado (id=" + id + ")");
                });
    }

    private void verificarStatusDaSessao(SessaoVotacao sessao) {
        boolean statusAberto = sessao.getStatusVotacao() == StatusVotacao.EM_ANDAMENTO;

        if (!statusAberto) {
            log.warn("Tentativa de votar em sessão encerrada. SessãoID={}", sessao.getId());
            throw new SessaoException("Sessão encerrada; consulte o resultado.");
        }

        LocalDateTime termino = sessao.getDataDeTermino();

        if (termino != null && LocalDateTime.now().isAfter(termino)) {
            log.info("Sessão expirada. Apurando resultado automaticamente. SessãoID={}", sessao.getId());
            sessaoVotacaoService.apurarResultado(sessao);
            throw new SessaoException("Sessão encerrada; consulte o resultado.");
        }
    }
}
