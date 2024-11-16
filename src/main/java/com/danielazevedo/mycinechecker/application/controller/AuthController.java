package com.danielazevedo.mycinechecker.application.controller;

import com.danielazevedo.mycinechecker.application.dto.CadastroDTO;
import com.danielazevedo.mycinechecker.application.exception.UsuarioExistenteException;
import com.danielazevedo.mycinechecker.application.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    @GetMapping("/cadastro")
    public String renderizarPaginaCadastro(Model model) {
        model.addAttribute("objCadastro", new CadastroDTO());
        return "cadastro"; // Retorna o template cadastro.html
    }

    @PostMapping("/cadastro")
    public String realizarCadastro(@ModelAttribute("objCadastro") CadastroDTO cadastroDTO, Model model) {
        try {
            userService.cadastrarNovoUsuario(cadastroDTO);
            model.addAttribute("successMessage", "Cadastro realizado com sucesso!");
            return "redirect:/auth/login";
        } catch (UsuarioExistenteException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "cadastro";
        }
    }

    @GetMapping("/login")
    public String renderizarPaginaLogin() {
        return "login";
    }
}
