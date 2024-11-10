package com.danielazevedo.mycinechecker.service;

import com.danielazevedo.mycinechecker.dto.FilmeDTO;
import com.danielazevedo.mycinechecker.exception.FilmeNotFoundException;
import com.danielazevedo.mycinechecker.model.Filme;
import com.danielazevedo.mycinechecker.repository.FilmeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FilmeService {

    @Autowired
    private FilmeRepository filmeRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<FilmeDTO> listarTodos() {
        return filmeRepository.findAll().stream()
                .map(filme -> modelMapper.map(filme, FilmeDTO.class))
                .collect(Collectors.toList());
    }

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

    public void excluirPorId(Long id) {
        if (!filmeRepository.existsById(id)) {
            throw new FilmeNotFoundException("Filme não encontrado para exclusão com id " + id);
        }
        filmeRepository.deleteById(id);
    }
}
