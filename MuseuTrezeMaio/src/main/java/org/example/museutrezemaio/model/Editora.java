package org.example.museutrezemaio.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(
        uniqueConstraints = { @UniqueConstraint(columnNames = {"nome", "cidade", "estado"} ) }
)
public class Editora {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idEditora;

    @NotBlank(message = "O campo nome é obrigatório")
    @Column(nullable=false)
    private String nome;

    @Embedded
    private Localizacao localizacao = new Localizacao();

    @Column(nullable = false)
    private boolean ativo = true;

    public Editora() {
    }

    public Editora(int idEditora, String nome, Localizacao localizacao, boolean ativo) {
        this.idEditora = idEditora;
        this.nome = nome;
        this.localizacao = localizacao;
        this.ativo = ativo;
    }

    public Editora(String nome, Localizacao localizacao, boolean ativo) {
        this.nome = nome;
        this.localizacao = localizacao;
        this.ativo = ativo;
    }

    public int getIdEditora() {
        return idEditora;
    }

    public void setIdEditora(int idEditora) {
        this.idEditora = idEditora;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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

    @Override
    public String toString() {
        return "Editora{" +
                "idEditora=" + idEditora +
                ", nome='" + nome + '\'' +
                ", localizacao=" + localizacao +
                ", ativo=" + ativo +
                '}';
    }
}
