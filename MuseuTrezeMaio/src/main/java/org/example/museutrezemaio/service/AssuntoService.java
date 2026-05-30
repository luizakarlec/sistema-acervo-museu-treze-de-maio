package org.example.museutrezemaio.service;

import org.example.museutrezemaio.model.Assunto;
import org.example.museutrezemaio.repository.AssuntoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AssuntoService {

    private final AssuntoRepository assuntoRepository;

    public AssuntoService(AssuntoRepository assuntoRepository) {
        this.assuntoRepository = assuntoRepository;
    }

    public void salvar(Assunto assunto){
        assuntoRepository.save(assunto);
    }

    public boolean existeDescricaoId(String descricao, Integer id){
        return assuntoRepository.existsByDescricaoAndIdAssuntoNot(descricao, id);
    }

    public List<Assunto> listarAssuntos(){
        return assuntoRepository.findAll();
    }

    public Optional<Assunto> EditarId(Integer id){
        return assuntoRepository.findById(id);
    }


}
