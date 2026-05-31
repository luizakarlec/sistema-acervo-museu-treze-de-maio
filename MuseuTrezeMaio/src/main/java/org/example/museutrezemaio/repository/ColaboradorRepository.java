package org.example.museutrezemaio.repository;

import org.example.museutrezemaio.model.Colaborador;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ColaboradorRepository extends JpaRepository<Colaborador,Integer> {
    boolean existsByNomeAndSobrenomeAndTipoAndIdColaboradorNot(String nome, String sobrenome,  String tipo, int idColaborador);
}
