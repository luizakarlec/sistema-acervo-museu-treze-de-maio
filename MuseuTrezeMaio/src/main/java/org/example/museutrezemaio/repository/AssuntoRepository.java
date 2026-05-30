package org.example.museutrezemaio.repository;

import org.example.museutrezemaio.model.Assunto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssuntoRepository extends JpaRepository<Assunto,Integer> {

    boolean existsByDescricaoAndIdAssuntoNot(String descricao, int idAssunto);


}
