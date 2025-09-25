package agendamentoAPI.postech_fase3.service;

import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import agendamentoAPI.postech_fase3.dto.AppointmentDTO;
import agendamentoAPI.postech_fase3.model.Appointment;
import agendamentoAPI.postech_fase3.model.AuthenticatedUser;
import agendamentoAPI.postech_fase3.model.User;
import agendamentoAPI.postech_fase3.repository.AppointmentRepository;
import agendamentoAPI.postech_fase3.repository.UserRepository;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor

public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;

    public Appointment create(AppointmentDTO dto) {
        User paciente = userRepository.findById(dto.getPacienteId())
                .orElseThrow(() -> new RuntimeException("Paciente não encontrado"));

        User profissional = userRepository.findById(dto.getProfissionalId())
                .orElseThrow(() -> new RuntimeException("Profissional não encontrado"));

        Appointment appointment = Appointment.builder()
                .dataHora(dto.getDataHora())
                .paciente(paciente)
                .profissional(profissional)
                .build();

        return appointmentRepository.save(appointment);
    }

    public Appointment update(Long id, AppointmentDTO dto) {
        Appointment existing = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Consulta não encontrada"));

        User paciente = userRepository.findById(dto.getPacienteId())
                .orElseThrow(() -> new RuntimeException("Paciente não encontrado"));

        User profissional = userRepository.findById(dto.getProfissionalId())
                .orElseThrow(() -> new RuntimeException("Profissional não encontrado"));

        existing.setDataHora(dto.getDataHora());
        existing.setPaciente(paciente);
        existing.setProfissional(profissional);

        return appointmentRepository.save(existing);
    }

    public void delete(Long id) {
        appointmentRepository.deleteById(id);
    }
    
    public List<Appointment> listAll() {
        return appointmentRepository.findAll();
    }
    
    public List<Appointment> listPatientAll() {
		AuthenticatedUser userAuth =  (AuthenticatedUser)   SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return appointmentRepository.findAllByPatientId(userAuth.getId());
    }
}