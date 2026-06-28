package org.example.museutrezemaio.model;

import jakarta.persistence.*;

@Entity
public class Exemplar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_exemplar;

    @Column(unique = true, nullable = false)
    private String codigo;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_acervo")
    private ItemAcervo itemAcervo;

    @Column(nullable = false)
    private boolean ativo = true;

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public int getId_exemplar() {
        return id_exemplar;
    }

    public void setId_exemplar(int id_exemplar) {
        this.id_exemplar = id_exemplar;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public ItemAcervo getItemAcervo() {
        return itemAcervo;
    }

    public void setItemAcervo(ItemAcervo itemAcervo) {
        this.itemAcervo = itemAcervo;
    }
}
