package org.example.museutrezemaio.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(
        uniqueConstraints = { @UniqueConstraint(columnNames = {"codigo", "idAcervo"} ) }
)
public class Exemplar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idExemplar;

    @NotBlank(message = "O campo código é obrigatório")
    @Column(unique = true, nullable = false)
    private String codigo;

    @NotNull(message = "O item de acervo é obrigatório")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idAcervo")
    private ItemAcervo itemAcervo;

    @Column(nullable = false)
    private boolean ativo = true;

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public int getIdExemplar() {
        return idExemplar;
    }

    public void setIdExemplar(int id_exemplar) {
        this.idExemplar = id_exemplar;
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
