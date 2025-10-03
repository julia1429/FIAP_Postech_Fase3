package agendamentoAPI.postech_fase3.config;

import agendamentoAPI.postech_fase3.model.Professional;
import agendamentoAPI.postech_fase3.model.Role;
import agendamentoAPI.postech_fase3.model.User;
import agendamentoAPI.postech_fase3.repository.ProfessionalRepository;
import agendamentoAPI.postech_fase3.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Component
public class DataLoader implements CommandLineRunner {

	UserRepository userRepository;

	ProfessionalRepository professionalRepository;


	public DataLoader(UserRepository userRepository, ProfessionalRepository professionalRepository) {
		this.userRepository = userRepository;
		this.professionalRepository = professionalRepository;
	}

	@Override
	@Transactional
	public void run(String... args) throws Exception {

		Set<Role> userTypesRoles = new HashSet<>();
		Optional<User> user = userRepository.findById(2L);

		Professional professional = new Professional();
		professional.setUser(user.get());
		professional.setSpecialty("Medico");

		Optional.of(professionalRepository.findById(1L)
				.orElseGet(() -> professionalRepository.save(professional)));

	}
}
