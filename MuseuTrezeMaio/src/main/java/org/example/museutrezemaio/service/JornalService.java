package org.example.museutrezemaio.service;

import org.example.museutrezemaio.model.Editora;
import org.example.museutrezemaio.model.Jornal;
import org.example.museutrezemaio.model.Localizacao;
import org.example.museutrezemaio.repository.EditoraRepository;
import org.example.museutrezemaio.repository.JornalRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JornalService {
    private final JornalRepository jornalRepository;

    public JornalService(JornalRepository jornalRepository) {
        this.jornalRepository = jornalRepository;
    }

    public void salvar(Jornal jornal){
        //regras de negócio
        jornalRepository.save(jornal);
    }

    public List<Jornal> listaJornais(){
        return jornalRepository.findAll();
    }
    public Optional<Jornal> EditarId(Integer id){
        return jornalRepository.findById(id);
    }
}
