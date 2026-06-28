package org.example.museutrezemaio.service;

import org.example.museutrezemaio.model.Colaborador;
import org.example.museutrezemaio.model.Exemplar;
import org.example.museutrezemaio.repository.ExemplarRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExemplarService {
    private final ExemplarRepository exemplarRepository;

    public ExemplarService(ExemplarRepository exemplarRepository) {this.exemplarRepository = exemplarRepository;}

    public void salvarExemplar(Exemplar exemplar) {
        //implementar ExistsBy... antes de salvar
        exemplarRepository.save(exemplar);


    }

    public Page<Exemplar> listarExemplaresPaginas(int page){
        return exemplarRepository.findAll(PageRequest.of(page,15, Sort.by("codigo").ascending()));
    }


    public List<Exemplar> listarExemplares() { return exemplarRepository.findAll((Sort.by("codigo").ascending()));}

    public Optional<Exemplar> editarId(Integer id) { return exemplarRepository.findById(id); }
}
