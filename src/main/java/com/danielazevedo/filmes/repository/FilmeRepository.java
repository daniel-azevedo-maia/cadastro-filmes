package com.danielazevedo.filmes.repository;

import com.danielazevedo.filmes.model.Filme;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public interface FilmeRepository extends JpaRepository<Filme, Long> {

    // OBS.: os nomes das entidades, no JPQL, são case-sensitive!
    @Query("SELECT f FROM Filme f WHERE f.titulo LIKE %:titulo%")
    List<Filme> findFilmeByTitulo(String titulo);

    @Query("SELECT f FROM Filme f WHERE f.anoLancamento = :ano")
    List<Filme> findFilmeByAno(int ano);

}
