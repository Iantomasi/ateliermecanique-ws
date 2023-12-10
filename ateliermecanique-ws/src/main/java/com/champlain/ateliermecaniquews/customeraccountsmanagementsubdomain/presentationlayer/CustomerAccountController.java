package com.champlain.ateliermecaniquews.customeraccountsmanagementsubdomain.presentationlayer;

import com.champlain.ateliermecaniquews.customeraccountsmanagementsubdomain.businesslayer.CustomerAccountService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/customers")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class CustomerAccountController {

    final private CustomerAccountService customerAccountService;

    @GetMapping()
    public List<CustomerAccountResponseModel> getAllCustomerAccounts(){
        return customerAccountService.getAllCustomerAccounts();
    }

    @GetMapping("/{customerId}")
    public CustomerAccountResponseModel getCustomerAccountByCustomerId(@PathVariable String customerId){
        return customerAccountService.getCustomerAccountById(customerId);
    }

}
