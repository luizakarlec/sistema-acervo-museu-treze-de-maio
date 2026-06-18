package org.example.museutrezemaio.controller;

import org.example.museutrezemaio.model.Assunto;
import org.example.museutrezemaio.service.AssuntoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/assunto")
public class AssuntoController {

    private final AssuntoService assuntoService;

    public AssuntoController(AssuntoService assuntoService) {
        this.assuntoService = assuntoService;
    }


    /*esta testado se o dado é valido antes de tentar salva no banco de dados */
    @PostMapping("/salvarAssunto")
    public String salvarAssunto(@Validated @ModelAttribute Assunto assunto, BindingResult result, @RequestParam(defaultValue = "0") int page,Model model,  RedirectAttributes redirectAttributes) {
        //validação dos campos obrigatórios
        if (result.hasErrors()) {
            Page<Assunto> paginaAssuntos = assuntoService.listarAssuntos(page);
            model.addAttribute("assunto", assunto);
            model.addAttribute("paginaAssuntos", paginaAssuntos);

            return "assunto";
        }
        try{
            assuntoService.salvar(assunto);
        }catch(IllegalArgumentException e){
            result.rejectValue("descricao", "error.assunto", e.getMessage());
            Page<Assunto> paginaAssuntos = assuntoService.listarAssuntos(page);
            model.addAttribute("assuntos", paginaAssuntos);
            model.addAttribute("paginaAssuntos", paginaAssuntos);
            return "assunto";
        }

        if (assunto.getIdAssunto() > 0) {
            redirectAttributes.addFlashAttribute(
                    "mensagemSucesso",
                    "Assunto atualizado com sucesso!"
            );
        } else {
            redirectAttributes.addFlashAttribute(
                    "mensagemSucesso",
                    "Assunto cadastrado com sucesso!"
            );
        }

        return "redirect:/assunto/lista";
    }

    @GetMapping("/lista")
    public String assuntoLista(@RequestParam(defaultValue = "0") int page, Model model) {
        Page<Assunto> paginaAssuntos = assuntoService.listarAssuntos(page);
        List<Assunto>todosAssuntos = assuntoService.buscarPorDescricao("");

        model.addAttribute("assunto", new Assunto());
        model.addAttribute("paginaAssuntos", paginaAssuntos);
        model.addAttribute("todosAssuntos", todosAssuntos); //lista para o JS

        return "assunto";
    }

    @GetMapping("/editar/{idAssunto}")
    public String editarAssunto(@PathVariable int idAssunto,@RequestParam(defaultValue = "0") int page, Model model) {
        Assunto assunto = assuntoService.EditarId(idAssunto).orElseThrow(() -> new RuntimeException("Assunto não encontrado"));
        Page<Assunto> paginaAssuntos = assuntoService.listarAssuntos(page);
        model.addAttribute("assunto", assunto);
        model.addAttribute("paginaAssuntos", paginaAssuntos);
        return "assunto";
    }



}
