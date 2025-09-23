package agendamentoAPI.postech_fase3.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
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
	private List<String> roles = new ArrayList<>();
	
	public AuthenticatedUser( String email, Long id,
			List<String> roles ) {
		super();
		this.email = email;
		this.id = id;
		this.roles = roles;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return roles.stream().map(role -> new SimpleGrantedAuthority("ROLE_"+role.toString()))
		.collect(Collectors.toList());
	}

	@Override
	public String getUsername() {
		return nome;
	}
}