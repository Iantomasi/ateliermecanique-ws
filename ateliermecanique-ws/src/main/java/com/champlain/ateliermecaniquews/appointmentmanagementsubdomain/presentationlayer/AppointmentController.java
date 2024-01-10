package com.champlain.ateliermecaniquews.appointmentmanagementsubdomain.presentationlayer;

import com.champlain.ateliermecaniquews.appointmentmanagementsubdomain.businesslayer.AppointmentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class AppointmentController {

    final private AppointmentService appointmentService;

    @GetMapping("/appointments")
    public ResponseEntity<List<AppointmentResponseModel>> getAllAppointmentsAdmin() {
        List<AppointmentResponseModel> appointments = appointmentService.getAllAppointments();
        if (appointments == null || appointments.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(appointments);
    }


    @GetMapping("/customers/{customerId}/appointments")
    public ResponseEntity<List<AppointmentResponseModel>> getAllAppointmentsByCustomerId(@PathVariable String customerId) {
        List<AppointmentResponseModel> appointments = appointmentService.getAllAppointmentsByCustomerId(customerId);
        if (appointments == null || appointments.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(appointments);
    }


    @PutMapping({"/appointments/{appointmentId}/updateStatus","/customers/{customerId}/appointments/{appointmentId}/updateStatus"})
    public ResponseEntity<AppointmentResponseModel> updateAppointmentStatus(@PathVariable String appointmentId, @RequestParam boolean isConfirm) {
        AppointmentResponseModel appointment = appointmentService.updateAppointmentStatus(appointmentId, isConfirm);
        if (appointment == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(appointment);
    }


}
