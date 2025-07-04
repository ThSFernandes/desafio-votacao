package com.thiago.desafiovotacao.repository;

import com.thiago.desafiovotacao.model.entity.Pauta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PautaRepository extends JpaRepository<Pauta, Long> {
}
