package org.example.museutrezemaio.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;

@Entity
public class ItemAcervo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected int idItemAcervo;

    @NotBlank(message = "O campo título é obrigatório")
    @Column(nullable = false)
    protected String titulo;

    @NotBlank(message = "O campo status é obrigatório")
    @Column(nullable = false)
    protected String status;

    @NotBlank(message = "O campo idioma é obrigatório")
    @Column(nullable = false)
    protected String idioma;

    @Column(nullable = false)
    protected String tipo;

    protected String descricao;

    protected String notas;

    protected boolean visibilidadeNotas;

    protected String subtitulo;

    @NotBlank(message = "O campo edição é obrigatório")
    @Column(nullable = false)
    protected String edicao;

    protected int numeroPaginas;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }

    public boolean isVisibilidadeNotas() {
        return visibilidadeNotas;
    }

    public void setVisibilidadeNotas(boolean visibilidadeNotas) {
        this.visibilidadeNotas = visibilidadeNotas;
    }

    @NotBlank(message = "O campo localização no acervo é obrigatório")
    @Column(nullable = false) //unique?
    protected String localizacaoAcervo;

    @ManyToOne
    @JoinColumn(name = "idEditora")
    private Editora editora;

    @ManyToMany
    @JoinTable(
            name = "itemAcervoColaborador",
            joinColumns = @JoinColumn(name = "idItemAcervo"),
            inverseJoinColumns = @JoinColumn(name = "idColaborador")
    )
    private List<Colaborador> colaboradores = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "itemAcervoAssunto",
            joinColumns = @JoinColumn(name = "idItemAcervo"),

            inverseJoinColumns = @JoinColumn(name = "idAssunto")
    )
    private List<Assunto> assuntos = new ArrayList<>();

}
