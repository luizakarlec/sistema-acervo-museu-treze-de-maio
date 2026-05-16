package org.example.museutrezemaio.model;

import jakarta.persistence.*;

@Entity
public class Assunto{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_assunto;

    @Column(unique=true, nullable=false)
    private String descricao;

    @Column(nullable = false)
    private boolean ativo = true;

}
