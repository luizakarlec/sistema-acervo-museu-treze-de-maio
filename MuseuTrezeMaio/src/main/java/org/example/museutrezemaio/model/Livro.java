package org.example.museutrezemaio.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Livro extends ItemAcervo {

    @Column(unique = true)
    private String isbn;

    @NotBlank(message = "O campo código do livro é obrigatório")
    @Column(nullable = false, unique = true)
    private String codigoLivro;

    @Min(value = 1, message = "O ano de publicação deve ser informado")
    @Column(nullable = false)
    private int anoPublicacao;

    private boolean ilustrado = false;

    @Column(nullable = false)
    private boolean ativo = true;

    public Livro() {
        super();
        this.setTipo("Livro");
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getCodigoLivro() {
        return codigoLivro;
    }

    public void setCodigoLivro(String codigoLivro) {
        this.codigoLivro = codigoLivro;
    }

    public int getAnoPublicacao() {
        return anoPublicacao;
    }

    public void setAnoPublicacao(int anoPublicacao) {
        this.anoPublicacao = anoPublicacao;
    }

    public boolean isIlustrado() {
        return ilustrado;
    }

    public void setIlustrado(boolean ilustrado) {
        this.ilustrado = ilustrado;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    @Override
    public String getCodigoExclusivo() {
        return this.codigoLivro;
    }
}
