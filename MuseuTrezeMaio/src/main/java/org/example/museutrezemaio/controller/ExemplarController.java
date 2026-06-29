package org.example.museutrezemaio.controller;

import org.example.museutrezemaio.model.Assunto;
import org.example.museutrezemaio.model.Exemplar;
import org.example.museutrezemaio.service.ExemplarService;
import org.example.museutrezemaio.service.ItemAcervoService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    /*esta testado se o dado é valido antes de tentar salva no banco de dados */
    @PostMapping("/salvarExemplar")
    public String salvarExemplar(@Validated @ModelAttribute Exemplar exemplar, BindingResult result, @RequestParam(defaultValue = "0") int page, Model model, RedirectAttributes redirectAttributes) {
        //validação dos campos obrigatórios
        if (result.hasErrors()) {
            Page<Exemplar> paginaExemplares = exemplarService.listarExemplaresPaginas(page);
            model.addAttribute("exemplar", exemplar);
            model.addAttribute("paginaExemplares", paginaExemplares);
            model.addAttribute("listaItemAcervo", itemAcervoService.listarTodos());

            return "exemplar";
        }
        try{
            exemplarService.salvarExemplar(exemplar);
        }catch(IllegalArgumentException e){
            result.reject("erro", e.getMessage());
            Page<Exemplar> paginaExemplares = exemplarService.listarExemplaresPaginas(page);
            model.addAttribute("exemplar", exemplar);
            model.addAttribute("paginaExemplares", paginaExemplares);
            model.addAttribute("listaItemAcervo", itemAcervoService.listarTodos());

            return "exemplar";
        }

        if (exemplar.getIdExemplar() > 0) {
            redirectAttributes.addFlashAttribute(
                    "mensagemSucesso",
                    "Exemplar atualizado com sucesso!"
            );
        } else {
            redirectAttributes.addFlashAttribute(
                    "mensagemSucesso",
                    "Exemplar cadastrado com sucesso!"
            );
        }

        return "redirect:/exemplar/lista";
    }

    @GetMapping("/lista")
    public String exemplarLista(@RequestParam(defaultValue = "0") int page, Model model) {
        Page<Exemplar> paginaExemplares = exemplarService.listarExemplaresPaginas(page);
        List<Exemplar> todosExemplares = exemplarService.buscarPorCodigo("");

        model.addAttribute("exemplar", new Exemplar());
        model.addAttribute("paginaExemplares", paginaExemplares);
        model.addAttribute("listaItemAcervo", itemAcervoService.listarTodos()); //
        model.addAttribute("todosExemplares", todosExemplares); //lista para o JS

        return "exemplar";
    }

    @PostMapping("/ajax")
    @ResponseBody
    public ResponseEntity<?> salvarAjax(@RequestBody Exemplar exemplar) {

        try {
            Exemplar exemplarSalvo = exemplarService.salvarExemplar(exemplar);
            return ResponseEntity.ok(exemplarSalvo);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());

        }

    }

    @GetMapping("/editar/{idExemplar}")
    public String editarExemplar(@PathVariable int idExemplar,@RequestParam(defaultValue = "0") int page, Model model) {
        Exemplar exemplar = exemplarService.editarId(idExemplar).orElseThrow(() -> new RuntimeException("Exemplar não encontrado"));
        Page<Exemplar> paginaExemplar = exemplarService.listarExemplaresPaginas(page);
        model.addAttribute("exemplar", exemplar);
        model.addAttribute("listaItemAcervo", itemAcervoService.listarTodos()); //
        model.addAttribute("paginaExemplares", paginaExemplar);
        return "exemplar";
    }
}
