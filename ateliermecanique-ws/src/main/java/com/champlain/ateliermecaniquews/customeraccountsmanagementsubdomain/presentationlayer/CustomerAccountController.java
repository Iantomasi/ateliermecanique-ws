package com.champlain.ateliermecaniquews.customeraccountsmanagementsubdomain.presentationlayer;

import com.champlain.ateliermecaniquews.customeraccountsmanagementsubdomain.businesslayer.CustomerAccountService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/customers")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class CustomerAccountController {

    final private CustomerAccountService customerAccountService;

    @GetMapping()
    public ResponseEntity<List<CustomerAccountResponseModel>> getAllCustomerAccounts() {
        List<CustomerAccountResponseModel> accounts = customerAccountService.getAllCustomerAccounts();
        if (accounts == null || accounts.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(accounts);
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerAccountResponseModel> getCustomerAccountById(@PathVariable String customerId) {
        CustomerAccountResponseModel response = customerAccountService.getCustomerAccountById(customerId);
        if (response == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();        }
        return ResponseEntity.ok(response);
    }

}
