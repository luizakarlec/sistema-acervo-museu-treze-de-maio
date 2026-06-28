package org.example.museutrezemaio.service;

import org.example.museutrezemaio.model.Assunto;
import org.example.museutrezemaio.model.Editora;
import org.example.museutrezemaio.repository.AssuntoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AssuntoService {

    private final AssuntoRepository assuntoRepository;

    public AssuntoService(AssuntoRepository assuntoRepository) {
        this.assuntoRepository = assuntoRepository;
    }

    public Assunto salvar(Assunto assunto) {
        if(assuntoRepository.existsByDescricaoAndIdAssuntoNot(assunto.getDescricao(), assunto.getIdAssunto())){
             throw  new IllegalArgumentException ("Já existe um assunto cadastrado com esse descricao");
        }
        return assuntoRepository.save(assunto);
    }

    public List<Assunto> listaAssuntos() {
        return assuntoRepository.findAll(Sort.by("descricao").ascending());
    }

    public List<Assunto> listarAssuntosAtivos(){
        return assuntoRepository.findByAtivoTrueOrderByDescricaoAsc();
    }

    public Page<Assunto> listarAssuntos(int page){

        return assuntoRepository.findAll(PageRequest.of(page,15,Sort.by("descricao").ascending()));
    }

    public Optional<Assunto> EditarId(Integer id){
        return assuntoRepository.findById(id);
    }

    public List<Assunto> buscarPorDescricao(String descricao) {
        if (descricao == null || descricao.trim().isEmpty()) {
            return assuntoRepository.findAll();
        }
        return assuntoRepository.findByDescricaoContainingIgnoreCase(descricao);
    }



}
