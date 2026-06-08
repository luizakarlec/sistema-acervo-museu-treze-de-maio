package org.example.museutrezemaio.model;

import jakarta.persistence.*;

@Entity
public class Exemplar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_exemplar;

    @Column(unique = true, nullable = false)
    private String codigo;

    @ManyToOne
    @JoinColumn(name = "id_jornal")
    private Jornal jornal;

    @ManyToOne
    @JoinColumn(name = "id_revista")
    private Revista revista;

    @ManyToOne
    @JoinColumn(name = "id_livro")
    private Livro livro;

    @ManyToOne
    @JoinColumn(name = "id_acervo")
    private ItemAcervo item_acervo;
}
