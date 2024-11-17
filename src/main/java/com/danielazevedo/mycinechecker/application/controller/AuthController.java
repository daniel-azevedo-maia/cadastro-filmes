package com.danielazevedo.mycinechecker.application.controller;

import com.danielazevedo.mycinechecker.application.dto.CadastroDTO;
import com.danielazevedo.mycinechecker.application.dto.LoginDTO;
import com.danielazevedo.mycinechecker.application.exception.UsuarioExistenteException;
import com.danielazevedo.mycinechecker.application.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    @GetMapping("/login")
    public String loginPage(@RequestParam(value = "error", required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("errorMessage", "Usuário ou senha inválidos. Tente novamente.");
        }
        model.addAttribute("objLogin", new LoginDTO());
        return "login";
    }

    @PostMapping("/login")
    public String login(
            @ModelAttribute("objLogin") LoginDTO loginDTO,
            Model model) {
        boolean autenticado = userService.validarLogin(loginDTO);
        if (autenticado) {
            return "redirect:/home"; // Redireciona após login bem-sucedido
        }
        model.addAttribute("errorMessage", "Usuário ou senha inválidos.");
        return "login";
    }

    @GetMapping("/cadastro")
    public String cadastroPage(Model model) {
        model.addAttribute("objCadastro", new CadastroDTO());
        return "cadastro";
    }

    @PostMapping("/cadastro")
    public String cadastrarNovoUsuario(
            @Valid @ModelAttribute("objCadastro") CadastroDTO cadastroDTO,
            BindingResult bindingResult,
            Model model) {
        if (bindingResult.hasErrors()) {
            return "cadastro";
        }
        try {
            userService.cadastrarNovoUsuario(cadastroDTO);
            return "redirect:/auth/login";
        } catch (UsuarioExistenteException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "cadastro";
        }
    }
}
