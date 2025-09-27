package agendamentoAPI.postech_fase3.repository;

import agendamentoAPI.postech_fase3.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByName(String name);
}