package org.example.museutrezemaio.controller;


import org.example.museutrezemaio.model.Assunto;
import org.example.museutrezemaio.model.Colaborador;
import org.example.museutrezemaio.model.Revista;
import org.example.museutrezemaio.service.AssuntoService;
import org.example.museutrezemaio.service.ColaboradorService;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URLConnection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/revista")
public class RevistaController {
    private final RevistaService revistaService;
    private final EditoraService editoraService;
    private final ColaboradorService colaboradorService;
    private final AssuntoService assuntoService;

    public RevistaController(RevistaService revistaService,EditoraService editoraService, ColaboradorService colaboradorService, AssuntoService assuntoService) {
        this.revistaService = revistaService;
        this.editoraService = editoraService;
        this.colaboradorService = colaboradorService;
        this.assuntoService = assuntoService;
    }

    @GetMapping("/revistaFormulario")
    public String exibirFormularioRevista(Model model) {
        Revista revista = new Revista();
        model.addAttribute("revista",revista);
        carregarListasFormulario(model, revista);
        return "revistaFormulario";
    }

    @PostMapping("/salvarRevista")
    public String salvarRevista(@Validated @ModelAttribute("revista") Revista revista,
            BindingResult result,
            @RequestParam("fileCapa") MultipartFile fileCapa,
            @RequestParam(value = "colaboradoresIds", required = false) List<Integer> colaboradoresIds,
            @RequestParam(value = "assuntosIds", required = false) List<Integer> assuntosIds,
            Model model,
            RedirectAttributes redirectAttributes){

        if (result.hasErrors()) {
            carregarListasFormulario(model, revista);
            return "revistaFormulario";
        }

        try {
            revistaService.salvar(revista, fileCapa, colaboradoresIds, assuntosIds);
        } catch (IllegalArgumentException e) {
            result.reject("erro", e.getMessage());
            carregarListasFormulario(model, revista);
            return "revistaFormulario";
        } catch (Exception e) {
            e.printStackTrace();
            result.reject("erro", "Erro ao processar a imagem.");
            carregarListasFormulario(model, revista);
            return "revistaFormulario";
        }

        String mensagem = revista.getIdItemAcervo() > 0
                ? "Revista atualizada com sucesso!"
                : "Revista cadastrada com sucesso!";
        redirectAttributes.addFlashAttribute("mensagemSucesso", mensagem);
        return "redirect:/revista/revistaLista";
    }

    @GetMapping("/revistaLista")
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
        carregarListasFormulario(model, revista);

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

    private void carregarListasFormulario(Model model, Revista revista) {
        model.addAttribute("listaEditoras", editoraService.listarEditorasAtivas());
        model.addAttribute("listaColaboradores", colaboradorService.listarColaboradoresAtivos());
        model.addAttribute("listaAssuntos", assuntoService.listarAssuntosAtivos());

        List<Integer> colaboradoresIds = (revista.getColaboradores() != null)
                ? revista.getColaboradores().stream().map(Colaborador::getIdColaborador).collect(Collectors.toList())
                : Collections.emptyList();
        List<Integer> assuntosIds = (revista.getAssuntos() != null)
                ? revista.getAssuntos().stream().map(Assunto::getIdAssunto).collect(Collectors.toList())
                : Collections.emptyList();

        model.addAttribute("selectedColaboradoresIds", colaboradoresIds);
        model.addAttribute("selectedAssuntosIds", assuntosIds);
    }
}

