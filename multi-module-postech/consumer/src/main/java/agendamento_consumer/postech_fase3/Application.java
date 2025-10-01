package agendamento_consumer.postech_fase3;

import agendamento_consumer.postech_fase3.dto.AppointmentDTO;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

@SpringBootApplication
@EnableRabbit
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
		System.out.println("🚀 Consumer de mensagens do RabbitMQ iniciado!");;
	}

}


