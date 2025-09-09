package agendamentoAPI.postech_fase3.service;

import agendamentoAPI.postech_fase3.model.User;
import agendamentoAPI.postech_fase3.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User create(User user) {
        // Aqui você pode validar e-mails duplicados, formatos de telefone, etc.
        return userRepository.save(user);
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