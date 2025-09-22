package agendamento_consumer.postech_fase3.consumer;

import agendamento_consumer.postech_fase3.dto.AppointmentDTO;
import agendamento_consumer.postech_fase3.service.EmailService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class    AppointmentConsumer {

    private final EmailService emailService;

    public AppointmentConsumer(EmailService emailService){
        this.emailService = emailService;
    }
    @RabbitListener(queues = "${rabbitmq.queue}")
    public void receiveMessage(AppointmentDTO appointment) {
        System.out.println("Mensagem recebida: " + appointment);

        emailService.sendEmail(
                appointment.getEmail(),
                "Confirmação de Consulta",
                "Olá! Sua consulta foi agendada para: " + appointment.getDataHora()
        );
    }
}
