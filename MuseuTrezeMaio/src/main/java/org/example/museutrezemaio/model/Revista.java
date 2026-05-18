package org.example.museutrezemaio.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Revista extends ItemAcervo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_revista;

    @Column(unique = true, nullable = false)
    private String codigo_revista;

    private String volume;

    private String periodicidade;

    private int ano;

    private int mes;



}
