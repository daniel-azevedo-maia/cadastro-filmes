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

import java.util.List;

@Controller
@RequestMapping("/filmes")
@AllArgsConstructor
public class FilmeController {

    private final FilmeService filmeService;

    @GetMapping("/")
    public String listarFilmes(Model model) {
        List<FilmeDTO> filmes = filmeService.listarTodos();
        model.addAttribute("filmes", filmes);
        return "filmes/listagem";
    }

    @GetMapping("/cadastrofilme")
    public String mostrarCadastroFilme(Model model) {
        model.addAttribute("filmeobj", new FilmeDTO());
        return "filmes/registro";
    }

    @PostMapping("/cadastrarfilme")
    public String cadastrarFilme(
            @Valid @ModelAttribute("filmeobj") FilmeDTO filmeDTO,
            BindingResult result,
            Model model) {
        if (result.hasErrors()) {
            model.addAttribute("filmeobj", filmeDTO);
            return "filmes/registro"; // Corrige os erros no mesmo formulário
        }

        filmeService.salvar(filmeDTO);
        return "redirect:/filmes/";
    }

    @GetMapping("/editarfilme/{id}")
    public String editarFilme(@PathVariable("id") Long id, Model model) {
        FilmeDTO filme = filmeService.buscarPorId(id);
        if (filme == null) {
            throw new FilmeNotFoundException("Filme não encontrado com o ID " + id);
        }
        model.addAttribute("filmeobj", filme);
        return "filmes/registro";
    }

    @GetMapping("/excluir/{id}")
    public String excluirFilme(@PathVariable("id") Long id) {
        filmeService.excluirPorId(id);
        return "redirect:/filmes/";
    }
}
