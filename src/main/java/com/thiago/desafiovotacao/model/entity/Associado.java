package com.thiago.desafiovotacao.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Associado {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_associado")
    @SequenceGenerator(name = "seq_associado", sequenceName = "seq_associado", allocationSize = 1)
    @Column(name = "ID_ASSOCIADO")
    private Long id;

    @Column(name = "NOME")
    private String nome;

    @Column(name = "CPF")
    private String cpf;

}
