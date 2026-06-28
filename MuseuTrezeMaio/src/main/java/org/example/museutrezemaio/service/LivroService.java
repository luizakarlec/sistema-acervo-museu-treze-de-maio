package org.example.museutrezemaio.service;

import org.example.museutrezemaio.model.Assunto;
import org.example.museutrezemaio.model.Livro;
import org.example.museutrezemaio.repository.AssuntoRepository;
import org.example.museutrezemaio.repository.ColaboradorRepository;
import org.example.museutrezemaio.repository.LivroRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LivroService {

    private final LivroRepository livroRepository;
    private final ColaboradorRepository colaboradorRepository;
    private final AssuntoRepository assuntoRepository;

    public LivroService(LivroRepository livroRepository,
                        ColaboradorRepository colaboradorRepository,
                        AssuntoRepository assuntoRepository) {
        this.livroRepository = livroRepository;
        this.colaboradorRepository = colaboradorRepository;
        this.assuntoRepository = assuntoRepository;
    }

    public void salvar(Livro livro, MultipartFile fileCapa,
                       List<Integer> colaboradoresIds,
                       List<Integer> assuntosIds) throws IOException {

        // Converte strings vazias em null pois o isbn é unico e se deixar vazio ele permite salvar apenas um livro sem isbn
        if (livro.getIsbn() != null && livro.getIsbn().trim().isEmpty()) {
            livro.setIsbn(null);
        }

        if (livro.getIsbn() != null && !livro.getIsbn().isBlank()) {

            if (livroRepository.existsByIsbnAndIdItemAcervoNot(
                    livro.getIsbn(), livro.getIdItemAcervo())) {

                throw new IllegalArgumentException(
                        "Já existe um livro cadastrado com este ISBN."
                );
            }
        }

        if (livroRepository.existsByCodigoLivroAndIdItemAcervoNot(
                livro.getCodigoLivro(), livro.getIdItemAcervo())) {
            throw new IllegalArgumentException("Já existe um livro cadastrado com este código.");
        }

        livro.setColaboradores(colaboradoresIds != null && !colaboradoresIds.isEmpty()
                ? colaboradorRepository.findAllById(colaboradoresIds)
                : new ArrayList<>());

        livro.setAssuntos(assuntosIds != null && !assuntosIds.isEmpty()
                ? assuntoRepository.findAllById(assuntosIds)
                : new ArrayList<>());

        if (!fileCapa.isEmpty()) {
            livro.setCapa(fileCapa.getBytes());
        } else if (livro.getIdItemAcervo() != 0) {
            livroRepository.findById(livro.getIdItemAcervo())
                    .ifPresent(livroExistente -> livro.setCapa(livroExistente.getCapa()));
        }

        livroRepository.save(livro);
    }

    public List<Livro> listarLivros() {
        return livroRepository.findAll();
    }

    public Page<Livro> listarLivroPag(int page){

        return livroRepository.findAll(PageRequest.of(page,15, Sort.by("codigoLivro").ascending()));
    }

    public Optional<Livro> editarId(Integer id) {
        return livroRepository.findById(id);
    }

    public void excluir(int id) {
        livroRepository.deleteById(id);
    }
}
