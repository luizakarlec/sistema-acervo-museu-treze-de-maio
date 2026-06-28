package org.example.museutrezemaio.service;

import org.example.museutrezemaio.model.Jornal;
import org.example.museutrezemaio.model.Revista;
import org.example.museutrezemaio.repository.RevistaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class RevistaService {
    private final RevistaRepository revistaRepository;

    public RevistaService(RevistaRepository revistaRepository) {this.revistaRepository = revistaRepository;}

    public void salvar(Revista revista, MultipartFile fileCapa) throws IOException {

        if (!fileCapa.isEmpty()) {
            // Nova imagem enviada: usa ela
            revista.setCapa(fileCapa.getBytes());
        } else if (revista.getIdItemAcervo() != 0) {
            // Edição sem nova imagem: mantém a capa existente no banco
            revistaRepository.findById(revista.getIdItemAcervo())
                    .ifPresent(revistaExistente -> revista.setCapa(revistaExistente.getCapa()));
        }

        revistaRepository.save(revista);
    }


    public List<Revista> listar() { return this.revistaRepository.findAll(); }

    public Page<Revista> listarRevistaPag(int page) {
        return this.revistaRepository.findAll(PageRequest.of(page,15, Sort.by("codigoRevista").ascending()));
    }


    public Optional<Revista> editar(Integer id) { return revistaRepository.findById(id); }
}

