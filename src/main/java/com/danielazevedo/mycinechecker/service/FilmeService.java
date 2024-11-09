package com.danielazevedo.mycinechecker.service;

import com.danielazevedo.mycinechecker.dto.FilmeDTO;

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
        filme = filmeRepository.save(filme);
        return modelMapper.map(filme, FilmeDTO.class);
    }

    public List<FilmeDTO> buscarPorTitulo(String titulo) {
        return filmeRepository.findByTituloContainingIgnoreCase(titulo).stream()
                .map(filme -> modelMapper.map(filme, FilmeDTO.class))
                .collect(Collectors.toList());
    }

    public FilmeDTO buscarPorId(Long id) {
        return modelMapper.map(filmeRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Filme não encontrado: " + id)), FilmeDTO.class);
    }

    public void excluirPorId(Long id) {
        filmeRepository.deleteById(id);
    }


}
