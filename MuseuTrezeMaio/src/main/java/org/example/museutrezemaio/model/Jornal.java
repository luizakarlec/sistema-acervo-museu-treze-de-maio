package org.example.museutrezemaio.model;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Jornal extends ItemAcervo {

    @Column(nullable = false, unique=true)
    private String codigoJornal;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date data;

    @Embedded
    private Localizacao localizacao;

    @Column(nullable = false)
    private boolean ativo = true;

    public String getCodigoJornal() {
        return codigoJornal;
    }

    public void setCodigoJornal(String codigoJornal) {
        this.codigoJornal = codigoJornal;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Localizacao getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(Localizacao localizacao) {
        this.localizacao = localizacao;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
}
