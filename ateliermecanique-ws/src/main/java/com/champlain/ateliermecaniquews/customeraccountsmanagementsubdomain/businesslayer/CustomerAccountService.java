package com.champlain.ateliermecaniquews.customeraccountsmanagementsubdomain.businesslayer;


import com.champlain.ateliermecaniquews.customeraccountsmanagementsubdomain.presentationlayer.CustomerAccountRequestModel;
import com.champlain.ateliermecaniquews.customeraccountsmanagementsubdomain.presentationlayer.CustomerAccountResponseModel;

import java.util.List;

public interface CustomerAccountService {

    //Admin permissions
    List<CustomerAccountResponseModel> getAllCustomerAccounts();
    CustomerAccountResponseModel getCustomerAccountByCustomerId(String customerId);
    CustomerAccountResponseModel updateCustomerAccountByCustomerId(String customerId, CustomerAccountRequestModel customerAccountRequestModel);
    void deleteCustomerAccountByCustomerId(String customerId);

}
