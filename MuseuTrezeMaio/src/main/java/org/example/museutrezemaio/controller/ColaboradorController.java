package org.example.museutrezemaio.controller;


import org.example.museutrezemaio.model.Assunto;
import org.example.museutrezemaio.model.Colaborador;
import org.example.museutrezemaio.service.ColaboradorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/colaborador")
public class ColaboradorController {
    private final ColaboradorService colaboradorService;

    public ColaboradorController(ColaboradorService colaboradorService) {
        this.colaboradorService = colaboradorService;
    }

    @GetMapping("/colaboradorFormulario")
    public String exibirFormularioColaborador(Model model){
        model.addAttribute("colaborador",new Colaborador());
        return "colaboradorFormulario";
    }

    @PostMapping("/salvarColaborador")
    public String salvarColaborador(@Validated @ModelAttribute Colaborador colaborador, BindingResult result) {
        if (result.hasErrors()) {
            return "colaboradorFormulario";
        }
        if (colaboradorService.existeNomeSobrenomeTipo(colaborador.getNome(),colaborador.getSobrenome(),colaborador.getTipo(),colaborador.getIdColaborador())) {
            result.reject(
                    "colaborador.duplicado",
                    "Já existe um colaborador com esse nome, sobrenome e tipo."
            );
            return "colaboradorFormulario";
        }
        colaboradorService.salvar(colaborador);
        return "redirect:/colaborador/colaboradorLista";
    }

    @GetMapping("/colaboradorLista")
    public String colaboradorLista(Model model) {
        List<Colaborador> colaboradores = colaboradorService.listarColaboradores();
        model.addAttribute("colaboradores", colaboradores);
        return "colaboradorLista";
    }

    @GetMapping("/editar/{idColaborador}")
    public String editarColaborador(@PathVariable int idColaborador, Model model) {
        Colaborador colaborador = colaboradorService.EditarId(idColaborador).orElseThrow(() -> new RuntimeException("Colaborador não encontrado"));
        model.addAttribute("colaborador", colaborador);
        return "colaboradorFormulario";
    }


}
