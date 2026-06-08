package org.example.museutrezemaio.repository;

import org.example.museutrezemaio.model.Assunto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssuntoRepository extends JpaRepository<Assunto,Integer> {

    boolean existsByDescricaoAndIdAssuntoNot(String descricao, int idAssunto);

    List<Assunto> findByDescricaoContainingIgnoreCase(String descricao);

}
