package org.example.museutrezemaio.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Revista {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_revista;

    @Column(unique = true, nullable = false)
    private String codigo_revista;

    @Column(nullable = false)
    private String edicao;

    private String volume;

    private String periodicidade;

    private int ano;

    private int mes;

    @Column(nullable = false)
    private String titulo;

    private String subtitulo;

    private String numero_pagina;

    @Column(nullable = false)
    private String localização_acervo;

    @Column(nullable = false)
    private String idioma;

    private int quantidade;

    private byte[] capa;

    private String tipo;

    private String edicao;

    @ManyToOne
    @JoinColumn(name = "id_editora")
    private Editora editora;

    @ManyToMany
    @JoinTable(
            name = "revista_colaborador",
            joinColumns = @JoinColumn(name = "id_revista"),
            inverseJoinColumns = @JoinColumn(name = "id_colaborador")
    )
    private List<Colaborador> colaboradores = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "revista_assunto",
            joinColumns = @JoinColumn(name = "id_revista"),
            inverseJoinColumns = @JoinColumn(name = "id_assunto")
    )
    private List<Assunto> assuntos = new ArrayList<>();

    @Column(nullable = false)
    private boolean ativo = true;

}
