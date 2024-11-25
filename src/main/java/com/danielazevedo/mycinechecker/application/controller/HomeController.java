package com.danielazevedo.mycinechecker.application.controller;

import com.danielazevedo.mycinechecker.application.exception.UsuarioNaoEncontradoException;
import com.danielazevedo.mycinechecker.application.service.UserService;
import com.danielazevedo.mycinechecker.domain.model.User;
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

    private final UserService userService;

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
        // Adiciona o nome do usuário ao modelo ou exibe mensagem de visitante
        adicionarUsuarioAoModelo(model, principal);

        return "index";
    }

    /**
     * Método auxiliar para adicionar o nome do usuário ao modelo.
     * Se o usuário não estiver autenticado ou não for encontrado, exibe mensagem de "Visitante".
     */
    private void adicionarUsuarioAoModelo(Model model, Principal principal) {
        if (principal == null) {
            model.addAttribute("nome", "Visitante");
            return;
        }

        String username = principal.getName();
        try {
            User user = userService.buscarUsuarioPeloUsername(username);
            model.addAttribute("nome", user.getNome());
        } catch (UsuarioNaoEncontradoException ex) {
            model.addAttribute("nome", "Usuário não encontrado");
        }
    }
}
