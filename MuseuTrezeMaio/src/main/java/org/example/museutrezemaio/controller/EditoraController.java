package org.example.museutrezemaio.controller;

import org.example.museutrezemaio.model.Assunto;
import org.example.museutrezemaio.model.Editora;
import org.example.museutrezemaio.service.EditoraService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/editora")
public class EditoraController {
    private final EditoraService editoraService;

    public EditoraController(EditoraService editoraService) {
        this.editoraService = editoraService;
    }


    /* esta testado se o dado é valido antes de tentar salva no banco de dados */
    @PostMapping("/salvarEditora")
    public String salvarEditora(@Validated @ModelAttribute Editora editora, BindingResult result, @RequestParam(defaultValue = "0") int page, Model model,  RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            Page<Editora> paginaEditoras = editoraService.listaTodasEditoras(page);
            model.addAttribute("paginaEditoras", paginaEditoras);
            model.addAttribute("editora", editora);
            return "editora";
        }
        try {
            editoraService.salvar(editora);

        } catch (IllegalArgumentException e) {
            result.reject("erro", e.getMessage());
            Page<Editora> paginaEditoras = editoraService.listaTodasEditoras(page);
            model.addAttribute("paginaEditoras", paginaEditoras);
            model.addAttribute("editora", editora);

            return "editora";
        }
        if (editora.getIdEditora() > 0) {
            redirectAttributes.addFlashAttribute(
                    "mensagemSucesso",
                    "Editora atualizada com sucesso!"
            );
        } else {
            redirectAttributes.addFlashAttribute(
                    "mensagemSucesso",
                    "Editorta cadastrada com sucesso!"
            );
        }
        return "redirect:/editora/lista";
    }

    /*endpoint ajax para pou-up dentro dos demais formularios*/
    @PostMapping("/ajax")
    @ResponseBody
    public ResponseEntity<?> salvarAjax(@RequestBody Editora editora){
        try{
            Editora salva = editoraService.salvar(editora);
            return ResponseEntity.ok(salva);
        }catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/lista")
    public String editoraLista(@RequestParam(defaultValue = "0") int page,Model model) {
        Page<Editora> paginaEditoras = editoraService.listaTodasEditoras(page);
        List<Editora> todasEditoras = editoraService.buscarPorNome("");
        model.addAttribute("paginaEditoras", paginaEditoras);
        model.addAttribute("editora",new Editora());
        model.addAttribute("todasEditoras", todasEditoras); //lista para o JS
        return "editora";
    }

    @GetMapping("/editar/{idEditora}")
    public String editarEditora(@PathVariable int idEditora,@RequestParam(defaultValue = "0") int page, Model model) {
        Editora editora = editoraService.EditarId(idEditora).orElseThrow(() -> new RuntimeException("Editora não encontrado"));
        Page<Editora> paginaEditoras = editoraService.listaTodasEditoras(page);
        model.addAttribute("paginaEditoras", paginaEditoras);
        model.addAttribute("editora",editora);
        return "editora";
    }
}
