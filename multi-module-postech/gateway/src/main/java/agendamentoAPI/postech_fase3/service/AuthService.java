package agendamentoAPI.postech_fase3.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import agendamentoAPI.postech_fase3.exceptions.ResourceNotFoundException;
import agendamentoAPI.postech_fase3.model.User;
import agendamentoAPI.postech_fase3.repository.UserRepository;

@Service
public class AuthService  {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public boolean verifyPassword(String password, String passwordDatabase) {
		return passwordEncoder.matches(password, passwordDatabase);
	}

	public User getUserByEmail(String email) {
		return userRepository.findByEmail(email)
				.orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com o e-mail: " + email));
	}
}