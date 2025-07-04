package com.thiago.desafiovotacao.repository;

import com.thiago.desafiovotacao.model.entity.Associado;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssociadoRepository extends JpaRepository<Associado, Long> {
}
