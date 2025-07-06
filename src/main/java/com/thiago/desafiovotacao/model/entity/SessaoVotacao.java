package com.thiago.desafiovotacao.model.entity;

import com.thiago.desafiovotacao.model.enums.StatusVotacao;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "SESSAO_VOTACAO")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SessaoVotacao {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_sessao")
    @SequenceGenerator(name = "seq_sessao", sequenceName = "seq_sessao", allocationSize = 1)
    @Column(name = "ID_SESSAO_VOTOS")
    private Long id;

    @Column(name = "DATA_INICIO_VOTACAO")
    private LocalDateTime dataDeCriacao;

    @Column(name = "DATA_TERMINO_VOTACAO")
    private LocalDateTime dataDeTermino;

    @Enumerated(EnumType.STRING)
    private StatusVotacao statusVotacao = StatusVotacao.EM_ANDAMENTO;

    @ManyToOne
    @JoinColumn(name = "ID_PAUTA", referencedColumnName = "ID_PAUTA")
    private Pauta pauta;


    @OneToMany(mappedBy = "sessaoVotacao", fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Voto> votos;


}
