package com.danielazevedo.mycinechecker.application.controller;

import com.danielazevedo.mycinechecker.application.dto.CadastroDTO;
import com.danielazevedo.mycinechecker.application.dto.LoginDTO;
import com.danielazevedo.mycinechecker.application.dto.RecaptchaResponse;
import com.danielazevedo.mycinechecker.application.exception.UsuarioExistenteException;
import com.danielazevedo.mycinechecker.application.service.RecaptchaValidationService;
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
    private final RecaptchaValidationService recaptchaValidationService;

    @GetMapping("/login")
    public String loginPage(@RequestParam(value = "error", required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("errorMessage", "Usuário ou senha inválidos. Tente novamente.");
        }
        model.addAttribute("objLogin", new LoginDTO());
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
            @RequestParam("g-recaptcha-response") String recaptchaToken,
            Model model) {

        // Log inicial do método
        System.out.println("=== Iniciando Cadastro ===");
        System.out.println("Token recebido: " + recaptchaToken);
        System.out.println("Dados do Cadastro: " + cadastroDTO);

        if (bindingResult.hasErrors()) {
            System.out.println("=== Erros de Validação ===");
            bindingResult.getAllErrors().forEach(error -> System.out.println("Erro: " + error.getDefaultMessage()));
            return "cadastro";
        }

        // Validação do reCAPTCHA
        try {
            System.out.println("=== Validando reCAPTCHA ===");
            RecaptchaResponse recaptchaResponse = recaptchaValidationService.validate(recaptchaToken, "submit");
            System.out.println("Resultado da Validação: " + recaptchaResponse.isValid());
            System.out.println("Pontuação: " + recaptchaResponse.getScore());

            if (!recaptchaResponse.isValid() || recaptchaResponse.getScore() < 0.5) {
                model.addAttribute("errorMessage", "Validação do reCAPTCHA falhou. Tente novamente.");
                System.out.println("Validação do reCAPTCHA falhou.");
                return "cadastro";
            }
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Erro ao validar o reCAPTCHA: " + e.getMessage());
            System.out.println("Erro ao validar o reCAPTCHA: " + e.getMessage());
            e.printStackTrace();
            return "cadastro";
        }

        // Criação do usuário
        try {
            System.out.println("=== Criando Usuário ===");
            userService.cadastrarNovoUsuario(cadastroDTO);
            System.out.println("Usuário criado com sucesso: " + cadastroDTO.getEmail());
            return "redirect:/auth/login";
        } catch (UsuarioExistenteException e) {
            model.addAttribute("errorMessage", e.getMessage());
            System.out.println("Erro ao criar usuário: " + e.getMessage());
            return "cadastro";
        }
    }


}
