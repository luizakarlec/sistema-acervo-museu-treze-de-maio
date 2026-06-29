package org.example.museutrezemaio.repository;

import org.example.museutrezemaio.model.Revista;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RevistaRepository extends JpaRepository<Revista,Integer> {
    boolean existsByISSNAndIdItemAcervoNot(String issn, int idItemAcervo);
    boolean existsByCodigoRevistaAndIdItemAcervoNot(String codigoLivro, int idItemAcervo);
}

