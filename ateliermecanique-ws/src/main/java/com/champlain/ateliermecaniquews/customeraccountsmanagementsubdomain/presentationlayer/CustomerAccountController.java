package com.champlain.ateliermecaniquews.customeraccountsmanagementsubdomain.presentationlayer;

import com.champlain.ateliermecaniquews.customeraccountsmanagementsubdomain.businesslayer.CustomerAccountService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
