package com.thiago.desafiovotacao.service;

import com.thiago.desafiovotacao.model.dtos.CriarVotacaoDto;
import com.thiago.desafiovotacao.model.dtos.VotacaoDto;
import com.thiago.desafiovotacao.model.entity.Associado;
import com.thiago.desafiovotacao.model.entity.SessaoVotacao;
import com.thiago.desafiovotacao.model.entity.Voto;
import com.thiago.desafiovotacao.model.enums.StatusVotacao;
import com.thiago.desafiovotacao.model.mapper.VotoMapper;
import com.thiago.desafiovotacao.repository.AssociadoRepository;
import com.thiago.desafiovotacao.repository.SessaoVotacaoRepository;
import com.thiago.desafiovotacao.repository.VotoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class VotacaoService {

    private final VotoRepository votoRepository;
    private final SessaoVotacaoRepository sessaoRepository;
    private final AssociadoRepository associadoRepository;
    private final VotoMapper mapper;


    public VotacaoDto criarVoto(Long sessaoId, Long associadoId, CriarVotacaoDto dto) {

        SessaoVotacao sessao = sessaoRepository.findById(sessaoId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Sessão não encontrada (id=" + sessaoId + ")"));

        Associado associado = associadoRepository.findById(associadoId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Associado não encontrado (id=" + associadoId + ")"));

        boolean sessaoFechadaPeloStatus =
                sessao.getStatusVotacao() != StatusVotacao.EM_ANDAMENTO;

        boolean sessaoFechadaPeloHorario =
                LocalDateTime.now().isAfter(sessao.getDataDeTermino());

        if (sessaoFechadaPeloStatus || sessaoFechadaPeloHorario) {
            throw new IllegalStateException("Sessão encerrada para votação.");
        }

        if (votoRepository.existsByAssociadoAndSessaoVotacao(associado, sessao)) {
            throw new IllegalStateException("Associado já registrou voto nesta sessão.");
        }

        Voto voto = mapper.votacaoDtoParaVoto(dto);
        voto.setAssociado(associado);
        voto.setSessaoVotacao(sessao);
        voto = votoRepository.save(voto);
        log.info("Voto salvo (id={})", voto.getId());

        return mapper.votoParaVotacaoDto(voto);
    }

    public VotacaoDto buscarVotoPorId(Long votoId) {
        Voto voto = votoRepository.findById(votoId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Voto não encontrado (id=" + votoId + ")"));
        return mapper.votoParaVotacaoDto(voto);
    }

    public void deletarVoto(Long votoId) {
        votoRepository.deleteById(votoId);
    }
}
