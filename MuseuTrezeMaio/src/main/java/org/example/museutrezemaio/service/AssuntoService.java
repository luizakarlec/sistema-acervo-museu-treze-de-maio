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

    public void salvar(Assunto assunto) {
        if(assuntoRepository.existsByDescricaoAndIdAssuntoNot(assunto.getDescricao(), assunto.getIdAssunto())){
             throw  new IllegalArgumentException ("Já existe um assunto cadastrado com esse descricao");
        }
        assuntoRepository.save(assunto);
    }

    public List<Assunto> listarAssuntos(){
        return assuntoRepository.findAll();
    }

    public Optional<Assunto> EditarId(Integer id){
        return assuntoRepository.findById(id);
    }


}
