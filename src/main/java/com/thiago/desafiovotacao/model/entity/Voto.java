package com.thiago.desafiovotacao.model.entity;

import com.thiago.desafiovotacao.model.enums.TipoVoto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Voto {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_voto")
    @SequenceGenerator(name = "seq_voto", sequenceName = "seq_voto", allocationSize = 1)
    @Column(name = "ID_VOTO")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "TIPO_VOTO")
    private TipoVoto tipoVoto;

    @Column(name = "DATA_CRIACAO")
    private LocalDateTime dataCriacao = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "ID_ASSOCIADO", referencedColumnName = "ID_ASSOCIADO")
    private Associado associado;

    @ManyToOne
    @JoinColumn(name = "ID_SESSAO_VOTOS", referencedColumnName = "ID_SESSAO_VOTOS")
    private SessaoVotacao sessaoVotacao;
}
