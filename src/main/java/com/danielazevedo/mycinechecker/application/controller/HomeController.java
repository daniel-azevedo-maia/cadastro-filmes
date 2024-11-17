package com.danielazevedo.mycinechecker.application.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class HomeController {

    @GetMapping("/")
    public String redirecionarParaLogin() {
        return "redirect:/auth/login";
    }

    @GetMapping("/home")
    public String home() {
        return "index";
    }
}
