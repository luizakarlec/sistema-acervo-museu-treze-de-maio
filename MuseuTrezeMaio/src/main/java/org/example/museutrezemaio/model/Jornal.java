package org.example.museutrezemaio.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Jornal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_jornal;

    @Column(nullable = false, unique=true)
    private String codigo_jornal;
    private Date data;
    @Column(nullable = false)
    private String titulo;
    private String subtitulo;
    private int quantidade;
    @Column(nullable = false)
    private String edicao;
    @Column(nullable = false)
    private String idioma;
    private int numero_paginas;
    private byte[] capa;
    @Column(nullable = false)
    private String localizacao_acervo;

    @Embedded
    private Localizacao localizacao;

    @Column(nullable = false)
    private boolean ativo = true;

    @ManyToOne
    @JoinColumn(name = "id_editora")
    private Editora editora;

    @ManyToMany
    @JoinTable(
            name = "jornal_colaborador",
            joinColumns = @JoinColumn(name = "id_jornal"),
            inverseJoinColumns = @JoinColumn(name = "id_colaborador")
    )
    private List<Colaborador> colaboradores = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "jornal_assunto",
            joinColumns = @JoinColumn(name = "id_jornal"),
            inverseJoinColumns = @JoinColumn(name = "id_assunto")
    )
    private List<Assunto> assuntos = new ArrayList<>();
}
