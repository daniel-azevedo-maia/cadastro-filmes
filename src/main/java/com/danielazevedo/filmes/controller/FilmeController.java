package com.danielazevedo.filmes.controller;

import com.danielazevedo.filmes.model.Filme;
import com.danielazevedo.filmes.repository.FilmeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
@RequestMapping("/filmes")
public class FilmeController {

    @Autowired
    private FilmeRepository filmeRepository;

    @GetMapping("/cadastrofilme")
    public ModelAndView inicio() {
        ModelAndView modelAndView = new ModelAndView("cadastro/cadastrofilme");
        modelAndView.addObject("filmeobj", new Filme());
        return modelAndView;
    }

    @GetMapping("/listarfilmes")
    public ModelAndView listarFilmes() {
        ModelAndView modelAndView = new ModelAndView("cadastro/cadastrofilme");
        modelAndView.addObject("filmes", filmeRepository.findAll());
        modelAndView.addObject("filmeobj", new Filme());
        return modelAndView;
    }

    @PostMapping("/cadastrarfilme")
    public ModelAndView cadastrarFilme(Filme filme) {
        filmeRepository.save(filme);
        ModelAndView modelAndView = new ModelAndView("cadastro/cadastrofilme");
        modelAndView.addObject("filmes", filmeRepository.findAll());
        modelAndView.addObject("filmeobj", new Filme());
        return modelAndView;
    }

    @GetMapping("/editarfilme/{idfilme}")
    public ModelAndView editarFilme(@PathVariable("idfilme") Long idfilme) {
        Optional<Filme> filme = filmeRepository.findById(idfilme);

        ModelAndView modelAndView = new ModelAndView("cadastro/cadastrofilme");
        modelAndView.addObject("filmeobj", filme.get());

        return modelAndView;
    }

    @GetMapping("/excluirfilme/{idfilme}")
    public ModelAndView excluirFilme(@PathVariable("idfilme") Long idfilme) {
        filmeRepository.deleteById(idfilme);

        ModelAndView modelAndView = new ModelAndView("cadastro/cadastrofilme");
        modelAndView.addObject("filmes",filmeRepository.findAll());
        modelAndView.addObject("filmeobj", new Filme());
        return modelAndView;

    }

    /*
        @RequestParam é usado para extrair valores de parâmetros de consulta da URL.
        Estes não são parte do caminho da URL, mas são anexados ao final dela
        após um ponto de interrogação. Por exemplo,
        na URL /pesquisarfilme?nomepesquisa=Star+Wars, nomepesquisa é um
        parâmetro de consulta cujo valor é Star+Wars.

        @PathVariable é usado para extrair valores que são parte do caminho da URL.
        Por exemplo, na URL /pesquisarfilme/Star+Wars, Star+Wars é uma variável
        de caminho que pode ser acessada com @PathVariable.
    */

    @PostMapping("/pesquisarfilme")
    public ModelAndView pesquisarFilme(@RequestParam("nomepesquisa") String nomepesquisa) {
        ModelAndView modelAndView = new ModelAndView("cadastro/cadastrofilme");
        modelAndView.addObject("filmes", filmeRepository.findFilmeByTitulo(nomepesquisa));
        modelAndView.addObject("filmeobj", new Filme());
        return modelAndView;
    }
}
