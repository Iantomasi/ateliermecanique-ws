package com.champlain.ateliermecaniquews.appointmentmanagementsubdomain.presentationlayer;

import com.champlain.ateliermecaniquews.appointmentmanagementsubdomain.businesslayer.AppointmentService;
import com.champlain.ateliermecaniquews.authenticationsubdomain.dataLayer.User;
import com.champlain.ateliermecaniquews.authenticationsubdomain.dataLayer.repositories.UserRepository;
import com.champlain.ateliermecaniquews.authenticationsubdomain.utils.security.services.UserDetailsImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
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
    public ResponseEntity<AppointmentResponseModel> getAppointmentByIdCustomer(
            @PathVariable String appointmentId,
            @PathVariable String customerId) {


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();


        if (authentication.getAuthorities().stream().noneMatch(a -> a.getAuthority().equals("ROLE_CUSTOMER"))) {
            // Check if it's an admin, and go straight to checking if the user exists
            User user = userRepository.findUserByUserIdentifier_UserId(customerId);

            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } else {
            // It's a customer, check if the authenticated user's ID matches the path variable
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            String authenticatedUserId = userDetails.getUserId();

            if (!authenticatedUserId.equals(customerId)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();


        if (authentication.getAuthorities().stream().noneMatch(a -> a.getAuthority().equals("ROLE_CUSTOMER"))) {
            // Check if it's an admin, and go straight to checking if the user exists
            User user = userRepository.findUserByUserIdentifier_UserId(customerId);

            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } else {
            // It's a customer, check if the authenticated user's ID matches the path variable
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            String authenticatedUserId = userDetails.getUserId();

            if (!authenticatedUserId.equals(customerId)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
        }

        List<AppointmentResponseModel> appointments = appointmentService.getAllAppointmentsByCustomerId(customerId);

        if (appointments == null || appointments.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok(appointments);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/appointments/{appointmentId}")
    public ResponseEntity<AppointmentResponseModel> updateAppointmentByAppointmentIdAdmin(@RequestBody AppointmentRequestModel appointmentRequestModel, @PathVariable String appointmentId) {

        AppointmentResponseModel appointment = appointmentService.updateAppointmentByAppointmentId(appointmentRequestModel, appointmentId);
        if (appointment == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(appointment);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('CUSTOMER')")
    @PutMapping("/customers/{customerId}/appointments/{appointmentId}")
    public ResponseEntity<AppointmentResponseModel> updateAppointmentByAppointmentId(@RequestBody AppointmentRequestModel appointmentRequestModel, @PathVariable String appointmentId, @PathVariable String customerId) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();


        if (authentication.getAuthorities().stream().noneMatch(a -> a.getAuthority().equals("ROLE_CUSTOMER"))) {
            // Check if it's an admin, and go straight to checking if the user exists
            User user = userRepository.findUserByUserIdentifier_UserId(customerId);

            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } else {
            // It's a customer, check if the authenticated user's ID matches the path variable
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            String authenticatedUserId = userDetails.getUserId();

            if (!authenticatedUserId.equals(customerId)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
        }

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

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();


        if (authentication.getAuthorities().stream().noneMatch(a -> a.getAuthority().equals("ROLE_CUSTOMER"))) {
            // Check if it's an admin, and go straight to checking if the user exists
            User user = userRepository.findUserByUserIdentifier_UserId(customerId);

            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } else {
            // It's a customer, check if the authenticated user's ID matches the path variable
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            String authenticatedUserId = userDetails.getUserId();

            if (!authenticatedUserId.equals(customerId)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
        }
        AppointmentResponseModel appointment = appointmentService.updateAppointmentStatus(appointmentId, isConfirm);
        if (appointment == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(appointment);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/appointments/{appointmentId}")
    public ResponseEntity<Void> deleteAppointmentByAppointmentIdAdmin(@PathVariable String appointmentId) {
        try {
            appointmentService.deleteAppointmentByAppointmentId(appointmentId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('CUSTOMER')")
    @DeleteMapping("/customers/{customerId}/appointments/{appointmentId}")
    public ResponseEntity<Void> deleteAppointmentByAppointmentIdCustomer(@PathVariable String appointmentId,@PathVariable String customerId) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();


            if (authentication.getAuthorities().stream().noneMatch(a -> a.getAuthority().equals("ROLE_CUSTOMER"))) {
                // Check if it's an admin, and go straight to checking if the user exists
                User user = userRepository.findUserByUserIdentifier_UserId(customerId);

                if (user == null) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
                }
            } else {
                // It's a customer, check if the authenticated user's ID matches the path variable
                UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
                String authenticatedUserId = userDetails.getUserId();

                if (!authenticatedUserId.equals(customerId)) {
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
                }
            }
            appointmentService.deleteAppointmentByAppointmentId(appointmentId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

   @PreAuthorize("hasRole('CUSTOMER') or hasRole('ADMIN')")
    @PostMapping("/customers/{customerId}/appointments")
    public ResponseEntity<AppointmentResponseModel> addAppointmentToCustomerAccountCustomer(@PathVariable String customerId, @RequestBody AppointmentRequestModel appointmentRequestModel)  {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();


        if (authentication.getAuthorities().stream().noneMatch(a -> a.getAuthority().equals("ROLE_CUSTOMER"))) {
            // Check if it's an admin, and go straight to checking if the user exists
            User user = userRepository.findUserByUserIdentifier_UserId(customerId);

            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } else {
            // It's a customer, check if the authenticated user's ID matches the path variable
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            String authenticatedUserId = userDetails.getUserId();

            if (!authenticatedUserId.equals(customerId)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
        }

        AppointmentResponseModel appointment = appointmentService.addAppointmentToCustomerAccount(customerId, appointmentRequestModel); //appointmentRequestModel -> addAppointmentRequest
        if (appointment == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(appointment);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/appointments")
    public ResponseEntity<AppointmentResponseModel> addAppointmentToCustomerAccountAdmin(@PathVariable String customerId, @RequestBody AppointmentRequestModel appointmentRequestModel) {
        AppointmentResponseModel appointment = appointmentService.addAppointmentToCustomerAccount(customerId, appointmentRequestModel);
        if (appointment == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(appointment);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/appointments/cancelled")
    public ResponseEntity<Void> deleteAllCancelledAppointments(){
    try{
        appointmentService.deleteAllCancelledAppointments();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PreAuthorize("hasRole('CUSTOMER') or hasRole('ADMIN')")
    @GetMapping("/availability/{date}")
    public ResponseEntity<Map<String, Boolean>> checkTimeSlotAvailability(@PathVariable LocalDate date) {
        Map<String, Boolean> availability = appointmentService.checkTimeSlotAvailability(date);
        if (availability == null || availability.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(availability);
    }

}
