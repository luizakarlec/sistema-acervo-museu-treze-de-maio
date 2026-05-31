package org.example.museutrezemaio.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;

@Embeddable
public class Localizacao {

    private String cidade;

    private String estado;

    private String pais;

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getLocalizacaoFormatada() {

        boolean temCidade = cidade != null && !cidade.isBlank();
        boolean temEstado = estado != null && !estado.isBlank();

        if (temCidade && temEstado) {
            return cidade + " (" + estado + ")";
        }

        if (temCidade) {
            return cidade;
        }

        if (temEstado) {
            return estado;
        }

        return "";
    }
}
