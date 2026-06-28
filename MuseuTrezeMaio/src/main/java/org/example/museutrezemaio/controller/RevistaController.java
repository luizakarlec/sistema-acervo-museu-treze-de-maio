package org.example.museutrezemaio.controller;


import org.example.museutrezemaio.model.Revista;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.example.museutrezemaio.service.EditoraService;
import org.example.museutrezemaio.service.RevistaService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URLConnection;
import java.util.List;

@Controller
@RequestMapping("/revista")
public class RevistaController {
    private final RevistaService revistaService;
    private final EditoraService editoraService;

    public RevistaController(RevistaService revistaService, EditoraService editoraService) {
        this.revistaService = revistaService;
        this.editoraService = editoraService;
    }

    @GetMapping("/revistaFormulario")
    public String exibirFormularioRevista(Model model) {
        model.addAttribute("revista",new Revista());
        model.addAttribute("listaEditoras", editoraService.listarEditorasAtivas());
        return "revistaFormulario";
    }

    @PostMapping("/salvarRevista")
    public String salvarRevista(@Validated @ModelAttribute("revista") Revista revista, BindingResult result, @RequestParam("fileCapa") MultipartFile fileCapa, Model model) {

        if (result.hasErrors()) {
            model.addAttribute("listaEditoras", editoraService.listaEditoras());
            return "revistaFormulario";
        }

        try {
            revistaService.salvar(revista, fileCapa);
        } catch (IllegalArgumentException e) {
            result.reject("erro", e.getMessage());
            model.addAttribute("listaEditoras", editoraService.listaEditoras());
            return "revistaFormulario";
        } catch (Exception e) {
            result.reject("erro", "Erro ao processar a imagem.");
            model.addAttribute("listaEditoras", editoraService.listaEditoras());
            return "revistaFormulario";
        }

        return "redirect:/revista/revistaLista";
    }

    @GetMapping("revistaLista")
    public String revistaLista(@RequestParam(defaultValue = "0") int page,Model model) {
        List<Revista> revistas = revistaService.listar();
        Page<Revista> paginaRevistas = revistaService.listarRevistaPag(page);

        model.addAttribute("listaRevistas", revistas);
        model.addAttribute("paginaRevistas", paginaRevistas);
        return "revistaLista";
    }

    @GetMapping("/editar/{idItemAcervo}") //tambem podia ser "buscarId"
    public String editarRevista(@PathVariable int idItemAcervo, Model model) {
        Revista revista = revistaService.editar(idItemAcervo).orElseThrow(() -> new RuntimeException("Revista não encontrado"));

        model.addAttribute("revista", revista);
        model.addAttribute("listaEditoras", editoraService.listaEditoras());
        return "revistaFormulario";
    }

    @GetMapping("/detalhes/{id}")
    public String detalhesRevista (@PathVariable int id, Model model) {
        Revista revista = revistaService.editar(id)
                .orElseThrow(() -> new RuntimeException("Revista não encontrado"));

        model.addAttribute("revista", revista);
        return "revistaDetalhes";
    }

    @GetMapping("/capa/{id}")
    public ResponseEntity<byte[]> getCapa(@PathVariable int id) {
        Revista revista = revistaService.editar(id)
                .orElseThrow(() -> new RuntimeException("Revista não encontrado"));

        byte[] capa = revista.getCapa();

        if (capa == null || capa.length == 0) {
            return ResponseEntity.notFound().build();
        }

        HttpHeaders headers = new HttpHeaders();

        String mimeType = null;
        try (InputStream is = new ByteArrayInputStream(capa)) {
            mimeType = URLConnection.guessContentTypeFromStream(is);
        } catch (Exception e) {

        }

        if (mimeType != null) {
            headers.setContentType(MediaType.parseMediaType(mimeType));
        } else {
            headers.setContentType(MediaType.IMAGE_JPEG); // Fallback caso não consiga identificar
        }

        return new ResponseEntity<>(capa, headers, HttpStatus.OK);
    }
}

