package agendamentoAPI.postech_fase3.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import agendamentoAPI.postech_fase3.dto.AppointmentDTO;
import agendamentoAPI.postech_fase3.dto.AppointmentDetailsDTO;
import agendamentoAPI.postech_fase3.model.Appointment;
import agendamentoAPI.postech_fase3.service.AppointmentProducer;
import agendamentoAPI.postech_fase3.service.AppointmentService;

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
    @PreAuthorize("hasRole('ENFERMEIRO')")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<AppointmentDetailsDTO> create(@RequestBody AppointmentDTO dto) {
        AppointmentDetailsDTO details = appointmentService.create(dto);

        appointmentProducer.sendAppointmentMessage(details);

        return ResponseEntity.ok(details);
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ENFERMEIRO')")
    public ResponseEntity<AppointmentDetailsDTO> update(@PathVariable Long id, @RequestBody AppointmentDTO dto) {
        AppointmentDetailsDTO details = appointmentService.update(id, dto);

        appointmentProducer.sendAppointmentMessage(details);

        return ResponseEntity.ok(details);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ENFERMEIRO')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        appointmentService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @PreAuthorize("hasRole('ENFERMEIRO')")
    public ResponseEntity<List<Appointment>> listAll() {
        return ResponseEntity.ok(appointmentService.listAll());
    }
    
    @GetMapping("/patient")
    @PreAuthorize("hasRole('PACIENTE')")
    public ResponseEntity<List<Appointment>> listPatientAll() {
        return ResponseEntity.ok(appointmentService.listPatientAll());
    }
}
