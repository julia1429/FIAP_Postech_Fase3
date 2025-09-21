package agendamentoAPI.postech_fase3.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import agendamentoAPI.postech_fase3.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
 	Optional<Role> findByNome(String name);
}
