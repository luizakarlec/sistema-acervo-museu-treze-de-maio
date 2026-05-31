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
        colaboradorRepository.save(colaborador);
    }

    public boolean existeNomeSobrenomeTipo(String nome, String  sobrenome, String tipo, Integer idColaborador){
        return colaboradorRepository.existsByNomeAndSobrenomeAndTipoAndIdColaboradorNot(nome, sobrenome, tipo, idColaborador);
    }


    public List<Colaborador> listarColaboradores(){
        return colaboradorRepository.findAll();
    }

    public Optional<Colaborador> EditarId(Integer id){
    return colaboradorRepository.findById(id);
    }
}
