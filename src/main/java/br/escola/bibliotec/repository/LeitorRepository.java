
package br.escola.bibliotec.repository;

import br.escola.bibliotec.model.Leitor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LeitorRepository extends JpaRepository<Leitor, Long> {

    List<Leitor> findAllByOrderByNomeAsc();

    Optional<Leitor> findByMatricula(String matricula);

    List<Leitor> findByNomeContainingIgnoreCaseOrderByNomeAsc(String nome);
}
