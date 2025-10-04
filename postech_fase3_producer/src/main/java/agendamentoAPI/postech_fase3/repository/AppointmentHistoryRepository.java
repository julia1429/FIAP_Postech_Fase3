package agendamentoAPI.postech_fase3.repository;

import agendamentoAPI.postech_fase3.model.AppointmentHistory;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AppointmentHistoryRepository extends JpaRepository<AppointmentHistory, Long> {

}