package org.example.museutrezemaio.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class ItemAcervo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected int idItemAcervo;

    @Column(nullable = false)
    protected String titulo;

    @Column(nullable = false)
    protected String status;

    @Column(nullable = false)
    protected String idioma;

    @Column(nullable = false)
    protected String tipo;

    protected String subtitulo;

    protected String edicao;

    protected int numeroPaginas;

    protected byte[] capa;

    public int getIdItemAcervo() {
        return idItemAcervo;
    }

    public void setIdItemAcervo(int idItemAcervo) {
        this.idItemAcervo = idItemAcervo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getSubtitulo() {
        return subtitulo;
    }

    public void setSubtitulo(String subtitulo) {
        this.subtitulo = subtitulo;
    }

    public String getEdicao() {
        return edicao;
    }

    public void setEdicao(String edicao) {
        this.edicao = edicao;
    }

    public int getNumeroPaginas() {
        return numeroPaginas;
    }

    public void setNumeroPaginas(int numeroPaginas) {
        this.numeroPaginas = numeroPaginas;
    }

    public byte[] getCapa() {
        return capa;
    }

    public void setCapa(byte[] capa) {
        this.capa = capa;
    }

    public String getLocalizacaoAcervo() {
        return localizacaoAcervo;
    }

    public void setLocalizacaoAcervo(String localizacaoAcervo) {
        this.localizacaoAcervo = localizacaoAcervo;
    }

    public Editora getEditora() {
        return editora;
    }

    public void setEditora(Editora editora) {
        this.editora = editora;
    }

    public List<Colaborador> getColaboradores() {
        return colaboradores;
    }

    public void setColaboradores(List<Colaborador> colaboradores) {
        this.colaboradores = colaboradores;
    }

    public List<Assunto> getAssuntos() {
        return assuntos;
    }

    public void setAssuntos(List<Assunto> assuntos) {
        this.assuntos = assuntos;
    }

    protected String localizacaoAcervo;

    @ManyToOne
    @JoinColumn(name = "idEditora")
    private Editora editora;

    @ManyToMany
    @JoinTable(
            name = "Colaborador",
            joinColumns = @JoinColumn(name = "idItemAcervo"),
            inverseJoinColumns = @JoinColumn(name = "idColaborador")
    )
    private List<Colaborador> colaboradores = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "Assunto",
            joinColumns = @JoinColumn(name = "idItemAcervo"),

            inverseJoinColumns = @JoinColumn(name = "idAssunto")
    )
    private List<Assunto> assuntos = new ArrayList<>();

}
