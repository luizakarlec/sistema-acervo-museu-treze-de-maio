package org.example.museutrezemaio.controller;

import org.example.museutrezemaio.model.Jornal;
import org.example.museutrezemaio.model.Localizacao;
import org.example.museutrezemaio.service.EditoraService;
import org.example.museutrezemaio.service.JornalService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("/jornal")
public class JornalController {
    private final JornalService jornalService;
    private final EditoraService editoraService;

    public JornalController(JornalService jornalService, EditoraService editoraService) {
        this.jornalService = jornalService;
        this.editoraService = editoraService;
    }
    @GetMapping("/jornalFormulario")
    public String exibirFormularioJonral(Model model){
        model.addAttribute("jornal",new Jornal());
        model.addAttribute("listaEditoras", editoraService.listaEditoras());
        return "jornalFormulario";
    }

    @PostMapping("/salvarJornal")
    public String salvarJornal(@Validated @ModelAttribute("jornal") Jornal jornal, BindingResult result, @RequestParam("fileCapa") MultipartFile fileCapa, Model model) {

        if (result.hasErrors()) {
            model.addAttribute("listaEditoras", editoraService.listaEditoras());
            return "jornalFormulario";
        }

        try {
            jornalService.salvar(jornal, fileCapa);
        } catch (IllegalArgumentException e) {
            result.reject("erro", e.getMessage());
            model.addAttribute("listaEditoras", editoraService.listaEditoras());
            return "jornalFormulario";
        } catch (Exception e) {
            result.reject("erro", "Erro ao processar a imagem.");
            model.addAttribute("listaEditoras", editoraService.listaEditoras());
            return "jornalFormulario";
        }

        return "redirect:/jornal/jornalLista";
    }

    @GetMapping("/jornalLista")
    public String jornalLista(Model model) {
        List<Jornal> jornais = jornalService.listaJornais();
        model.addAttribute("jornais", jornais);
        return "jornalLista";
    }

    @GetMapping("/editar/{idItemAcervo}")
    public String editarJornal(@PathVariable int idItemAcervo, Model model) {
        Jornal jornal = jornalService.EditarId(idItemAcervo).orElseThrow(() -> new RuntimeException("Jornal não encontrado"));

        if (jornal.getLocalizacao() == null) {
            jornal.setLocalizacao(new Localizacao());
        }

        model.addAttribute("jornal", jornal);
        model.addAttribute("listaEditoras", editoraService.listaEditoras());
        return "jornalFormulario";
    }

    @GetMapping("/detalhes/{id}")
    public String detalhesJornal (@PathVariable int id, Model model) {
        Jornal jornal = jornalService.EditarId(id)
                .orElseThrow(() -> new RuntimeException("Jornal não encontrado"));

        // metodo EditarId é usado na edição mas serve apenas para encontrar o id do jornal

        if (jornal.getLocalizacao() == null) {
            jornal.setLocalizacao(new Localizacao());
        }

        model.addAttribute("jornal", jornal);
        return "jornalDetalhes";
    }

    @GetMapping("/capa/{id}")
    public ResponseEntity<byte[]> getCapa(@PathVariable int id) {
        Jornal jornal = jornalService.EditarId(id)
                .orElseThrow(() -> new RuntimeException("Jornal não encontrado"));

        byte[] capa = jornal.getCapa();

        if (capa == null || capa.length == 0) {
            return ResponseEntity.notFound().build();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);

        return new ResponseEntity<>(capa, headers, HttpStatus.OK);
    }
}