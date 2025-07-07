package com.thiago.desafiovotacao.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "PAUTA")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Pauta {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_pauta")
    @SequenceGenerator(name = "seq_pauta", sequenceName = "seq_pauta", allocationSize = 1)
    @Column(name = "ID_PAUTA")
    private Long id;

    @Column(name = "TITULO", nullable = false, length = 120)
    private String titulo;

    @Column(name = "DESCRICAO", nullable = false, length = 200)
    private String descricao;

    @Column(name = "DATA_CRIACAO", nullable = false, updatable = false)
    private LocalDateTime dataCriacao = LocalDateTime.now();

}
