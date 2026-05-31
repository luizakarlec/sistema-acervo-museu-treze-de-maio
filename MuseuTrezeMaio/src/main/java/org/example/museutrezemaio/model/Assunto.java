package org.example.museutrezemaio.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Assunto{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idAssunto;

    @NotBlank(message = "O campo descrição é obrigatório")
    @Column(unique=true, nullable=false)
    private String descricao;

    private boolean ativo = true;

    public Assunto() {
    }

    public Assunto(int idAssunto, String descricao, boolean ativo) {
        this.idAssunto = idAssunto;
        this.descricao = descricao;
        this.ativo = ativo;
    }

    public Assunto(String descricao, boolean ativo) {
        this.descricao = descricao;
        this.ativo = ativo;
    }

    public int getIdAssunto() {
        return idAssunto;
    }

    public void setIdAssunto(int idAssunto) {
        this.idAssunto = idAssunto;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    @Override
    public String toString() {
        return "Assunto{" +
                "idAssunto=" + idAssunto +
                ", descricao='" + descricao + '\'' +
                ", ativo=" + ativo +
                '}';
    }
}
