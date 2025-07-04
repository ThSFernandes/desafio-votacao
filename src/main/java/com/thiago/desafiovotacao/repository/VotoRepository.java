package com.thiago.desafiovotacao.repository;

import com.thiago.desafiovotacao.model.entity.Voto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VotoRepository extends JpaRepository<Voto, Long> {
}
