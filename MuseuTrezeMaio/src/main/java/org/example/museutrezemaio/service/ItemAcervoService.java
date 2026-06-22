package org.example.museutrezemaio.service;


import org.example.museutrezemaio.model.ItemAcervo;
import org.example.museutrezemaio.repository.ItemAcervoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemAcervoService {
    private final ItemAcervoRepository itemAcervoRepository;

    public ItemAcervoService(ItemAcervoRepository itemAcervoRepository) {
        this.itemAcervoRepository = itemAcervoRepository;
    }

    public List<ItemAcervo> listarTodos() {
        return itemAcervoRepository.findAll();
    }
}
