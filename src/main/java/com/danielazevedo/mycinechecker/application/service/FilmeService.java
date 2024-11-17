package com.danielazevedo.mycinechecker.application.service;

import com.danielazevedo.mycinechecker.application.dto.FilmeDTO;
import com.danielazevedo.mycinechecker.application.exception.FilmeNotFoundException;
import com.danielazevedo.mycinechecker.application.exception.UsuarioNaoEncontradoException;
import com.danielazevedo.mycinechecker.domain.model.Filme;
import com.danielazevedo.mycinechecker.domain.model.User;
import com.danielazevedo.mycinechecker.domain.repository.FilmeRepository;
import com.danielazevedo.mycinechecker.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FilmeService {

    private final FilmeRepository filmeRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

    public List<FilmeDTO> listarTodos() {
        return filmeRepository.findAll().stream()
                .map(filme -> modelMapper.map(filme, FilmeDTO.class))
                .collect(Collectors.toList());
    }

    @Transactional
    public FilmeDTO salvar(FilmeDTO filmeDTO) throws UsuarioNaoEncontradoException {
        // Obter usuário autenticado
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        // Buscar o usuário no banco de dados
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário não encontrado: " + username));

        // Mapear DTO para entidade e associar o usuário
        Filme filme = modelMapper.map(filmeDTO, Filme.class);
        filme.setUser(user); // Associar o usuário ao filme

        // Salvar no banco de dados
        Filme savedFilme = filmeRepository.save(filme);

        // Retornar o DTO do filme salvo
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