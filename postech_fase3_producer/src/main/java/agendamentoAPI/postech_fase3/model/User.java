package agendamentoAPI.postech_fase3.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String phone;
    private String email;

    @Enumerated(EnumType.STRING)
    private Role role; // MÉDICO, ENFERMEIRO, PACIENTE

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Professional professional;
}
