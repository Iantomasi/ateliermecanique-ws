package com.champlain.ateliermecaniquews.appointmentmanagementsubdomain.presentationlayer;

import com.champlain.ateliermecaniquews.appointmentmanagementsubdomain.businesslayer.AppointmentService;
import com.champlain.ateliermecaniquews.authenticationsubdomain.dataLayer.User;
import com.champlain.ateliermecaniquews.authenticationsubdomain.dataLayer.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class AppointmentController {

    final private AppointmentService appointmentService;
    final private UserRepository userRepository;

    @GetMapping("/appointments")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<AppointmentResponseModel>> getAllAppointmentsAdmin() {
        List<AppointmentResponseModel> appointments = appointmentService.getAllAppointments();
        if (appointments == null || appointments.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(appointments);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/appointments/{appointmentId}")
    public ResponseEntity<AppointmentResponseModel> getAppointmentByIdAdmin(@PathVariable String appointmentId) {
        AppointmentResponseModel appointment = appointmentService.getAppointmentByAppointmentId(appointmentId);
        if (appointment == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(appointment);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('CUSTOMER')")
    @GetMapping("customers/{customerId}/appointments/{appointmentId}")
    public ResponseEntity<AppointmentResponseModel> getAppointmentByIdCustomer(@PathVariable String appointmentId,@PathVariable String customerId) {

        User user = userRepository.findUserByUserIdentifier_UserId(customerId);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        AppointmentResponseModel appointment = appointmentService.getAppointmentByAppointmentId(appointmentId);
        if (appointment == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(appointment);
    }


    @PreAuthorize("hasRole('ADMIN') or hasRole('CUSTOMER')")
    @GetMapping("/customers/{customerId}/appointments")
    public ResponseEntity<List<AppointmentResponseModel>> getAllAppointmentsByCustomerId(@PathVariable String customerId) {
        List<AppointmentResponseModel> appointments = appointmentService.getAllAppointmentsByCustomerId(customerId);
        if (appointments == null || appointments.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(appointments);
    }

    @PutMapping({"/appointments/{appointmentId}","/customers/{customerId}/appointments/{appointmentId}"})
    public ResponseEntity<AppointmentResponseModel> updateAppointmentByAppointmentId(@RequestBody AppointmentRequestModel appointmentRequestModel, @PathVariable String appointmentId) {
        AppointmentResponseModel appointment = appointmentService.updateAppointmentByAppointmentId(appointmentRequestModel, appointmentId);
        if (appointment == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(appointment);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/appointments/{appointmentId}/updateStatus")
    public ResponseEntity<AppointmentResponseModel> updateAppointmentStatusAdmin(@PathVariable String appointmentId, @RequestParam boolean isConfirm) {
        AppointmentResponseModel appointment = appointmentService.updateAppointmentStatus(appointmentId, isConfirm);
        if (appointment == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(appointment);
    }

    @PreAuthorize("hasRole('CUSTOMER') or hasRole('ADMIN')")
    @PutMapping("/customers/{customerId}/appointments/{appointmentId}/updateStatus")
    public ResponseEntity<AppointmentResponseModel> updateAppointmentStatusCustomer(@PathVariable String customerId,@PathVariable String appointmentId, @RequestParam boolean isConfirm) {
        User user = userRepository.findUserByUserIdentifier_UserId(customerId);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        AppointmentResponseModel appointment = appointmentService.updateAppointmentStatus(appointmentId, isConfirm);
        if (appointment == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(appointment);
    }

    @DeleteMapping({"/appointments/{appointmentId}","/customers/{customerId}/appointments/{appointmentId}"})
    public ResponseEntity<Void> deleteAppointmentByAppointmentId(@PathVariable String appointmentId) {
        try {
            appointmentService.deleteAppointmentByAppointmentId(appointmentId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    @PostMapping({"/customers/{customerId}/appointments","/appointments"})
    public ResponseEntity<AppointmentResponseModel> addAppointmentToCustomerAccount(@PathVariable String customerId, @RequestBody AppointmentRequestModel appointmentRequestModel) {
        AppointmentResponseModel appointment = appointmentService.addAppointmentToCustomerAccount(customerId, appointmentRequestModel);
        if (appointment == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(appointment);
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

    @GetMapping("/availability/{date}")
    public ResponseEntity<Map<String, Boolean>> checkTimeSlotAvailability(@PathVariable LocalDate date) {
        Map<String, Boolean> availability = appointmentService.checkTimeSlotAvailability(date);
        if (availability == null || availability.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(availability);
    }

}
