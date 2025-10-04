package agendamento_consumer.postech_fase3.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
@Getter
@Setter
public class AppointmentDetailsDTO implements Serializable {
    private String userName;
    private String userEmail;
    private String userPhone;

    private String professionalName;
    private String professionalSpecialty;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Date appointmentDateTime;
}
