package org.example.museutrezemaio.repository;

import org.example.museutrezemaio.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LivroRepository extends JpaRepository<Livro, Integer> {

    boolean existsByCodigoLivroAndIdItemAcervoNot(String codigoLivro, int idItemAcervo);

    List<Livro> findByTituloContainingIgnoreCase(String titulo);

    boolean existsByIsbnAndIdItemAcervoNot(String isbn, int idItemAcervo);
}
