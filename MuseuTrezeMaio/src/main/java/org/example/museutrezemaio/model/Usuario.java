package org.example.museutrezemaio.model;

import jakarta.persistence.*;

@Entity
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_usuario;
    @Column(nullable=false)
    private String nome;
    @Column(nullable=false)
    private String sobrenome;
    @Column(nullable=false, unique=true)
    private String cpf;
    @Column(nullable=false)
    private String email;
    @Column(nullable=false)
    private String tipo;
    //senha

    @Column(nullable = false)
    private boolean ativo = true;
}
