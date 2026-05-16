package org.example.museutrezemaio.model;

import jakarta.persistence.*;

@Entity
@Table(
        uniqueConstraints = { @UniqueConstraint(columnNames = {"nome", "cidade", "estado", "pais"} ) }
)
public class Editora {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_editora;

    @Column(nullable=false)
    private String nome;

    @Embedded
    private Localizacao localizacao;

    @Column(nullable = false)
    private boolean ativo = true;

}
