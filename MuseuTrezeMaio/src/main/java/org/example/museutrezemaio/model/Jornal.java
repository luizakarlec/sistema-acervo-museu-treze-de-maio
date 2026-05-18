package org.example.museutrezemaio.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Jornal extends ItemAcervo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_jornal;

    @Column(nullable = false, unique=true)
    private String codigo_jornal;
    private Date data;


    @Embedded
    private Localizacao localizacao;

    @Column(nullable = false)
    private boolean ativo = true;

}
