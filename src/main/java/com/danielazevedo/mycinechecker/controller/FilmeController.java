package com.danielazevedo.mycinechecker.controller;

import com.danielazevedo.mycinechecker.dto.FilmeDTO;
import com.danielazevedo.mycinechecker.service.FilmeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/filmes")
public class FilmeController {

    @Autowired
    private FilmeService filmeService;

    @GetMapping("/")
    public String listarFilmes(Model model) {
        model.addAttribute("filmes", filmeService.listarTodos());
        return "filmes/listar"; // Renderiza templates/filmes/listar.html
    }

    @GetMapping("/cadastrofilme")
    public String mostrarCadastroFilme(Model model) {
        model.addAttribute("filmeobj", new FilmeDTO());
        return "cadastro/index"; // Renderiza templates/cadastro/index.html
    }

    @PostMapping("/cadastrarfilme")
    public String cadastrarFilme(@ModelAttribute("filmeobj") FilmeDTO filmeDTO) {
        filmeService.salvar(filmeDTO);
        return "redirect:/filmes/"; // Redireciona para a lista de filmes
    }

    @GetMapping("/excluir/{id}")
    public String excluirFilme(@PathVariable("id") Long id) {
        filmeService.excluirPorId(id);
        return "redirect:/filmes/";
    }
}
