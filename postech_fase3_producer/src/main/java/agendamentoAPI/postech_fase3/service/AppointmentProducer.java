package agendamentoAPI.postech_fase3.service;

import agendamentoAPI.postech_fase3.dto.AppointmentDTO;
import agendamentoAPI.postech_fase3.model.Appointment;
import agendamentoAPI.postech_fase3.model.User;
import agendamentoAPI.postech_fase3.repository.UserRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AppointmentProducer {
    private final RabbitTemplate rabbitTemplate;
    private final UserRepository userRepository;

    @Value("${rabbitmq.exchange}")
    private String exchange;

    @Value("${rabbitmq.routingkey}")
    private String routingKey;

    public AppointmentProducer(RabbitTemplate rabbitTemplate, UserRepository userRepository) {
        this.rabbitTemplate = rabbitTemplate;
        this.userRepository = userRepository;
    }

    public void sendAppointmentMessage(Appointment appointment) {
        User paciente = appointment.getPaciente();

        AppointmentDTO dto = new AppointmentDTO();
        dto.setPacienteId(paciente.getId());
        dto.setProfissionalId(appointment.getProfissional().getId());
        dto.setDataHora(appointment.getDataHora());
        dto.setEmail(paciente.getEmail());
        dto.setTelefone(paciente.getTelefone());


        rabbitTemplate.convertAndSend(exchange, routingKey, dto);
        System.out.println("📤 Evento de consulta criada enviado para RabbitMQ: " + dto);
    }
}
