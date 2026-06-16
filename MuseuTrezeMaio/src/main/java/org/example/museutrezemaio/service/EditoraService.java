package org.example.museutrezemaio.service;

import org.example.museutrezemaio.model.Editora;
import org.example.museutrezemaio.model.Localizacao;
import org.example.museutrezemaio.repository.EditoraRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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

        if(editoraRepository.existsByNomeAndLocalizacaoCidadeAndLocalizacaoEstadoAndIdEditoraNot(editora.getNome(),cidade,estado,editora.getIdEditora())){
            throw new IllegalArgumentException("Já existe uma editora cadastrada com esse nome e localização");
        }

        editoraRepository.save(editora);
    }


    public Page<Editora>listaEditoras(Pageable pageable){
        return editoraRepository.findAll(pageable);
    }

    public Optional<Editora> EditarId(Integer id){
        return editoraRepository.findById(id);
    }
}
