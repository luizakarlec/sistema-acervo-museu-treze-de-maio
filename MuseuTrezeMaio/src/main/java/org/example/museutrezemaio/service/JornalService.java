package org.example.museutrezemaio.service;

import org.example.museutrezemaio.model.Jornal;
import org.example.museutrezemaio.repository.AssuntoRepository;
import org.example.museutrezemaio.repository.ColaboradorRepository;
import org.example.museutrezemaio.repository.JornalRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class JornalService {
    private final JornalRepository jornalRepository;
    private final AssuntoRepository assuntoRepository;
    private final ColaboradorRepository colaboradorRepository;

    public JornalService(JornalRepository jornalRepository, AssuntoRepository assuntoRepository, ColaboradorRepository colaboradorRepository) {
        this.jornalRepository = jornalRepository;
        this.assuntoRepository = assuntoRepository;
        this.colaboradorRepository = colaboradorRepository;
    }

    public void salvar(Jornal jornal, MultipartFile fileCapa, List<Integer> colaboradoresIds,
                       List<Integer> assuntosIds) throws IOException {

        if (!fileCapa.isEmpty()) {
            // Nova imagem enviada: usa ela
            jornal.setCapa(fileCapa.getBytes());
        } else if (jornal.getIdItemAcervo() != 0) {
            // Edição sem nova imagem: mantém a capa existente no banco
            jornalRepository.findById(jornal.getIdItemAcervo())
                    .ifPresent(jornalExistente -> jornal.setCapa(jornalExistente.getCapa()));
        }

        jornal.setColaboradores(colaboradoresIds != null && !colaboradoresIds.isEmpty()
                ? colaboradorRepository.findAllById(colaboradoresIds)
                : new ArrayList<>());

        jornal.setAssuntos(assuntosIds != null && !assuntosIds.isEmpty()
                ? assuntoRepository.findAllById(assuntosIds)
                : new ArrayList<>());

        jornalRepository.save(jornal);
    }

    public List<Jornal> listaJornais(){
        return jornalRepository.findAll();
    }

    public Optional<Jornal> EditarId(Integer id){
        return jornalRepository.findById(id);
    }
}
