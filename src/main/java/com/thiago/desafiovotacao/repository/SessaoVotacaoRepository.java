package com.thiago.desafiovotacao.repository;

import com.thiago.desafiovotacao.model.entity.SessaoVotacao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessaoVotacaoRepository extends JpaRepository<SessaoVotacao, Long> {
}
