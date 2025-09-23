package agendamentoAPI.postech_fase3.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class AppointmentDetailsDTO {
    private String userName;
    private String userEmail;
    private String userPhone;

    private String professionalName;
    private String professionalSpecialty;

    private Date appointmentDateTime;
}
