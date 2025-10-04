package agendamento_consumer.postech_fase3.consumer;

import agendamento_consumer.postech_fase3.dto.AppointmentDetailsDTO;
import agendamento_consumer.postech_fase3.service.EmailService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Component
public class    AppointmentConsumer {

    private final EmailService emailService;

    public AppointmentConsumer(EmailService emailService){
        this.emailService = emailService;
    }
    @RabbitListener(queues = "${rabbitmq.queue}")
    public void receiveMessage(AppointmentDetailsDTO appointment) {
        System.out.println("Mensagem recebida: " + appointment);

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");


        LocalDateTime dateTime = appointment.getAppointmentDateTime()
                .toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();

        String mensagem = String.format(
                "Olá, %s! Sua consulta foi agendada para o dia %s às %s com o profissional %s.",
                appointment.getUserName(),
                dateTime.toLocalDate().format(dateFormatter),
                dateTime.toLocalTime().format(timeFormatter),
                appointment.getProfessionalName()
        );


        emailService.sendEmail(
                appointment.getUserEmail(),
                "Confirmação de Consulta",
                mensagem
        );
    }
}
