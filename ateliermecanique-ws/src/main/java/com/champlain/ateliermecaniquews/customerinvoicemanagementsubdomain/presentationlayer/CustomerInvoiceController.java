package com.champlain.ateliermecaniquews.customerinvoicemanagementsubdomain.presentationlayer;

import com.champlain.ateliermecaniquews.appointmentmanagementsubdomain.presentationlayer.AppointmentResponseModel;
import com.champlain.ateliermecaniquews.authenticationsubdomain.dataLayer.User;
import com.champlain.ateliermecaniquews.authenticationsubdomain.dataLayer.repositories.UserRepository;
import com.champlain.ateliermecaniquews.authenticationsubdomain.utils.security.services.UserDetailsImpl;
import com.champlain.ateliermecaniquews.customerinvoicemanagementsubdomain.businesslayer.CustomerInvoiceService;
import com.champlain.ateliermecaniquews.customerinvoicemanagementsubdomain.datalayer.CustomerInvoiceRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/test")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class CustomerInvoiceController {

    final private CustomerInvoiceService customerInvoiceService;
    final private CustomerInvoiceRepository customerInvoiceRepository;
    final private UserRepository userRepository;

    @GetMapping("/invoices")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<CustomerInvoiceResponseModel>> getAllInvoicesAdmin() {
        List<CustomerInvoiceResponseModel> invoices = customerInvoiceService.getAllInvoices();
        if (invoices == null || invoices.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(invoices);
    }

    //@PreAuthorize("hasRole('ADMIN') or hasRole('CUSTOMER')")
    @GetMapping("/customers/{customerId}/invoices")
    public ResponseEntity<List<CustomerInvoiceResponseModel>> getAllInvoicesByCustomerId(@PathVariable String customerId) {
       /*
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

        */



        List<CustomerInvoiceResponseModel> invoices = customerInvoiceService.getAllInvoicesByCustomerId(customerId);

        if (invoices == null || invoices.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok(invoices);
    }



}
