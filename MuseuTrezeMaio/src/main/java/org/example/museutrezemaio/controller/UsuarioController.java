package org.example.museutrezemaio.controller;

import org.example.museutrezemaio.model.Usuario;
import org.example.museutrezemaio.service.UsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {
    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/usuarioFormulario")
    public String exibirFormularioUsuario(Model model){
        model.addAttribute("usuario",new Usuario());
        return "usuarioFormulario";
    }

    @PostMapping("/salvarUsuario")
    public String salvarUsuario(@Validated @ModelAttribute("usuario") Usuario usuario, BindingResult result, Model model) {

        if (result.hasErrors()) {
            return "usuarioFormulario";
        }

        try {
            usuarioService.salvar(usuario);
        } catch (IllegalArgumentException e) {
            result.reject("erro", e.getMessage());
            return "usuarioFormulario";
        }

        return "redirect:/usuario/usuarioLista";
    }

    @GetMapping("/usuarioLista")
    public String usuarioLista(Model model) {
        List<Usuario> usuarios = usuarioService.listaUsuarios();
        model.addAttribute("listaUsuarios", usuarios);
        return "usuarioLista";
    }

    @GetMapping("/editar/{idUsuario}")
    public String editarUsuario(@PathVariable Long idUsuario, Model model) {
        Usuario usuario = usuarioService.BuscarId(idUsuario).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        model.addAttribute("usuario", usuario);
        return "usuarioFormulario";
    }
}
