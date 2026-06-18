package org.example.museutrezemaio.repository;

import org.example.museutrezemaio.model.Assunto;
import org.example.museutrezemaio.model.Editora;
import org.example.museutrezemaio.model.Localizacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EditoraRepository extends JpaRepository<Editora, Integer> {

    boolean existsByNomeAndLocalizacaoCidadeAndLocalizacaoEstadoAndIdEditoraNot(String nome, String cidade, String estado, int idEditora);
    List<Editora> findByNomeContainingIgnoreCase(String nome);

}
