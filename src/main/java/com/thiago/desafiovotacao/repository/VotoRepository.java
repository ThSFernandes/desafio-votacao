package com.thiago.desafiovotacao.repository;

import com.thiago.desafiovotacao.model.entity.Associado;
import com.thiago.desafiovotacao.model.entity.SessaoVotacao;
import com.thiago.desafiovotacao.model.entity.Voto;
import com.thiago.desafiovotacao.model.enums.TipoVoto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VotoRepository extends JpaRepository<Voto, Long> {
    boolean existsByAssociadoAndSessaoVotacao(Associado associado, SessaoVotacao sessao);

    long countBySessaoVotacaoAndTipoVoto(SessaoVotacao sessao, TipoVoto tipo);

    List<Voto> findByAssociado(Associado associado);

    boolean existsByAssociadoId(Long idAssociado);
}
