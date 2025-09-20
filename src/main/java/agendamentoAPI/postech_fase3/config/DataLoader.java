package agendamentoAPI.postech_fase3.config;

import java.util.HashSet;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import agendamentoAPI.postech_fase3.Enum.EnumUserType;
import agendamentoAPI.postech_fase3.model.Role;
import agendamentoAPI.postech_fase3.model.User;
import agendamentoAPI.postech_fase3.repository.RoleRepository;
import agendamentoAPI.postech_fase3.repository.UserRepository;
import jakarta.transaction.Transactional;

@Component
public class DataLoader implements CommandLineRunner {

	UserRepository userRepository;
	PasswordEncoder passwordEncoder;
	RoleRepository roleRepository;

	public DataLoader(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.roleRepository = roleRepository;
	}

	@Override
	@Transactional
	public void run(String... args) throws Exception {

		Role roleMedico = roleRepository.findByNome(EnumUserType.MEDICO.toString())
				.orElseGet(() -> roleRepository.save(new Role(null, EnumUserType.MEDICO.toString())));

		Role roleEnfermeiro = roleRepository.findByNome(EnumUserType.ENFERMEIRO.toString())
				.orElseGet(() -> roleRepository.save(new Role(null, EnumUserType.ENFERMEIRO.toString())));

		Role rolePaciente = roleRepository.findByNome(EnumUserType.PACIENTE.toString())
				.orElseGet(() -> roleRepository.save(new Role(null, EnumUserType.PACIENTE.toString())));

		Set<Role> userTypesRoles = new HashSet<>();
		User medico = new User();
		medico.setNome("medico");
		medico.setEmail("medico.com.br");
		medico.setSenha(passwordEncoder.encode("123"));
		userTypesRoles.add(roleMedico);
		medico.setRoles(userTypesRoles);
		var save = userRepository.save(medico);
		
		
		userTypesRoles = new HashSet<>();
		
		User enfermeiro = new User();
		enfermeiro.setNome("enfermeiro");
		enfermeiro.setEmail("enfermeiro.com.br");
		enfermeiro.setSenha(passwordEncoder.encode("123"));
		userTypesRoles.add(roleEnfermeiro);
		enfermeiro.setRoles(userTypesRoles);
	    save = userRepository.save(enfermeiro);
		
	    userTypesRoles = new HashSet<>();
	    
		User paciente = new User();
		paciente.setNome("paciente");
		paciente.setEmail("paciente.com.br");
		paciente.setSenha(passwordEncoder.encode("123"));
		userTypesRoles.add(rolePaciente);
		paciente.setRoles(userTypesRoles);
		save = userRepository.save(paciente);
		System.out.println("user created successfully!");
	}

}