package org.example.museutrezemaio.model;

import jakarta.persistence.*;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Livro extends ItemAcervo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idLivro;

    private String isbn;

    @Column(nullable = false, unique = true)
    private String codigoLivro;

    private Year anoPublicacao;




}
