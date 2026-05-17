package org.example.museutrezemaio.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class Localizacao {
    private String cidade;

    private String estado;

    @Column(nullable = false)
    private String pais;
}
