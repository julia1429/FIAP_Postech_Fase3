package agendamentoAPI.postech_fase3.repository;

import agendamentoAPI.postech_fase3.model.Professional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfessionalRepository  extends JpaRepository<Professional, Long> {
}
