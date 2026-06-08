package org.example.museutrezemaio.controller;

import org.example.museutrezemaio.model.Editora;
import org.example.museutrezemaio.service.EditoraService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/editora")
public class EditoraController {
    private final EditoraService editoraService;

    public EditoraController(EditoraService editoraService) {
        this.editoraService = editoraService;
    }
    @GetMapping("/editoraFormulario")
    public String exibirFormularioEditora(Model model){
        model.addAttribute("editora",new Editora());
        return "editoraFormulario";
    }

    /* esta testado se o dado é valido antes de tentar salva no banco de dados */
    @PostMapping("/salvarEditora")
    public String salvarEditora(@Validated @ModelAttribute Editora editora, BindingResult result) {
        if (result.hasErrors()) {
            return "editoraFormulario";
        }
        try {
            editoraService.salvar(editora);

        } catch (IllegalArgumentException e) {
            result.reject("erro", e.getMessage());
            return "editoraFormulario";
        }
        return "redirect:/editora/editoraLista";
    }

    @GetMapping("/editoraLista")
    public String editoraLista(Model model) {
        List<Editora> editoras = editoraService.listaEditoras();
        model.addAttribute("editoras", editoras);
        return "editoraLista";
    }

    @GetMapping("/editar/{idEditora}")
    public String editarEditora(@PathVariable int idEditora, Model model) {
        Editora editora = editoraService.EditarId(idEditora).orElseThrow(() -> new RuntimeException("Editora não encontrado"));
        model.addAttribute("editora", editora);
        return "editoraFormulario";
    }
}
