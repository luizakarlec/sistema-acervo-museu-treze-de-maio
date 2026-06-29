package org.example.museutrezemaio.service;

import org.example.museutrezemaio.repository.AssuntoRepository;
import org.example.museutrezemaio.repository.ColaboradorRepository;
import org.example.museutrezemaio.model.Revista;
import org.example.museutrezemaio.repository.RevistaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RevistaService {
    private final RevistaRepository revistaRepository;
    private final AssuntoRepository assuntoRepository;
    private final ColaboradorRepository colaboradorRepository;

    public RevistaService(RevistaRepository revistaRepository, AssuntoRepository assuntoRepository, ColaboradorRepository colaboradorRepository) {
        this.revistaRepository = revistaRepository;
        this.assuntoRepository = assuntoRepository;
        this.colaboradorRepository = colaboradorRepository;
    }

    public void salvar(Revista revista, MultipartFile fileCapa,
                       List<Integer> colaboradoresIds,
                       List<Integer> assuntosIds) throws IOException {
        if (revista.getISSN() != null && revista.getISSN().trim().isEmpty()) {
            revista.setISSN(null);
        }

        if (revista.getISSN() != null && !revista.getISSN().isBlank()) {

            if (revistaRepository.existsByISSNAndIdItemAcervoNot(
                    revista.getISSN(), revista.getIdItemAcervo())) {

                throw new IllegalArgumentException(
                        "Já existe um revista cadastrada com este ISSN."
                );
            }
        }

        if (revistaRepository.existsByCodigoRevistaAndIdItemAcervoNot(
                revista.getCodigoRevista(), revista.getIdItemAcervo())) {
            throw new IllegalArgumentException("Já existe uma revista cadastrada com este código.");
        }

        if (!fileCapa.isEmpty()) {
            // Nova imagem enviada: usa ela
            revista.setCapa(fileCapa.getBytes());
        } else if (revista.getIdItemAcervo() != 0) {
            // Edição sem nova imagem: mantém a capa existente no banco
            revistaRepository.findById(revista.getIdItemAcervo())
                    .ifPresent(revistaExistente -> revista.setCapa(revistaExistente.getCapa()));
        }

        revista.setColaboradores(colaboradoresIds != null && !colaboradoresIds.isEmpty()
                ? colaboradorRepository.findAllById(colaboradoresIds)
                : new ArrayList<>());

        revista.setAssuntos(assuntosIds != null && !assuntosIds.isEmpty()
                ? assuntoRepository.findAllById(assuntosIds)
                : new ArrayList<>());

        revistaRepository.save(revista);
    }


    public List<Revista> listar() { return this.revistaRepository.findAll(); }

    public Page<Revista> listarRevistaPag(int page) {
        return this.revistaRepository.findAll(PageRequest.of(page,15, Sort.by("codigoRevista").ascending()));
    }


    public Optional<Revista> editar(Integer id) { return revistaRepository.findById(id); }
}

