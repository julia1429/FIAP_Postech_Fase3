package agendamentoAPI.postech_fase3.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "appointments")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date dataHora;

    @ManyToOne
    @JoinColumn(name = "paciente_id")
    private User paciente;

    @ManyToOne
    @JoinColumn(name = "profissional_id")
    private User profissional; // médico ou enfermeiro
}
