package org.example.museutrezemaio.controller;

import org.example.museutrezemaio.model.Livro;
import org.example.museutrezemaio.service.AssuntoService;
import org.example.museutrezemaio.service.ColaboradorService;
import org.example.museutrezemaio.service.EditoraService;
import org.example.museutrezemaio.service.LivroService;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/livro")
public class LivroController {

    private final LivroService livroService;
    private final EditoraService editoraService;
    private final ColaboradorService colaboradorService;
    private final AssuntoService assuntoService;

    public LivroController(LivroService livroService, EditoraService editoraService, ColaboradorService colaboradorService, AssuntoService assuntoService) {
        this.livroService = livroService;
        this.editoraService = editoraService;
        this.colaboradorService = colaboradorService;
        this.assuntoService = assuntoService;
    }

    @GetMapping("/livroFormulario")
    public String exibirFormulario(Model model) {
        model.addAttribute("livro", new Livro());
        adicionarAtributosFormulario(model, new HashSet<>(), new HashSet<>());
        return "livroFormulario";
    }

    @PostMapping("/salvarLivro")
    public String salvarLivro(
            @Validated @ModelAttribute("livro") Livro livro,
            BindingResult result,
            @RequestParam("fileCapa") MultipartFile fileCapa,
            @RequestParam(value = "colaboradoresIds", required = false) List<Integer> colaboradoresIds,
            @RequestParam(value = "assuntosIds", required = false) List<Integer> assuntosIds,
            Model model,
            RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            adicionarAtributosFormulario(model, toSet(colaboradoresIds), toSet(assuntosIds));
            return "livroFormulario";
        }

        try {
            livroService.salvar(livro, fileCapa, colaboradoresIds, assuntosIds);
        } catch (IllegalArgumentException e) {
            result.reject("erro", e.getMessage());
            adicionarAtributosFormulario(model, toSet(colaboradoresIds), toSet(assuntosIds));
            return "livroFormulario";
        } catch (Exception e) {
            result.reject("erro", "Erro ao processar a imagem da capa.");
            adicionarAtributosFormulario(model, toSet(colaboradoresIds), toSet(assuntosIds));
            return "livroFormulario";
        }

        String mensagem = livro.getIdItemAcervo() > 0
                ? "Livro atualizado com sucesso!"
                : "Livro cadastrado com sucesso!";
        redirectAttributes.addFlashAttribute("mensagemSucesso", mensagem);
        return "redirect:/livro/livroLista";
    }

    @GetMapping("/livroLista")
    public String livroLista(Model model) {
        model.addAttribute("listaLivros", livroService.listarLivros());
        return "livroLista";
    }

    @GetMapping("/editar/{idItemAcervo}")
    public String editarLivro(@PathVariable int idItemAcervo, Model model) {
        Livro livro = livroService.editarId(idItemAcervo).orElseThrow(() -> new RuntimeException("Livro não encontrado"));

        Set<Integer> selectedColaboradoresIds = livro.getColaboradores().stream().map(c -> c.getIdColaborador()).collect(Collectors.toSet());
        Set<Integer> selectedAssuntosIds = livro.getAssuntos().stream().map(a -> a.getIdAssunto()).collect(Collectors.toSet());

        model.addAttribute("livro", livro);
        adicionarAtributosFormulario(model, selectedColaboradoresIds, selectedAssuntosIds);
        return "livroFormulario";
    }

    @GetMapping("/detalhes/{id}")
    public String detalhesLivro(@PathVariable int id, Model model) {
        Livro livro = livroService.editarId(id).orElseThrow(() -> new RuntimeException("Livro não encontrado"));
        model.addAttribute("livro", livro);
        return "livroDetalhes";
    }

    @PostMapping("/excluir/{id}")
    public String excluirLivro(@PathVariable int id, RedirectAttributes redirectAttributes) {
        livroService.excluir(id);
        redirectAttributes.addFlashAttribute("mensagemSucesso", "Livro excluído com sucesso!");
        return "redirect:/livro/livroLista";
    }

    @GetMapping("/capa/{id}")
    public ResponseEntity<byte[]> getCapa(@PathVariable int id) {
        Livro livro = livroService.editarId(id).orElseThrow(() -> new RuntimeException("Livro não encontrado"));

        byte[] capa = livro.getCapa();
        if (capa == null || capa.length == 0) {
            return ResponseEntity.notFound().build();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        return new ResponseEntity<>(capa, headers, HttpStatus.OK);
    }

    private void adicionarAtributosFormulario(Model model, Set<Integer> selectedColaboradoresIds, Set<Integer> selectedAssuntosIds) {
        model.addAttribute("listaEditoras", editoraService.listaEditoras());
        model.addAttribute("listaColaboradores", colaboradorService.listarColaboradores());
        model.addAttribute("listaAssuntos", assuntoService.listaAssuntos());
        model.addAttribute("selectedColaboradoresIds", selectedColaboradoresIds);
        model.addAttribute("selectedAssuntosIds", selectedAssuntosIds);
    }

    private Set<Integer> toSet(List<Integer> ids) {
        return ids != null ? new HashSet<>(ids) : new HashSet<>();
    }
}
