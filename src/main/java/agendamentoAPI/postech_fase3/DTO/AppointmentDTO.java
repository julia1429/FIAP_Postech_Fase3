package agendamentoAPI.postech_fase3.DTO;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AppointmentDTO {
    private Long pacienteId;
    private Long profissionalId;
    private LocalDateTime dataHora;
}
