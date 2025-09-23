package agendamentoAPI.postech_fase3.controller;

import agendamentoAPI.postech_fase3.dto.AppointmentDTO;
import agendamentoAPI.postech_fase3.dto.AppointmentDetailsDTO;
import agendamentoAPI.postech_fase3.model.Appointment;
import agendamentoAPI.postech_fase3.service.AppointmentProducer;
import agendamentoAPI.postech_fase3.service.AppointmentService;
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
    public ResponseEntity<AppointmentDetailsDTO> create(@RequestBody AppointmentDTO dto) {

        AppointmentDetailsDTO details = appointmentService.create(dto);

        appointmentProducer.sendAppointmentMessage(details);

        return ResponseEntity.ok(details);
    }
    @PutMapping("/{id}")
    public ResponseEntity<AppointmentDetailsDTO> update(@PathVariable Long id, @RequestBody AppointmentDTO dto) {
        AppointmentDetailsDTO details = appointmentService.update(id, dto);

        appointmentProducer.sendAppointmentMessage(details);

        return ResponseEntity.ok(details);
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
