package agendamentoAPI.postech_fase3.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

import agendamentoAPI.postech_fase3.model.Role;
import agendamentoAPI.postech_fase3.model.User;

@Service
public class JWTService {

    private final String jwtSecret;

    public JWTService(@Value("${security.jwt.secret}") String jwtSecret) {
        this.jwtSecret = jwtSecret;
    }
    
    public String createToken(User user) {
    	
    	List<String> rolesList = user.getRoles().stream()
                .map(Role::getNome)
                .toList();
    	
        return JWT.create()
                .withSubject(user.getEmail())
                .withClaim("roles", rolesList)
                .withClaim("id", user.getId())
                .withClaim("email", user.getEmail())
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + 100 * 60 * 60)) // 100h
                .sign(Algorithm.HMAC256(jwtSecret));
    }

    public DecodedJWT parseToken(String token) {
        try {
            Algorithm alg = Algorithm.HMAC256(jwtSecret);
            JWTVerifier verifier = JWT.require(alg).build();
            return verifier.verify(token);
        } catch (JWTVerificationException e) {
            // Token inválido
            return null;
        }
    }
    
    public  String getEmail(String token) {
        DecodedJWT decodedJWT =  parseToken(token);
        return decodedJWT != null ? decodedJWT.getSubject() : null;
    }
}
