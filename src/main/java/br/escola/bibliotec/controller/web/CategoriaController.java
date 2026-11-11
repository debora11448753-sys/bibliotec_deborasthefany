
package br.escola.bibliotec.controller.web;

import br.escola.bibliotec.model.Categoria;
import br.escola.bibliotec.service.CategoriaService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/categorias")
public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    // Lista todas as categorias.
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("categorias", categoriaService.listarTodas());
        return "categorias/lista";
    }

    // Abre o formulário para cadastrar uma categoria.
    @GetMapping("/nova")
    public String nova(Model model) {
        model.addAttribute("categoria", new Categoria());
        return "categorias/form";
    }

    // Salva uma categoria nova ou editada.
    @PostMapping
    public String salvar(
            @Valid @ModelAttribute("categoria") Categoria categoria,
            BindingResult resultado,
            RedirectAttributes redirectAttributes) {

        // TESTE: verifica os dados recebidos pelo formulário.
        System.out.println("NOME RECEBIDO: [" + categoria.getNome() + "]");
        System.out.println("DESCRICAO RECEBIDA: [" + categoria.getDescricao() + "]");

        // Verifica se o nome já está cadastrado.
        if (categoriaService.nomeJaExiste(categoria)) {
            resultado.rejectValue(
                    "nome",
                    "nome.duplicado",
                    "Já existe uma categoria com este nome"
            );
        }

        // Se houver erros, retorna ao formulário.
        if (resultado.hasErrors()) {
            return "categorias/form";
        }

        // Salva a categoria.
        categoriaService.salvar(categoria);

        // Exibe mensagem de sucesso.
        redirectAttributes.addFlashAttribute(
                "mensagem",
                "Categoria salva com sucesso!"
        );

        return "redirect:/categorias";
    }

    // Abre o formulário para editar uma categoria.
    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("categoria", categoriaService.buscarPorId(id));
        return "categorias/form";
    }

    // Exclui uma categoria.
    @PostMapping("/{id}/excluir")
    public String excluir(
            @PathVariable Long id,
            RedirectAttributes redirectAttributes) {

        categoriaService.excluir(id);

        redirectAttributes.addFlashAttribute(
                "mensagem",
                "Categoria excluída com sucesso!"
        );

        return "redirect:/categorias";
    }
}
