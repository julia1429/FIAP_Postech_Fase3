package agendamentoAPI.postech_fase3.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import agendamentoAPI.postech_fase3.model.Appointment;


public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
	
    List<Appointment> findAllByPacienteId(Long patientId);
}