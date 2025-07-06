package com.thiago.desafiovotacao.repository;

import com.thiago.desafiovotacao.model.entity.Associado;
import com.thiago.desafiovotacao.model.entity.SessaoVotacao;
import com.thiago.desafiovotacao.model.entity.Voto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VotoRepository extends JpaRepository<Voto, Long> {
    boolean existsByAssociadoAndSessaoVotacao(Associado associado, SessaoVotacao sessao);
}
