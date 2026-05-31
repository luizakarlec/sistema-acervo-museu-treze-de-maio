package org.example.museutrezemaio.service;

import org.example.museutrezemaio.model.Editora;
import org.example.museutrezemaio.model.Localizacao;
import org.example.museutrezemaio.repository.EditoraRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EditoraService {
    private final EditoraRepository editoraRepository;

    public EditoraService(EditoraRepository editoraRepository) {
        this.editoraRepository = editoraRepository;
    }

    public void salvar(Editora editora){
        Localizacao loc = editora.getLocalizacao();

        String cidade = loc.getCidade();
        String estado = loc.getEstado();

        if ((cidade == null || cidade.isBlank()) &&
                (estado == null || estado.isBlank())) {

            throw new IllegalArgumentException(
                    "Informe pelo menos a cidade ou o estado.");
        }
        editoraRepository.save(editora);
    }

    public boolean existeNomeLocalizacao(String nome,String cidade, String estado, Integer idEditora){
        return editoraRepository.existsByNomeAndLocalizacaoCidadeAndLocalizacaoEstadoAndIdEditoraNot(nome, cidade, estado,idEditora);
    }

    public List<Editora>listaEditoras(){
        return editoraRepository.findAll();
    }

    public Optional<Editora> EditarId(Integer id){
        return editoraRepository.findById(id);
    }
}
