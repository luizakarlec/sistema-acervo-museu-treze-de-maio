package org.example.museutrezemaio.repository;

import org.example.museutrezemaio.model.Assunto;
import org.example.museutrezemaio.model.Exemplar;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExemplarRepository extends JpaRepository<Exemplar,Integer>{
    boolean existsByCodigoAndIdExemplarNot(String codigo, int id);

    List<Exemplar> findByCodigoContainingIgnoreCase(String codigo);

    List<Exemplar> findByAtivoTrueOrderByCodigoAsc();
}
