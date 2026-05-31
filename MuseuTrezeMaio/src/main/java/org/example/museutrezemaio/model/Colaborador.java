package org.example.museutrezemaio.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(
        uniqueConstraints = {@UniqueConstraint(columnNames = {"nome", "sobrenome", "tipo"}) }
)
public class Colaborador {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idColaborador;

    @NotBlank(message = "O campo nome é obrigatório")
    @Column(nullable=false)
    private String nome;

    @NotBlank(message = "O campo sobrenome é obrigatório")
    @Column(nullable=false)
    private String sobrenome;

    private String nacionalidade;

    @NotBlank(message = "O campo tipo é obrigatório")
    @Column(nullable=false)
    private String tipo;

    @Column(nullable = false)
    private boolean ativo = true;

    public Colaborador() {
    }

    public Colaborador(int idColaborador, String nome, String sobrenome, String nacionalidade, String tipo, boolean ativo) {
        this.idColaborador = idColaborador;
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.nacionalidade = nacionalidade;
        this.tipo = tipo;
        this.ativo = ativo;
    }

    public Colaborador(String nome, String sobrenome, String nacionalidade, String tipo, boolean ativo) {
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.nacionalidade = nacionalidade;
        this.tipo = tipo;
        this.ativo = ativo;
    }

    public int getIdColaborador() {
        return idColaborador;
    }

    public void setIdColaborador(int idColaborador) {
        this.idColaborador = idColaborador;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getNacionalidade() {
        return nacionalidade;
    }

    public void setNacionalidade(String nacionalidade) {
        this.nacionalidade = nacionalidade;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    @Override
    public String toString() {
        return "Colaborador{" +
                "idColaborador=" + idColaborador +
                ", nome='" + nome + '\'' +
                ", sobrenome='" + sobrenome + '\'' +
                ", nacionalidade='" + nacionalidade + '\'' +
                ", tipo='" + tipo + '\'' +
                ", ativo=" + ativo +
                '}';
    }
}
