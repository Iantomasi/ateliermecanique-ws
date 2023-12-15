package com.champlain.ateliermecaniquews.customeraccountsmanagementsubdomain.businesslayer;


import com.champlain.ateliermecaniquews.customeraccountsmanagementsubdomain.presentationlayer.CustomerAccountRequestModel;
import com.champlain.ateliermecaniquews.customeraccountsmanagementsubdomain.presentationlayer.CustomerAccountResponseModel;

import java.util.List;

public interface CustomerAccountService {

    //Admin permissions
    List<CustomerAccountResponseModel> getAllCustomerAccounts();
    CustomerAccountResponseModel getCustomerAccountById(String customerId);
    CustomerAccountResponseModel updateCustomerById(String customerId, CustomerAccountRequestModel customerAccountRequestModel);
    void deleteCustomerById(String customerId);

}
// mohit test