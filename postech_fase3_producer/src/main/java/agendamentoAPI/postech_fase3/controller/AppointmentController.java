package agendamentoAPI.postech_fase3.controller;

import agendamentoAPI.postech_fase3.DTO.AppointmentDTO;
import agendamentoAPI.postech_fase3.model.Appointment;
import agendamentoAPI.postech_fase3.service.AppointmentProducer;
import agendamentoAPI.postech_fase3.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/appointments")


public class AppointmentController {

    private final AppointmentService appointmentService;
    private final AppointmentProducer appointmentProducer;

    public AppointmentController(AppointmentService appointmentService, AppointmentProducer appointmentProducer) {
        this.appointmentService = appointmentService;
        this.appointmentProducer = appointmentProducer;
    }

    @PostMapping
    public ResponseEntity<AppointmentDTO> create(@RequestBody AppointmentDTO dto) {

        Appointment appointment = appointmentService.create(dto);

        appointmentProducer.sendAppointmentMessage(appointment);

        dto.setEmail(appointment.getPaciente().getEmail());
        dto.setTelefone(appointment.getPaciente().getTelefone());

        return ResponseEntity.ok(dto);
    }
    @PutMapping("/{id}")
    public ResponseEntity<AppointmentDTO> update(@PathVariable Long id, @RequestBody AppointmentDTO dto) {
        Appointment appointment = appointmentService.update(id, dto);

        appointmentProducer.sendAppointmentMessage(appointment);

        dto.setEmail(appointment.getPaciente().getEmail());
        dto.setTelefone(appointment.getPaciente().getTelefone());

        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        appointmentService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<Appointment>> listAll() {
        return ResponseEntity.ok(appointmentService.listAll());
    }
}
