package agendamentoAPI.postech_fase3.service;

import java.time.LocalDateTime;
import java.util.List;

import agendamentoAPI.postech_fase3.model.AppointmentHistory;
import agendamentoAPI.postech_fase3.repository.AppointmentHistoryRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import agendamentoAPI.postech_fase3.dto.AppointmentDTO;
import agendamentoAPI.postech_fase3.dto.AppointmentDetailsDTO;
import agendamentoAPI.postech_fase3.model.Appointment;
import agendamentoAPI.postech_fase3.model.AuthenticatedUser;
import agendamentoAPI.postech_fase3.model.Professional;
import agendamentoAPI.postech_fase3.model.User;
import agendamentoAPI.postech_fase3.repository.AppointmentRepository;
import agendamentoAPI.postech_fase3.repository.ProfessionalRepository;
import agendamentoAPI.postech_fase3.repository.UserRepository;


@Service

public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;
    private final ProfessionalRepository professionalRepository;

    private final AppointmentHistoryRepository appointmentHistoryRepository;

    public AppointmentService(AppointmentRepository appointmentRepository,
                              UserRepository userRepository,
                              ProfessionalRepository professionalRepository,
                              AppointmentHistoryRepository appointmentHistoryRepository) {
        this.appointmentRepository = appointmentRepository;
        this.userRepository = userRepository;
        this.professionalRepository = professionalRepository;
        this.appointmentHistoryRepository = appointmentHistoryRepository;
    }

    public AppointmentDetailsDTO create(AppointmentDTO dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("Paciente não encontrado"));

        Professional professional = professionalRepository.findById(dto.getProfessionalId())
                .orElseThrow(() -> new RuntimeException("Profissional não encontrado"));

        Appointment appointment = Appointment.builder()
                .dateTime(dto.getAppointmentDateTime())
                .user(user)
                .professional(professional)
                .notes(dto.getNotes())
                .build();

        Appointment saved = appointmentRepository.save(appointment);

        saveHistory(saved, "CREATED");

        return mapToDetailsDTO(saved);
    }
    public AppointmentDetailsDTO update(Long id, AppointmentDTO dto) {
        Appointment existing = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Consulta não encontrada"));

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("Paciente não encontrado"));

        Professional professional = professionalRepository.findById(dto.getProfessionalId())
                .orElseThrow(() -> new RuntimeException("Profissional não encontrado"));

        existing.setDateTime(dto.getAppointmentDateTime());
        existing.setUser(user);
        existing.setProfessional(professional);
        existing.setCreatedAt(existing.getCreatedAt());
        existing.setNotes(dto.getNotes());

        Appointment updated = appointmentRepository.save(existing);

        saveHistory(updated, "UPDATED");

        return mapToDetailsDTO(updated);
    }

    public AppointmentDetailsDTO mapToDetailsDTO(Appointment appointment) {
       AppointmentDetailsDTO dto = new AppointmentDetailsDTO();
       dto.setUserName(appointment.getUser().getNome());
       dto.setUserEmail(appointment.getUser().getEmail());
       dto.setUserPhone(appointment.getUser().getTelefone());
       dto.setProfessionalName(appointment.getProfessional().getUser().getNome());
       dto.setProfessionalSpecialty(appointment.getProfessional().getSpecialty());
       dto.setAppointmentDateTime(appointment.getDateTime());
       return dto;
    }

    public void delete(Long id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Consulta não encontrada"));

        saveHistory(appointment, "DELETED");

        appointmentRepository.deleteById(id);
    }

    public List<Appointment> listAll() {
        return appointmentRepository.findAll();
    }

    public List<Appointment> listPatientAll() {
		AuthenticatedUser userAuth =  (AuthenticatedUser)   SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return appointmentRepository.findAllById(userAuth.getId());
    }

    private void saveHistory(Appointment appointment, String status) {
        AppointmentHistory history = AppointmentHistory.builder()
                .appointmentId(appointment.getId())
                .patientId(appointment.getUser().getId())
                .doctorId(appointment.getProfessional().getId())
                .createdAt(appointment.getCreatedAt())
                .updatedAt("UPDATED".equals(status) ? LocalDateTime.now() : null)
                .status(status)
                .notes(appointment.getNotes())
                .build();

        appointmentHistoryRepository.save(history);
    }
}