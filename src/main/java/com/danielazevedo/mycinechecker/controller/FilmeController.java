package com.danielazevedo.mycinechecker.controller;

import com.danielazevedo.mycinechecker.dto.FilmeDTO;
import com.danielazevedo.mycinechecker.exception.FilmeNotFoundException;
import com.danielazevedo.mycinechecker.service.FilmeService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequestMapping("/api/v1/filmes") // Adicionando padrão de versão e endpoint base
@AllArgsConstructor
public class FilmeController {

    private final FilmeService filmeService;

    @GetMapping
    public String listarFilmes(Model model) {
        List<FilmeDTO> filmes = filmeService.listarTodos().stream()
                .map(filme -> {
                    filme.setDataAssistidoFormatada(filme.getDataAssistido()
                            .format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                    return filme;
                })
                .toList();

        model.addAttribute("filmes", filmes);
        return "filmes/listagem";
    }

    @GetMapping("/novo")
    public String mostrarFormularioCadastro(Model model) {
        model.addAttribute("filmeobj", new FilmeDTO());
        return "filmes/registro";
    }

    @PostMapping
    public String cadastrarFilme(
            @Valid @ModelAttribute("filmeobj") FilmeDTO filmeDTO,
            BindingResult result,
            Model model) {
        if (result.hasErrors()) {
            model.addAttribute("filmeobj", filmeDTO);
            return "filmes/registro";
        }
        filmeService.salvar(filmeDTO);
        return "redirect:/api/v1/filmes";
    }

    @GetMapping("/{id}/editar")
    public String mostrarFormularioEdicao(@PathVariable("id") Long id, Model model) {
        FilmeDTO filme = filmeService.buscarPorId(id);
        if (filme == null) {
            throw new FilmeNotFoundException("Filme não encontrado com o ID " + id);
        }
        model.addAttribute("filmeobj", filme);
        return "filmes/registro";
    }

    @PutMapping("/{id}")
    public String atualizarFilme(
            @PathVariable("id") Long id,
            @Valid @ModelAttribute FilmeDTO filmeDTO,
            BindingResult result,
            Model model) {
        if (result.hasErrors()) {
            model.addAttribute("filmeobj", filmeDTO);
            return "filmes/registro";
        }
        filmeDTO.setId(id);
        filmeService.salvar(filmeDTO);
        return "redirect:/api/v1/filmes";
    }

    @DeleteMapping("/{id}")
    public String excluirFilme(@PathVariable("id") Long id) {
        filmeService.excluirPorId(id);
        return "redirect:/api/v1/filmes";
    }
}
