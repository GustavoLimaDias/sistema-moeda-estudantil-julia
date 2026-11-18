package br.com.sistemamoedaestudantil.sistema_moeda_estudantil.repository;

import br.com.sistemamoedaestudantil.sistema_moeda_estudantil.model.Professor;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProfessorRepository extends JpaRepository<Professor, UUID> {

    @Override
    @EntityGraph(attributePaths = "instituicao")
    Optional<Professor> findById(UUID id);

    @Query(value = "select usuario_id from professor where usuario_id = :id for update", nativeQuery = true)
    List<String> bloquearLinha(@Param("id") String id);

    default Optional<Professor> buscarParaAtualizar(UUID id) {
        if (bloquearLinha(id.toString()).isEmpty()) {
            return Optional.empty();
        }
        return findById(id);
    }
}
