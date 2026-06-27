package org.example.museutrezemaio.service;


import org.example.museutrezemaio.model.ItemAcervo;
import org.example.museutrezemaio.repository.ItemAcervoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemAcervoService {
    private final ItemAcervoRepository itemAcervoRepository;

    public ItemAcervoService(ItemAcervoRepository itemAcervoRepository) {
        this.itemAcervoRepository = itemAcervoRepository;
    }

    public List<ItemAcervo> listarTodos() {
        return itemAcervoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<ItemAcervo> buscar(String q, List<String> tipos, Integer assuntoId, String idioma, Integer editoraId) {
        return itemAcervoRepository.findAll().stream()
                .filter(item -> {
                    if (q != null && !q.isBlank()) {
                        String lower = q.toLowerCase().trim();
                        boolean match = (item.getTitulo() != null && item.getTitulo().toLowerCase().contains(lower))
                                || (item.getSubtitulo() != null && item.getSubtitulo().toLowerCase().contains(lower))
                                || (item.getDescricao() != null && item.getDescricao().toLowerCase().contains(lower))
                                || item.getColaboradores().stream().anyMatch(c ->
                                        (c.getNome() + " " + c.getSobrenome()).toLowerCase().contains(lower));
                        if (!match) return false;
                    }
                    if (tipos != null && !tipos.isEmpty() && !tipos.contains(item.getTipo())) return false;
                    if (assuntoId != null && item.getAssuntos().stream().noneMatch(a -> a.getIdAssunto() == assuntoId)) return false;
                    if (idioma != null && !idioma.isBlank()) {
                        if (item.getIdioma() == null || !item.getIdioma().toLowerCase().contains(idioma.toLowerCase())) return false;
                    }
                    if (editoraId != null) {
                        if (item.getEditora() == null || item.getEditora().getIdEditora() != editoraId) return false;
                    }
                    return true;
                })
                .collect(Collectors.toList());
    }
}
