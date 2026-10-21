
package br.escola.bibliotec.controller.web;

import br.escola.bibliotec.model.Categoria;
import br.escola.bibliotec.service.CategoriaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/categorias")
public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("categorias",
                categoriaService.listarTodas());
        return "categorias/lista";
    }

    @GetMapping("/nova")
    public String nova(Model model) {
        model.addAttribute("categoria", new Categoria());
        return "categorias/form";
    }

    @PostMapping
    public String salvar(@ModelAttribute Categoria categoria) {
        categoriaService.salvar(categoria);
        return "redirect:/categorias";
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("categoria",
                categoriaService.buscarPorId(id));
        return "categorias/form";
    }

    @PostMapping("/{id}/excluir")
    public String excluir(@PathVariable Long id) {
        categoriaService.excluir(id);
        return "redirect:/categorias";
    }
}
