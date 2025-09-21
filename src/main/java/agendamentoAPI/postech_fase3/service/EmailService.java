package agendamentoAPI.postech_fase3.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService  {

	   @Autowired
	    private JavaMailSender mailSender;

	    public void sendResetToken(String toEmail, String token) {
	        String subject = "Recuperação de Senha";
	        String resetUrl = "http://localhost:8081/auth/reset-password?token=" + token;
	        String message = "Clique no link para redefinir sua senha: " + resetUrl;
	        
	        SimpleMailMessage email = new SimpleMailMessage();
	        email.setTo(toEmail);
	        email.setSubject(subject);
	        email.setText(message);

	        mailSender.send(email);
	    }
}