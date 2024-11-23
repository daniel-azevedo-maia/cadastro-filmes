package com.danielazevedo.mycinechecker.application.controller;

import com.danielazevedo.mycinechecker.application.dto.LoginDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@RequiredArgsConstructor
@Controller
public class HomeController {

    @GetMapping("/")
    public String redirecionarParaLoginOuHome() {
        // Verifica se o usuário está autenticado
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() &&
                !(authentication instanceof AnonymousAuthenticationToken)) {
            // Se autenticado, redireciona para /home
            return "redirect:/home";
        }
        // Se não autenticado, redireciona para /auth/login
        return "redirect:/auth/login";
    }

    @GetMapping("/home")
    public String home(Model model, Principal principal) {
        String username = principal.getName();
        model.addAttribute("username", username);

        return "index";
    }

}
