package agendamentoAPI.postech_fase3.DTO;

import lombok.Data;

import java.util.Date;

@Data
public class AppointmentDTO {
    private Long pacienteId;
    private Long profissionalId;
    private Date dataHora;

    private String email;
    private String telefone;

}
