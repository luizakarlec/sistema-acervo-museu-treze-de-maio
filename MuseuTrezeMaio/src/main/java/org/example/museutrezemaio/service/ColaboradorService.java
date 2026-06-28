package org.example.museutrezemaio.service;

import org.example.museutrezemaio.model.Assunto;
import org.example.museutrezemaio.model.Colaborador;
import org.example.museutrezemaio.repository.ColaboradorRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ColaboradorService {
    private final ColaboradorRepository colaboradorRepository;

    public ColaboradorService(ColaboradorRepository colaboradorRepository) {
        this.colaboradorRepository = colaboradorRepository;
    }

    public Colaborador salvar(Colaborador colaborador){
        if(colaboradorRepository.existsByNomeAndSobrenomeAndTipoAndIdColaboradorNot(colaborador.getNome(),colaborador.getSobrenome(),colaborador.getTipo(),colaborador.getIdColaborador())){
            throw new IllegalArgumentException("Já existe um colaborador cadastrado com este nome e sobrenome e tipo de colaboração");
        }
        return colaboradorRepository.save(colaborador);
    }

    public List<Colaborador> listarColaboradores(){
        return colaboradorRepository.findAll(Sort.by("nome").ascending());
    }

    public Page<Colaborador> listarTodosColaboradores(int page){
        return colaboradorRepository.findAll(PageRequest.of(page,15,Sort.by("nome").ascending()));
    }

    public Optional<Colaborador> EditarId(Integer id){
    return colaboradorRepository.findById(id);
    }
}
