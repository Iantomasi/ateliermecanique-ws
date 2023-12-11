package com.champlain.ateliermecaniquews.customeraccountsmanagementsubdomain.businesslayer;


import com.champlain.ateliermecaniquews.customeraccountsmanagementsubdomain.presentationlayer.CustomerAccountResponseModel;

import java.util.List;

public interface CustomerAccountService {

    //Admin permissions
    List<CustomerAccountResponseModel> getAllCustomerAccounts();
    CustomerAccountResponseModel getCustomerAccountById(String customerId);



}
