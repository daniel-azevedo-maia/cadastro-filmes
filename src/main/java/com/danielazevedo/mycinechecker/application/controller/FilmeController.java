package com.danielazevedo.mycinechecker.application.controller;

import com.danielazevedo.mycinechecker.application.dto.FilmeDTO;
import com.danielazevedo.mycinechecker.application.exception.FilmeNotFoundException;
import com.danielazevedo.mycinechecker.application.service.FilmeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/v1/filmes")
@RequiredArgsConstructor
public class FilmeController {

    private static final Logger logger = LoggerFactory.getLogger(FilmeController.class);

    private final FilmeService filmeService;

    @GetMapping
    public String listarFilmes(Model model) {
        List<FilmeDTO> filmes = filmeService.listarTodos();
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
        try {
            filmeService.excluirPorId(id);
        } catch (FilmeNotFoundException ex) {
            logger.warn("Tentativa de excluir filme não encontrado. ID: {}", id);
        }
        return "redirect:/api/v1/filmes";
    }
}
