package org.example.museutrezemaio.repository;

import org.example.museutrezemaio.model.Editora;
import org.example.museutrezemaio.model.Jornal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JornalRepository extends JpaRepository<Jornal, Integer> {
}
