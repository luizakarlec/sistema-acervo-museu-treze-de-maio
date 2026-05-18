package org.example.museutrezemaio.model;

import jakarta.persistence.*;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idLivro;

    private String isbn;

    @Column(nullable = false, unique = true)
    private String codigoLivro;

    private Year anoPublicacao;

    @Column(nullable = false)
    private String edicao;

    @Column(nullable = false)
    private String titulo;

    private String subtitulo;

    private int numeroPaginas;

    private String localizacaoAcervo;

    @Column(nullable = false)
    private String idioma;

    private int quantidade;

    private byte[] capa;

    @ManyToOne
    @JoinColumn(name = "id_editora")
    private Editora editora;

    @ManyToMany
    @JoinTable(
            name = "livro_colaborador",
            joinColumns = @JoinColumn(name = "id_livro"),
            inverseJoinColumns = @JoinColumn(name = "id_colaborador")
    )
    private List<Colaborador> colaboradores = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "livro_assunto",
            joinColumns = @JoinColumn(name = "id_livro"),
            inverseJoinColumns = @JoinColumn(name = "id_assunto")
    )
    private List<Assunto> assuntos = new ArrayList<>();

    @Column(nullable = false)
    private boolean ativo = true;

    // Getters e Setters

    public int getIdLivro() { return idLivro; }
    public void setIdLivro(int idLivro) { this.idLivro = idLivro; }

    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

    public String getCodigoLivro() { return codigoLivro; }
    public void setCodigoLivro(String codigoLivro) { this.codigoLivro = codigoLivro; }

    public Year getAnoPublicacao() { return anoPublicacao; }
    public void setAnoPublicacao(Year anoPublicacao) { this.anoPublicacao = anoPublicacao; }

    public String getEdicao() { return edicao; }
    public void setEdicao(String edicao) { this.edicao = edicao; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getSubtitulo() { return subtitulo; }
    public void setSubtitulo(String subtitulo) { this.subtitulo = subtitulo; }

    public int getNumeroPaginas() { return numeroPaginas; }
    public void setNumeroPaginas(int numeroPaginas) { this.numeroPaginas = numeroPaginas; }

    public String getLocalizacaoAcervo() { return localizacaoAcervo; }
    public void setLocalizacaoAcervo(String localizacaoAcervo) { this.localizacaoAcervo = localizacaoAcervo; }

    public String getIdioma() { return idioma; }
    public void setIdioma(String idioma) { this.idioma = idioma; }

    public int getQuantidade() { return quantidade; }
    public void setQuantidade(int quantidade) { this.quantidade = quantidade; }

    public byte[] getCapa() { return capa; }
    public void setCapa(byte[] capa) { this.capa = capa; }

    public Editora getEditora() { return editora; }
    public void setEditora(Editora editora) { this.editora = editora; }

    public List<Colaborador> getColaboradores() { return colaboradores; }
    public void setColaboradores(List<Colaborador> colaboradores) { this.colaboradores = colaboradores; }

    public List<Assunto> getAssuntos() { return assuntos; }
    public void setAssuntos(List<Assunto> assuntos) { this.assuntos = assuntos; }

    public boolean isAtivo() { return ativo; }
    public void setAtivo(boolean ativo) { this.ativo = ativo; }
}
