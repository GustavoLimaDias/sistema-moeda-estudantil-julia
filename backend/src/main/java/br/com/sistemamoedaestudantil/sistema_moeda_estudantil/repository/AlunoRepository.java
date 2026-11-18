package br.com.sistemamoedaestudantil.sistema_moeda_estudantil.repository;

import br.com.sistemamoedaestudantil.sistema_moeda_estudantil.model.Aluno;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AlunoRepository extends JpaRepository<Aluno, UUID> {
    boolean existsByEmail(String email);
    boolean existsByCpf(String cpf);
    boolean existsByEmailAndIdNot(String email, UUID id);
    boolean existsByCpfAndIdNot(String cpf, UUID id);

    @Override
    @EntityGraph(attributePaths = {"curso", "curso.instituicao"})
    List<Aluno> findAll();

    @Override
    @EntityGraph(attributePaths = {"curso", "curso.instituicao"})
    Optional<Aluno> findById(UUID id);

    @Query(value = "select usuario_id from aluno where usuario_id = :id for update", nativeQuery = true)
    List<String> bloquearLinha(@Param("id") String id);

    default Optional<Aluno> buscarParaAtualizar(UUID id) {
        if (bloquearLinha(id.toString()).isEmpty()) {
            return Optional.empty();
        }
        return findById(id);
    }
}
