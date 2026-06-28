package org.example.museutrezemaio.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.annotations.AttributeAccessor;

import java.util.ArrayList;
import java.util.List;

@Entity
//@AttributeOverride(name = "edicao", column = @Column(name = "numero", nullable = true))
public class Revista extends ItemAcervo {
    @NotBlank(message = "O campo código é obrigatório")
    @Column(nullable = false, unique=true)
    private String codigoRevista;

    @Column(unique=true)
    private String ISSN; //nao obrigatorio

    private String volume;

    private String numero;

    private String periodicidade;

    @Column(nullable = false)
    private int ano;

    private int mes;

    @Column(nullable = false)
    private boolean ativo = true;

    public Revista() {
        super();
        this.setTipo("Revista");
    }

    public String getCodigoRevista() {
        return codigoRevista;
    }

    public void setCodigoRevista(String codigoRevista) {
        this.codigoRevista = codigoRevista;
    }

    public String getISSN() {
        return ISSN;
    }

    public void setISSN(String ISSN) {
        this.ISSN = ISSN;
    }


    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getPeriodicidade() {
        return periodicidade;
    }

    public void setPeriodicidade(String periodicidade) {
        this.periodicidade = periodicidade;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    @Override
    public String getCodigoExclusivo() {
        return this.codigoRevista;
    }
}
