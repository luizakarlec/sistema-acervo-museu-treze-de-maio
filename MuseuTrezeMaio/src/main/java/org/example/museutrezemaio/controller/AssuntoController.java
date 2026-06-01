package org.example.museutrezemaio.controller;

import org.example.museutrezemaio.model.Assunto;
import org.example.museutrezemaio.service.AssuntoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/assunto")
public class AssuntoController {

    private final AssuntoService assuntoService;

    public AssuntoController(AssuntoService assuntoService) {
        this.assuntoService = assuntoService;
    }

    @GetMapping("/assuntoFormulario")
    public String exibirFormularioAssunto(Model model){
        model.addAttribute("assunto",new Assunto());
        return "assuntoFormulario";
    }

    /*esta testado se o dado é valido antes de tentar salva no banco de dados */
    @PostMapping("/salvarAssunto")
    public String salvarAssunto(@Validated @ModelAttribute Assunto assunto, BindingResult result) {
        //validação dos campos obrigatórios
        if (result.hasErrors()) {
            return "assuntoFormulario";
        }
        try{
            assuntoService.salvar(assunto);
        }catch(IllegalArgumentException e){
            result.rejectValue("descricao", "error.assunto", e.getMessage());
            return "assuntoFormulario";
        }

        return "redirect:/assunto/assuntoLista";
    }

    @GetMapping("/assuntoLista")
    public String assuntoLista(Model model) {
        List<Assunto> assuntos = assuntoService.listarAssuntos();
        model.addAttribute("assuntos", assuntos);
        return "assuntoLista";
    }

    @GetMapping("/editar/{idAssunto}")
    public String editarAssunto(@PathVariable int idAssunto, Model model) {
        Assunto assunto = assuntoService.EditarId(idAssunto).orElseThrow(() -> new RuntimeException("Assunto não encontrado"));
        model.addAttribute("assunto", assunto);
        return "assuntoFormulario";
    }

}
