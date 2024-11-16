package com.danielazevedo.mycinechecker.application.service;

import com.danielazevedo.mycinechecker.application.dto.FilmeDTO;
import com.danielazevedo.mycinechecker.application.exception.FilmeNotFoundException;
import com.danielazevedo.mycinechecker.domain.model.Filme;
import com.danielazevedo.mycinechecker.domain.repository.FilmeRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FilmeService {

    private final FilmeRepository filmeRepository;
    private final ModelMapper modelMapper;

    public List<FilmeDTO> listarTodos() {
        return filmeRepository.findAll().stream()
                .map(filme -> modelMapper.map(filme, FilmeDTO.class))
                .collect(Collectors.toList());
    }

    @Transactional
    public FilmeDTO salvar(FilmeDTO filmeDTO) {
        Filme filme = modelMapper.map(filmeDTO, Filme.class);
        Filme savedFilme = filmeRepository.save(filme);
        return modelMapper.map(savedFilme, FilmeDTO.class);
    }

    public FilmeDTO buscarPorId(Long id) {
        Filme filme = filmeRepository.findById(id)
                .orElseThrow(() -> new FilmeNotFoundException("Filme não encontrado com id " + id));
        return modelMapper.map(filme, FilmeDTO.class);
    }

    @Transactional
    public void excluirPorId(Long id) {
        if (!filmeRepository.existsById(id)) {
            throw new FilmeNotFoundException("Filme não encontrado para exclusão com id " + id);
        }
        filmeRepository.deleteById(id);
    }
}
