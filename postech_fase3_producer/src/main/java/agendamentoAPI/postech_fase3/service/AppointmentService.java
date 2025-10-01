package agendamentoAPI.postech_fase3.service;

import java.util.List;

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

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;



@Service

public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;
    private final ProfessionalRepository professionalRepository;

    public AppointmentService(AppointmentRepository appointmentRepository,
                              UserRepository userRepository,
                              ProfessionalRepository professionalRepository) {
        this.appointmentRepository = appointmentRepository;
        this.userRepository = userRepository;
        this.professionalRepository = professionalRepository;
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
                .build();

        Appointment saved = appointmentRepository.save(appointment);

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

        Appointment updated = appointmentRepository.save(existing);

        return mapToDetailsDTO(updated);
    }

    public AppointmentDetailsDTO mapToDetailsDTO(Appointment appointment) {
       AppointmentDetailsDTO dto = new AppointmentDetailsDTO();
       dto.setUserName(appointment.getUser().getName());
       dto.setUserEmail(appointment.getUser().getEmail());
       dto.setUserPhone(appointment.getUser().getPhone());
       dto.setProfessionalName(appointment.getProfessional().getName());
       dto.setProfessionalSpecialty(appointment.getProfessional().getSpecialty());
       dto.setAppointmentDateTime(appointment.getDateTime());
       return dto;
    }

    public void delete(Long id) {
        appointmentRepository.deleteById(id);
    }
    
    public List<Appointment> listAll() {
        return appointmentRepository.findAll();
    }
    
    public List<Appointment> listPatientAll() {
		AuthenticatedUser userAuth =  (AuthenticatedUser)   SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return appointmentRepository.findAllById(userAuth.getId());
    }
}