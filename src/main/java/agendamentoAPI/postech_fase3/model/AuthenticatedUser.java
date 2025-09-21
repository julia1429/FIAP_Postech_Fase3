package agendamentoAPI.postech_fase3.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthenticatedUser implements UserDetails {

	private static final long serialVersionUID = 1L;
	
	private String nome;
	private String password;
	private String email;
	private Long id;
	private Set<Role> userTypesRoles = new HashSet<>(); 
	
	public AuthenticatedUser( String nome, String email, Long id,
			Set<Role> userTypesRoles) {
		super();
		this.nome = nome;
		this.email = email;
		this.id = id;
		this.userTypesRoles = userTypesRoles;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return getUserTypesRoles().stream().map(role -> new SimpleGrantedAuthority("ROLE_"+role.getNome()))
		.collect(Collectors.toList());
	}

	public boolean hasRoleAdmin() {
		return getAuthorities().stream().anyMatch(auth -> "ROLE_ADMIN".equals(auth.getAuthority()));
	}

	@Override
	public String getUsername() {
		return nome;
	}
}