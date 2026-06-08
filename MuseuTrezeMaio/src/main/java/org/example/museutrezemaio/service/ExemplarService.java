package org.example.museutrezemaio.service;

import org.example.museutrezemaio.model.Exemplar;
import org.example.museutrezemaio.repository.ExemplarRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExemplarService {
    private final ExemplarRepository exemplarRepository;

    public ExemplarService(ExemplarRepository exemplarRepository) {this.exemplarRepository = exemplarRepository;}

    public void salvar(Exemplar exemplar) {
        //implementar ExistsBy... antes de salvar
        exemplarRepository.save(exemplar);


    }

    public List<Exemplar> listar() { return exemplarRepository.findAll(); }

    public Optional<Exemplar> editarId(Integer id) { return exemplarRepository.findById(id); }
}
