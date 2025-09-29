package agendamentoAPI.postech_fase3.service;

import agendamentoAPI.postech_fase3.dto.UserDTO;
import agendamentoAPI.postech_fase3.model.Professional;
import agendamentoAPI.postech_fase3.model.Role;
import agendamentoAPI.postech_fase3.model.User;
import agendamentoAPI.postech_fase3.repository.ProfessionalRepository;
import agendamentoAPI.postech_fase3.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ProfessionalRepository professionalRepository;

    public User create(UserDTO dto) {

        validateUser(dto);

        User user = User.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .role(dto.getRole())
                .build();

        User savedUser = userRepository.save(user);

        if (dto.getRole() == Role.MEDICO || dto.getRole() == Role.ENFERMEIRO) {
            Professional professional = Professional.builder()
                    .specialty(dto.getSpecialty())
                    .user(savedUser)
                    .build();

            professionalRepository.save(professional);
        }

        return savedUser;
    }

    private void validateUser(UserDTO dto) {
        switch (dto.getRole()) {
            case MEDICO, ENFERMEIRO -> {
                if (dto.getSpecialty() == null || dto.getSpecialty().isBlank()) {
                    throw new IllegalArgumentException("Especialidade é obrigatória para médicos/enfermeiros.");
                }
            }
            case PACIENTE -> {
                if (dto.getName() == null || dto.getName().isBlank()) {
                    throw new IllegalArgumentException("Nome é obrigatório para pacientes.");
                }
                if (dto.getEmail() == null || dto.getEmail().isBlank()) {
                    throw new IllegalArgumentException("Email é obrigatório para pacientes.");
                }
                if (dto.getPhone() == null || dto.getPhone().isBlank()) {
                    throw new IllegalArgumentException("Telefone é obrigatório para pacientes.");
                }
            }
        }
    }

    public List<User> list() {
        return userRepository.findAll();
    }

    public User getById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}
