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

    @GetMapping({"/appointments/{appointmentId}", "customers/{customerId}/appointments/{appointmentId}"})
    public ResponseEntity<AppointmentResponseModel> getAppointmentByAppointmentId(@PathVariable String appointmentId) {
        AppointmentResponseModel appointment = appointmentService.getAppointmentByAppointmentId(appointmentId);
        if (appointment == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(appointment);
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

<<<<<<< HEAD
    @DeleteMapping({"/appointments/{appointmentId}","/customers/{customerId}/appointments/{appointmentId}"})
    public ResponseEntity<Void> deleteAppointmentByAppointmentId(@PathVariable String appointmentId) {
        try{
            appointmentService.deleteAppointmentByAppointmentId(appointmentId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
=======
    @PostMapping({"/customers/{customerId}/appointments","/appointments"})
    public ResponseEntity<AppointmentResponseModel> addAppointmentToCustomerAccount(@PathVariable String customerId, @RequestBody AppointmentRequestModel appointmentRequestModel) {
        AppointmentResponseModel appointment = appointmentService.addAppointmentToCustomerAccount(customerId, appointmentRequestModel);
        if (appointment == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(appointment);
>>>>>>> 97dc299 (Add new appointment back end working)
    }

    @DeleteMapping("/appointments/cancelled")
    public ResponseEntity<Void> deleteAllCancelledAppointments(){
    try{
        appointmentService.deleteAllCancelledAppointments();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
