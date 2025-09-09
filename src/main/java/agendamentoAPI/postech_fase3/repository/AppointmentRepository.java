package agendamentoAPI.postech_fase3.repository;

import agendamentoAPI.postech_fase3.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
}