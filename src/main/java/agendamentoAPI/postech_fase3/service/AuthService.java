package agendamentoAPI.postech_fase3.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import agendamentoAPI.postech_fase3.DTO.UserAuthorizationDTO;
import agendamentoAPI.postech_fase3.exceptions.ResourceNotFoundException;
import agendamentoAPI.postech_fase3.model.PasswordResetToken;
import agendamentoAPI.postech_fase3.model.User;
import agendamentoAPI.postech_fase3.repository.PasswordResetTokenRepository;
import agendamentoAPI.postech_fase3.repository.UserRepository;

@Service
public class AuthService  {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private EmailService emailService;

	@Autowired
	private PasswordResetTokenRepository tokenRepository;

	

	public boolean verifyPassword(String password, String passwordDatabase) {
		return passwordEncoder.matches(password, passwordDatabase);
	}

	public User getUserByEmail(String email) {
		return userRepository.findByEmail(email)
				.orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com o e-mail: " + email));
	}

	public void requestPasswordReset(String email) {
		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

		String token = UUID.randomUUID().toString();

		PasswordResetToken resetToken = new PasswordResetToken();
		resetToken.setToken(token);
		resetToken.setUser(user);
		resetToken.setExpiration(LocalDateTime.now().plusHours(1));

		tokenRepository.save(resetToken);
		emailService.sendResetToken(user.getEmail(), token);

	}
	
	  public void resetPassword(String token, UserAuthorizationDTO userAuth) {
	        PasswordResetToken resetToken = tokenRepository.findByToken(token)
	            .orElseThrow(() -> new IllegalArgumentException("Token inválido"));

	        if (resetToken.isExpired()) {
	            throw new IllegalArgumentException("Token expirado");
	        }

	        User user = resetToken.getUser();
	        String passwordCrypto = this.passwordEncoder.encode(userAuth.password());
	        user.setSenha(passwordCrypto);
	        userRepository.save(user);

	        tokenRepository.delete(resetToken); 
	    }
}