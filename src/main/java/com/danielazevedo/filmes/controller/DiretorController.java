package com.danielazevedo.filmes.controller;

import com.danielazevedo.filmes.model.Diretor;
import com.danielazevedo.filmes.model.Filme;
import com.danielazevedo.filmes.repository.DiretorRepository;
import com.danielazevedo.filmes.repository.FilmeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/diretores")
public class DiretorController {

    @Autowired
    private DiretorRepository diretorRepository;

    @Autowired
    private FilmeRepository filmeRepository;

    @GetMapping("/cadastrodiretor")
    public ModelAndView inicio() {
        ModelAndView modelAndView = new ModelAndView("cadastro/cadastrodiretor");
        modelAndView.addObject("diretorobj", new Diretor());
        return modelAndView;
    }

    @GetMapping("/listardiretores")
    public ModelAndView listarDiretores() {
        ModelAndView modelAndView = new ModelAndView("cadastro/cadastrodiretor");
        modelAndView.addObject("diretores", diretorRepository.findAll());
        modelAndView.addObject("diretorobj", new Diretor());
        return modelAndView;
    }

    @PostMapping("/cadastrardiretor")
    public ModelAndView cadastrarDiretor(Diretor diretor) {
        diretorRepository.save(diretor);
        ModelAndView modelAndView = new ModelAndView("cadastro/cadastrodiretor");
        modelAndView.addObject("diretores", diretorRepository.findAll());
        modelAndView.addObject("diretorobj", new Diretor());
        return modelAndView;
    }

    @GetMapping("/editardiretor/{iddiretor}")
    public ModelAndView editarDiretor(@PathVariable("iddiretor") Long iddiretor) {

        Optional<Diretor> diretor = diretorRepository.findById(iddiretor);

        ModelAndView modelAndView = new ModelAndView("cadastro/cadastrodiretor");
        modelAndView.addObject("diretorobj", diretor.get());

        return modelAndView;
    }


    @GetMapping("/excluirdiretor/{iddiretor}")
    public ModelAndView excluirDiretor(@PathVariable("iddiretor") Long iddiretor) {
        Optional<Diretor> diretor = diretorRepository.findById(iddiretor);

        diretorRepository.deleteById(iddiretor);

        ModelAndView modelAndView = new ModelAndView("cadastro/cadastrodiretor");
        modelAndView.addObject("diretores",diretorRepository.findAll());
        modelAndView.addObject("diretorobj", new Diretor());
        return modelAndView;

    }

    @PostMapping("/pesquisardiretor/nome")
    public ModelAndView pesquisarDiretorNome(@RequestParam("nomepesquisa") String nomepesquisa) {
        ModelAndView modelAndView = new ModelAndView("cadastro/cadastrodiretor");
        modelAndView.addObject("diretores", diretorRepository.findDiretorByNome(nomepesquisa));
        modelAndView.addObject("diretorobj", new Diretor());
        return modelAndView;
    }


}