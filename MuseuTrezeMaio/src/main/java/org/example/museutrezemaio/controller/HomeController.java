package org.example.museutrezemaio.controller;

import org.example.museutrezemaio.model.ItemAcervo;
import org.example.museutrezemaio.service.AssuntoService;
import org.example.museutrezemaio.service.EditoraService;
import org.example.museutrezemaio.service.ItemAcervoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class HomeController {

    private final ItemAcervoService itemAcervoService;
    private final AssuntoService assuntoService;
    private final EditoraService editoraService;

    public HomeController(ItemAcervoService itemAcervoService,
                          AssuntoService assuntoService,
                          EditoraService editoraService) {
        this.itemAcervoService = itemAcervoService;
        this.assuntoService = assuntoService;
        this.editoraService = editoraService;
    }

    @GetMapping("/")
    public String home(Model model) {
        List<ItemAcervo> todos = itemAcervoService.listarTodosNaoInativos();

        List<ItemAcervo> destaques = todos.stream()
                .filter(i -> "Livro".equals(i.getTipo()))
                .limit(8)
                .collect(Collectors.toList());

        long totalLivros   = todos.stream().filter(i -> "Livro".equals(i.getTipo())).count();
        long totalRevistas = todos.stream().filter(i -> "Revista".equals(i.getTipo())).count();
        long totalJornais  = todos.stream().filter(i -> "Jornal".equals(i.getTipo())).count();

        model.addAttribute("destaques", destaques);
        model.addAttribute("totalLivros", totalLivros);
        model.addAttribute("totalRevistas", totalRevistas);
        model.addAttribute("totalJornais", totalJornais);
        model.addAttribute("totalItens", todos.size());
        return "home";
    }

    @GetMapping("/pesquisa")
    public String pesquisa(
            @RequestParam(required = false) String q,
            @RequestParam(value = "tipo", required = false) List<String> tipos,
            @RequestParam(required = false) Integer assuntoId,
            @RequestParam(required = false) String idioma,
            @RequestParam(required = false) Integer editoraId,
            Model model) {

        List<ItemAcervo> resultados = itemAcervoService.buscar(q, tipos, assuntoId, idioma, editoraId);

        model.addAttribute("resultados", resultados);
        model.addAttribute("q", q != null ? q : "");
        model.addAttribute("tiposSelecionados", tipos != null ? tipos : List.of());
        model.addAttribute("assuntoIdSelecionado", assuntoId);
        model.addAttribute("idiomaSelecionado", idioma != null ? idioma : "");
        model.addAttribute("editoraIdSelecionada", editoraId);
        model.addAttribute("listaAssuntosAtivos", assuntoService.listarAssuntosAtivos());
        model.addAttribute("listaEditorasAtivas", editoraService.listarEditorasAtivas());
        return "pesquisa";
    }

    @GetMapping("/admin")
    public String admin(Model model) {
        List<ItemAcervo> todos = itemAcervoService.listarTodosNaoInativos();
        model.addAttribute("totalLivros",   todos.stream().filter(i -> "Livro".equals(i.getTipo())).count());
        model.addAttribute("totalRevistas", todos.stream().filter(i -> "Revista".equals(i.getTipo())).count());
        model.addAttribute("totalJornais",  todos.stream().filter(i -> "Jornal".equals(i.getTipo())).count());
        model.addAttribute("totalItens", todos.size());
        return "admin";
    }

    @GetMapping("/index")
    public String index() {
        return "redirect:/";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/sobre")
    public String sobre() {
        return "sobre";
    }
}
