package agendamentoAPI.postech_fase3.repository;

import agendamentoAPI.postech_fase3.model.AppointmentHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface AppointmentHistoryRepository extends JpaRepository<AppointmentHistory, Long> {

    Optional<AppointmentHistory> findByAppointmentId(Long appointmentId);

}