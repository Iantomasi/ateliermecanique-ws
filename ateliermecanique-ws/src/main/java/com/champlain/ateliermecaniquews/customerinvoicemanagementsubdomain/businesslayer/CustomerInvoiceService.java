package com.champlain.ateliermecaniquews.customerinvoicemanagementsubdomain.businesslayer;

import com.champlain.ateliermecaniquews.customerinvoicemanagementsubdomain.presentationlayer.CustomerInvoiceResponseModel;

import java.util.List;

public interface CustomerInvoiceService {

    List<CustomerInvoiceResponseModel> getAllInvoices();

    //test
    List<CustomerInvoiceResponseModel> getAllInvoicesByCustomerId(String customerId);


}
