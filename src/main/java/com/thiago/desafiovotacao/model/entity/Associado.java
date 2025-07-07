package com.thiago.desafiovotacao.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ASSOCIADO")
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

    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 50, message = "Nome pode ter no máximo 50 caracteres")
    @Column(name = "NOME", nullable = false, length = 50)
    private String nome;

    @Column(name = "CPF", nullable = false, length = 11)
    private String cpf;

}
