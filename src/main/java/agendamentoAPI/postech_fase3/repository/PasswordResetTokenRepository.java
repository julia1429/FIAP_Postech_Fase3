package agendamentoAPI.postech_fase3.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import agendamentoAPI.postech_fase3.model.PasswordResetToken;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long>{
	  Optional<PasswordResetToken> findByToken(String token);

}
