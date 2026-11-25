
package br.escola.bibliotec.controller.web;

import br.escola.bibliotec.model.Leitor;
import br.escola.bibliotec.model.TipoLeitor;
import br.escola.bibliotec.service.LeitorService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/leitores")
public class LeitorController {

    private final LeitorService leitorService;

    public LeitorController(LeitorService leitorService) {
        this.leitorService = leitorService;
    }

    @GetMapping
    public String listar(@RequestParam(required = false) String nome,
                         Model model) {

        if (nome != null && !nome.isBlank()) {
            model.addAttribute("leitores",
                    leitorService.pesquisarPorNome(nome));
            model.addAttribute("nomePesquisa", nome);
        } else {
            model.addAttribute("leitores",
                    leitorService.listarTodos());
            model.addAttribute("nomePesquisa", "");
        }

        return "leitores/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("leitor", new Leitor());
        model.addAttribute("tipos", TipoLeitor.values());

        return "leitores/form";
    }

    @PostMapping
    public String salvar(@Valid @ModelAttribute("leitor") Leitor leitor,
                         BindingResult resultado,
                         Model model,
                         RedirectAttributes redirectAttributes) {

        if (leitorService.matriculaJaExiste(leitor)) {
            resultado.rejectValue(
                    "matricula",
                    "matricula.duplicada",
                    "Esta matrícula já está cadastrada."
            );
        }

        if (resultado.hasErrors()) {
            model.addAttribute("tipos", TipoLeitor.values());
            return "leitores/form";
        }

        leitorService.salvar(leitor);

        redirectAttributes.addFlashAttribute(
                "mensagem",
                "Leitor salvo com sucesso!"
        );

        return "redirect:/leitores";
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model,
                         RedirectAttributes redirectAttributes) {

        return leitorService.buscarPorId(id)
                .map(leitor -> {
                    model.addAttribute("leitor", leitor);
                    model.addAttribute("tipos", TipoLeitor.values());
                    return "leitores/form";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute(
                            "erro",
                            "Leitor não encontrado."
                    );
                    return "redirect:/leitores";
                });
    }

    @PostMapping("/{id}/excluir")
    public String excluir(@PathVariable Long id,
                          RedirectAttributes redirectAttributes) {

        leitorService.excluir(id);

        redirectAttributes.addFlashAttribute(
                "mensagem",
                "Leitor excluído com sucesso!"
        );

        return "redirect:/leitores";
    }
}
