package com.danielazevedo.mycinechecker.application.controller;

import com.danielazevedo.mycinechecker.application.dto.FilmeDTO;
import com.danielazevedo.mycinechecker.application.exception.FilmeNotFoundException;
import com.danielazevedo.mycinechecker.application.exception.UsuarioNaoEncontradoException;
import com.danielazevedo.mycinechecker.application.service.FilmeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/filmes")
@RequiredArgsConstructor
public class FilmeController {

    private static final Logger logger = LoggerFactory.getLogger(FilmeController.class);

    private final FilmeService filmeService;

    @GetMapping
    public String listarFilmes(Model model, Principal principal) {

        String username = principal.getName();
        model.addAttribute("username", username);

        List<FilmeDTO> filmes = filmeService.listarTodos();
        if (filmes == null) {
            filmes = List.of(); // Inicializa com uma lista vazia
        }
        model.addAttribute("filmes", filmes);
        return "filmes/listagem";
    }

    @GetMapping("/novo")
    public String mostrarFormularioCadastro(Model model, Principal principal) {

        String username = principal.getName();
        model.addAttribute("username", username);

        model.addAttribute("filmeobj", new FilmeDTO());
        return "filmes/registro";
    }

    @PostMapping
    public String cadastrarFilme(
            @Valid @ModelAttribute("filmeobj") FilmeDTO filmeDTO,
            BindingResult result,
            Model model,
            Principal principal) {
        // Adiciona o username ao modelo
        String username = principal.getName();
        model.addAttribute("username", username);

        if (result.hasErrors()) {
            logger.error("Erro ao validar os dados do filme: {}", result.getAllErrors());
            return "filmes/registro";
        }
        try {
            filmeService.salvar(filmeDTO);
        } catch (UsuarioNaoEncontradoException ex) {
            logger.error("Erro ao associar filme ao usuário: {}", ex.getMessage());
            model.addAttribute("errorMessage", "Usuário não encontrado para salvar o filme.");
            return "filmes/registro";
        }
        return "redirect:/filmes";
    }

    @GetMapping("/{id}/editar")
    public String mostrarFormularioEdicao(@PathVariable("id") Long id, Model model, Principal principal) {
        // Adiciona o username ao modelo
        String username = principal.getName();
        model.addAttribute("username", username);

        try {
            FilmeDTO filme = filmeService.buscarPorId(id);
            model.addAttribute("filmeobj", filme);
            return "filmes/registro";
        } catch (FilmeNotFoundException ex) {
            logger.warn("Tentativa de edição para filme não encontrado: ID {}", id);
            return "redirect:/filmes";
        }
    }

    @PostMapping("/{id}")
    public String atualizarFilme(
            @PathVariable("id") Long id,
            @Valid @ModelAttribute("filmeobj") FilmeDTO filmeDTO,
            BindingResult result,
            Model model,
            Principal principal) {
        // Adiciona o username ao modelo
        String username = principal.getName();
        model.addAttribute("username", username);

        if (result.hasErrors()) {
            logger.error("Erro ao validar os dados do filme: {}", result.getAllErrors());
            return "filmes/registro";
        }
        try {
            filmeDTO.setId(id);
            filmeService.salvar(filmeDTO);
        } catch (UsuarioNaoEncontradoException ex) {
            logger.error("Erro ao associar filme ao usuário: {}", ex.getMessage());
            model.addAttribute("errorMessage", "Usuário não encontrado para atualizar o filme.");
            return "filmes/registro";
        }
        return "redirect:/filmes";
    }

    @DeleteMapping("/{id}")
    public String excluirFilme(@PathVariable("id") Long id, Model model, Principal principal) {
        // Adiciona o username ao modelo
        String username = principal.getName();
        model.addAttribute("username", username);

        try {
            filmeService.excluirPorId(id);
        } catch (FilmeNotFoundException ex) {
            logger.warn("Tentativa de excluir filme não encontrado: ID {}", id);
        }
        return "redirect:/filmes";
    }
}
