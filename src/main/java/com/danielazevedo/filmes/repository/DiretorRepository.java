package com.danielazevedo.filmes.repository;

import com.danielazevedo.filmes.model.Diretor;
import com.danielazevedo.filmes.model.Filme;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public interface DiretorRepository extends JpaRepository<Diretor, Long> {
    @Query("SELECT d FROM Diretor d WHERE d.nome LIKE %:nome%")
    List<Diretor> findDiretorByNome(String nome);

}
