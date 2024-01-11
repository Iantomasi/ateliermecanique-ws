package com.champlain.ateliermecaniquews.customeraccountsmanagementsubdomain.presentationlayer;

import com.champlain.ateliermecaniquews.customeraccountsmanagementsubdomain.businesslayer.CustomerAccountService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/customers")
@AllArgsConstructor
public class CustomerAccountController {

    final private CustomerAccountService customerAccountService;

    @GetMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<CustomerAccountResponseModel>> getAllCustomerAccounts() {
        List<CustomerAccountResponseModel> accounts = customerAccountService.getAllCustomerAccounts();

        if (accounts == null || accounts.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(accounts);
    }

    @GetMapping("/{customerId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CustomerAccountResponseModel> getCustomerAccountByCustomerId(@PathVariable String customerId) {
        CustomerAccountResponseModel response = customerAccountService.getCustomerAccountByCustomerId(customerId);
        if (response == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{customerId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('CUSTOMER')")
    public ResponseEntity<CustomerAccountResponseModel> updateCustomerAccountByCustomerId(@PathVariable String customerId, @RequestBody CustomerAccountRequestModel accountRequestModel){
        CustomerAccountResponseModel response = customerAccountService.updateCustomerAccountByCustomerId(customerId,accountRequestModel);
        if (response == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{customerId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteCustomerAccountByCustomerId(@PathVariable String customerId){
        customerAccountService.deleteCustomerAccountByCustomerId(customerId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
