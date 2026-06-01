//package org.example.museutrezemaio.model;
//
//import jakarta.persistence.*;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Entity
//public class ItemAcervo {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    protected int idItemAcervo;
//
//    @Column(nullable = false)
//    protected String titulo;
//
//    @Column(nullable = false)
//    protected String status;
//
//    @Column(nullable = false)
//    protected String idioma;
//
//    @Column(nullable = false)
//    protected String tipo;
//
//    protected String subtitulo;
//
//    protected String edicao;
//
//    protected int numeroPaginas;
//
//    protected byte[] capa;
//
//    protected String localizacaoAcervo;
//
////    @ManyToOne
////    @JoinColumn(name = "id_editora")
////    private Editora editora;
////
////    @ManyToMany
////    @JoinTable(
////            name = "Colaborador",
////            joinColumns = @JoinColumn(name = "id_itemAcervo"),
////            inverseJoinColumns = @JoinColumn(name = "idColaborador")
////    )
////    private List<Colaborador> colaboradores = new ArrayList<>();
////
////    @ManyToMany
////    @JoinTable(
////            name = "Assunto",
////            joinColumns = @JoinColumn(name = "id_ItemAcervo"),
////
////            inverseJoinColumns = @JoinColumn(name = "idAssunto")
////    )
////    private List<Assunto> assuntos = new ArrayList<>();
//
//
//}
