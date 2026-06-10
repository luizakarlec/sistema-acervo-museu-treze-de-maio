package org.example.museutrezemaio.service;

import org.example.museutrezemaio.model.Jornal;
import org.example.museutrezemaio.repository.JornalRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class JornalService {
    private final JornalRepository jornalRepository;

    public JornalService(JornalRepository jornalRepository) {
        this.jornalRepository = jornalRepository;
    }

    public void salvar(Jornal jornal, MultipartFile fileCapa) throws IOException {

        if (!fileCapa.isEmpty()) {
            // Nova imagem enviada: usa ela
            jornal.setCapa(fileCapa.getBytes());
        } else if (jornal.getIdItemAcervo() != 0) {
            // Edição sem nova imagem: mantém a capa existente no banco
            jornalRepository.findById(jornal.getIdItemAcervo())
                    .ifPresent(jornalExistente -> jornal.setCapa(jornalExistente.getCapa()));
        }

        jornalRepository.save(jornal);
    }

    public List<Jornal> listaJornais(){
        return jornalRepository.findAll();
    }
    public Optional<Jornal> EditarId(Integer id){
        return jornalRepository.findById(id);
    }
}
