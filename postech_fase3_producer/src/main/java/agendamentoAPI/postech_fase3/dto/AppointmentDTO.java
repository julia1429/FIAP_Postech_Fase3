package agendamentoAPI.postech_fase3.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Data
public class AppointmentDTO {
    private Long userId;
    private Long professionalId;
    private Date appointmentDateTime;
    private String notes;

}
