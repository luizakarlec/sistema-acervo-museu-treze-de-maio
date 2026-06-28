package org.example.museutrezemaio.controller;

import org.example.museutrezemaio.model.Assunto;
import org.example.museutrezemaio.model.Colaborador;
import org.example.museutrezemaio.model.Jornal;
import org.example.museutrezemaio.model.Localizacao;
import org.example.museutrezemaio.service.AssuntoService;
import org.example.museutrezemaio.service.ColaboradorService;
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

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URLConnection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/jornal")
public class JornalController {
    private final JornalService jornalService;
    private final EditoraService editoraService;
    private final ColaboradorService colaboradorService;
    private final AssuntoService  assuntoService;

    public JornalController(JornalService jornalService, EditoraService editoraService, ColaboradorService colaboradorService, AssuntoService assuntoService) {
        this.jornalService = jornalService;
        this.editoraService = editoraService;
        this.colaboradorService = colaboradorService;
        this.assuntoService = assuntoService;
    }

    @GetMapping("/jornalFormulario")
    public String exibirFormularioJonral(Model model){
        Jornal jornal = new Jornal();
        model.addAttribute("jornal", jornal);
        carregarListasFormulario(model, jornal);
        return "jornalFormulario";
    }

    @PostMapping("/salvarJornal")
    public String salvarJornal(@Validated @ModelAttribute("jornal") Jornal jornal, BindingResult result, @RequestParam("fileCapa") MultipartFile fileCapa, @RequestParam(value = "colaboradoresIds", required = false) List<Integer> colaboradoresIds,
                               @RequestParam(value = "assuntosIds", required = false) List<Integer> assuntosIds, Model model) {

        if (result.hasErrors()) {
            carregarListasFormulario(model, jornal);
            return "jornalFormulario";
        }

        try {
            jornalService.salvar(jornal, fileCapa, colaboradoresIds, assuntosIds);
        } catch (IllegalArgumentException e) {
            result.reject("erro", e.getMessage());
            carregarListasFormulario(model, jornal);
            return "jornalFormulario";
        } catch (Exception e) {
            result.reject("erro", "Erro ao processar a imagem.");
            carregarListasFormulario(model, jornal);
            return "jornalFormulario";
        }

        return "redirect:/jornal/jornalLista";
    }

    @GetMapping("/jornalLista")
    public String jornalLista(Model model) {
        List<Jornal> jornais = jornalService.listaJornais();
        model.addAttribute("listaJornais", jornais);
        return "jornalLista";
    }

    @GetMapping("/editar/{idItemAcervo}")
    public String editarJornal(@PathVariable int idItemAcervo, Model model) {
        Jornal jornal = jornalService.EditarId(idItemAcervo).orElseThrow(() -> new RuntimeException("Jornal não encontrado"));

        if (jornal.getLocalizacao() == null) {
            jornal.setLocalizacao(new Localizacao());
        }

        model.addAttribute("jornal", jornal);
        carregarListasFormulario(model, jornal);
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

    private void carregarListasFormulario(Model model, Jornal jornal) {
        model.addAttribute("listaEditoras", editoraService.listarEditorasAtivas());
        model.addAttribute("listaColaboradores", colaboradorService.listarColaboradoresAtivos());
        model.addAttribute("listaAssuntos", assuntoService.listarAssuntosAtivos());

        List<Integer> colaboradoresIds = (jornal.getColaboradores() != null)
                ? jornal.getColaboradores().stream().map(Colaborador::getIdColaborador).collect(Collectors.toList())
                : Collections.emptyList();
        List<Integer> assuntosIds = (jornal.getAssuntos() != null)
                ? jornal.getAssuntos().stream().map(Assunto::getIdAssunto).collect(Collectors.toList())
                : Collections.emptyList();

        model.addAttribute("selectedColaboradoresIds", colaboradoresIds);
        model.addAttribute("selectedAssuntosIds", assuntosIds);
    }
}