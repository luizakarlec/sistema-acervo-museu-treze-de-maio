package org.example.museutrezemaio.service;

import org.example.museutrezemaio.model.Colaborador;
import org.example.museutrezemaio.repository.ColaboradorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ColaboradorService {
    private final ColaboradorRepository colaboradorRepository;

    public ColaboradorService(ColaboradorRepository colaboradorRepository) {
        this.colaboradorRepository = colaboradorRepository;
    }

    public void salvar(Colaborador colaborador){
        if(colaboradorRepository.existsByNomeAndSobrenomeAndTipoAndIdColaboradorNot(colaborador.getNome(),colaborador.getSobrenome(),colaborador.getTipo(),colaborador.getIdColaborador())){
            throw new IllegalArgumentException("Já existe um colaborador cadastrado com este nome e sobrenome e tipo de colaboração");
        }
        colaboradorRepository.save(colaborador);
    }

    public List<Colaborador> listarColaboradores(){
        return colaboradorRepository.findAll();
    }

    public Optional<Colaborador> EditarId(Integer id){
    return colaboradorRepository.findById(id);
    }
}
