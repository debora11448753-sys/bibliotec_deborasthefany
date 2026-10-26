
package br.escola.bibliotec.service;

import br.escola.bibliotec.model.Categoria;
import br.escola.bibliotec.repository.CategoriaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    public List<Categoria> listarTodas() {
        return categoriaRepository.findAll();
    }

    public boolean nomeJaExiste(Categoria categoria) {
        String nome = categoria.getNome();

        if (nome == null || nome.isBlank()) {
            return false;
        }

        return categoriaRepository.findByNomeIgnoreCase(nome.trim())
                .filter(existente ->
                        categoria.getId() == null
                                || !existente.getId().equals(categoria.getId()))
                .isPresent();
    }

    public Categoria salvar(Categoria categoria) {
        if (categoria.getNome() != null) {
            categoria.setNome(categoria.getNome().trim());
        }

        return categoriaRepository.save(categoria);
    }

    public Categoria buscarPorId(Long id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Categoria não encontrada"));
    }

    public void excluir(Long id) {
        categoriaRepository.deleteById(id);
    }
}
