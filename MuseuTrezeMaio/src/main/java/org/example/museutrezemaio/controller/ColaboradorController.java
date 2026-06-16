package org.example.museutrezemaio.controller;


import org.example.museutrezemaio.model.Assunto;
import org.example.museutrezemaio.model.Colaborador;
import org.example.museutrezemaio.service.ColaboradorService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/colaborador")
public class ColaboradorController {
    private final ColaboradorService colaboradorService;

    public ColaboradorController(ColaboradorService colaboradorService) {
        this.colaboradorService = colaboradorService;
    }

    @PostMapping("/salvarColaborador")
    public String salvarColaborador(@Validated @ModelAttribute Colaborador colaborador, BindingResult result, @RequestParam(defaultValue = "0") int page,Model model,  RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            Page<Colaborador>paginaColaboradores = colaboradorService.listarTodosColaboradores(page);
            model.addAttribute("colaborador", colaborador);
            model.addAttribute("paginaColaboradores", paginaColaboradores);
            return "colaborador";
        }

        try{
            colaboradorService.salvar(colaborador);
        }catch (IllegalArgumentException e){
            result.reject("erro.global.colaborador",e.getMessage());
            Page<Colaborador>paginaColaboradores = colaboradorService.listarTodosColaboradores(page);
            model.addAttribute("colaborador", colaborador);
            model.addAttribute("paginaColaboradores", paginaColaboradores);
            return "colaborador";
        }
        if (colaborador.getIdColaborador() > 0) {
            redirectAttributes.addFlashAttribute(
                    "mensagemSucesso",
                    "Colaborador atualizado com sucesso!"
            );
        } else {
            redirectAttributes.addFlashAttribute(
                    "mensagemSucesso",
                    "Colaborador cadastrado com sucesso!"
            );
        }

        return "redirect:/colaborador/lista";
    }

    @GetMapping("/lista")
    public String colaboradorLista(@RequestParam(defaultValue = "0") int page,Model model) {
        Page<Colaborador>paginaColaboradores = colaboradorService.listarTodosColaboradores(page);
        model.addAttribute("colaborador", new Colaborador());
        model.addAttribute("paginaColaboradores", paginaColaboradores);
        return "colaborador";
    }

    @GetMapping("/editar/{idColaborador}")
    public String editarColaborador(@PathVariable int idColaborador,@RequestParam(defaultValue = "0") int page,Model model) {
        Colaborador colaborador = colaboradorService.EditarId(idColaborador).orElseThrow(() -> new RuntimeException("Colaborador não encontrado"));
        Page<Colaborador>paginaColaboradores = colaboradorService.listarTodosColaboradores(page);
        model.addAttribute("colaborador", colaborador);
        model.addAttribute("paginaColaboradores", paginaColaboradores);
        return "colaborador";
    }


}
