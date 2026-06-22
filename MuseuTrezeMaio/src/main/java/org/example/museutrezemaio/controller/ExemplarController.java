package org.example.museutrezemaio.controller;

import org.example.museutrezemaio.model.Exemplar;
import org.example.museutrezemaio.model.ItemAcervo;
import org.example.museutrezemaio.service.ExemplarService;
import org.example.museutrezemaio.service.ItemAcervoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/exemplar")
public class ExemplarController {
    private final ExemplarService exemplarService;
    private final ItemAcervoService itemAcervoService;

    public ExemplarController(ExemplarService exemplarService, ItemAcervoService itemAcervoService) {
        this.exemplarService = exemplarService;
        this.itemAcervoService = itemAcervoService;
    }
    @GetMapping("/exemplarFormulario")
    public String exibirFormularioExemplar(Model model){
        model.addAttribute("exemplar",new Exemplar());
        model.addAttribute("listaItemAcervo", itemAcervoService.listarTodos());
        return "exemplarFormulario";
    }

    /*esta testado se o dado é valido antes de tentar salva no banco de dados */
    @PostMapping("/salvarExemplar")
    public String salvarExemplar(@Validated @ModelAttribute Exemplar exemplar, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("listaItemAcervo", itemAcervoService.listarTodos());
            return "exemplarFormulario";
        }
        try {
            exemplarService.salvar(exemplar);

        } catch (IllegalArgumentException e) {
            result.reject("erro", e.getMessage());
            model.addAttribute("listaItemAcervo", itemAcervoService.listarTodos());
            return "exemplarFormulario";
        }
        return "redirect:/exemplar/exemplarLista";

    }

    @GetMapping("/exemplarLista")
    public String exemplarLista(Model model) {
        List<Exemplar> exemplares = exemplarService.listar();
        model.addAttribute("exemplares", exemplares);
        return "exemplarLista";
    }

    @GetMapping("/exemplar/{idExemplar}")
    public String editarExemplar(@PathVariable int idExemplar, Model model) {
        Exemplar exemplar = exemplarService.editarId(idExemplar).orElseThrow(() -> new RuntimeException("Exemplar não encontrado"));
        model.addAttribute("exemplar", exemplar);
        model.addAttribute("listaItemAcervo", itemAcervoService.listarTodos());
        return "exemplarFormulario";
    }
}
