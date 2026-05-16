package org.example.museutrezemaio.model;

import jakarta.persistence.*;

@Entity
@Table(
        uniqueConstraints = {@UniqueConstraint(columnNames = {"nome", "sobrenome", "tipo"}) }
)
public class Colaborador {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_colaborador;
    @Column(nullable=false)
    private String nome;
    @Column(nullable=false)
    private String sobrenome;
    private String nacionalidade;
    @Column(nullable=false)
    private String tipo;

    @Column(nullable = false)
    private boolean ativo = true;
}
