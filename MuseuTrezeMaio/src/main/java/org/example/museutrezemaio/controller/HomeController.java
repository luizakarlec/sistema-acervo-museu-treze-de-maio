package org.example.museutrezemaio.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/index")
    public String index() {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

//    Configurar campos somente de admin em SecurityConfig
//    @GetMapping("/adminpanel")
//    public String adminPanel() {
//        return "adminpanel";
//    }
}