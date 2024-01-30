package com.champlain.ateliermecaniquews.customerinvoicemanagementsubdomain.businesslayer;

import com.champlain.ateliermecaniquews.customerinvoicemanagementsubdomain.presentationlayer.CustomerInvoiceRequestModel;
import com.champlain.ateliermecaniquews.customerinvoicemanagementsubdomain.presentationlayer.CustomerInvoiceResponseModel;

import java.util.List;

public interface CustomerInvoiceService {

    List<CustomerInvoiceResponseModel> getAllInvoices();
    List<CustomerInvoiceResponseModel> getAllInvoicesByCustomerId(String customerId);
    CustomerInvoiceResponseModel addInvoiceToCustomerAccount(String customerId, CustomerInvoiceRequestModel customerInvoiceRequestModel);

    CustomerInvoiceResponseModel getInvoiceById(String invoiceId);



}
