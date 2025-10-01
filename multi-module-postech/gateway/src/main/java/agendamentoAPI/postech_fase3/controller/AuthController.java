package agendamentoAPI.postech_fase3.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import agendamentoAPI.postech_fase3.DTO.UserAuthorizationDTO;
import agendamentoAPI.postech_fase3.exceptions.ResourceNotFoundException;
import agendamentoAPI.postech_fase3.model.User;
import agendamentoAPI.postech_fase3.service.AuthService;
import agendamentoAPI.postech_fase3.service.JWTService;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/auth", produces = { "application/json" })
public class AuthController {

	@Autowired
	private AuthService authService;

	@Autowired
	private JWTService jwtService;
	
	@PostMapping("/login")
	public ResponseEntity<?> authorization(@Valid @RequestBody UserAuthorizationDTO userAuth) {
		User user = null;

		try {
			user = authService.getUserByEmail(userAuth.identificador());
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(401).body("Credenciais inválidas, não autorizado.");
		}

		if (user != null && authService.verifyPassword(userAuth.password(), user.getSenha())) {
	
			String token = jwtService.createToken(user);
			return ResponseEntity.ok(token);
		}
		
		return ResponseEntity.status(401).body("Credenciais inválidas, não autorizado");
	}
}