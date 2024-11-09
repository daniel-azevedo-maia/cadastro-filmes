package com.danielazevedo.mycinechecker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.danielazevedo.mycinechecker.model.Filme;

import java.util.List;

@Repository
public interface FilmeRepository extends JpaRepository<Filme, Long> {
    List<Filme> findByTituloContainingIgnoreCase(String titulo);
}

