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

    private String nome;
    private String cpf;
    private String telefone;
    private String email;

    @Enumerated(EnumType.STRING)
    private Role role; // MÉDICO, ENFERMEIRO, PACIENTE
}
