package org.example.museutrezemaio.repository;

import org.example.museutrezemaio.model.ItemAcervo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemAcervoRepository  extends JpaRepository<ItemAcervo,Integer> {
}
