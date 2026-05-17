package org.example.museutrezemaio.model;

import jakarta.persistence.*;

import java.util.Date;

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
    //@Embedded
    //private Exemplar exemplar;

    @Column(nullable = false)
    private boolean ativo = true;
}
