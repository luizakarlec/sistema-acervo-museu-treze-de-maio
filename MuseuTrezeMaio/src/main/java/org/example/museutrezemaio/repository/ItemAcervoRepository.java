package org.example.museutrezemaio.repository;

import org.example.museutrezemaio.model.ItemAcervo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemAcervoRepository  extends JpaRepository<ItemAcervo,Integer> {
    List<ItemAcervo> findByStatusNot(String status);
}
