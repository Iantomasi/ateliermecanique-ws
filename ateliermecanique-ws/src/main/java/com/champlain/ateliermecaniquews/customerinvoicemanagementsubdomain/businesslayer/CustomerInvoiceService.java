package com.champlain.ateliermecaniquews.customerinvoicemanagementsubdomain.businesslayer;

import com.champlain.ateliermecaniquews.customerinvoicemanagementsubdomain.presentationlayer.CustomerInvoiceRequestModel;
import com.champlain.ateliermecaniquews.customerinvoicemanagementsubdomain.presentationlayer.CustomerInvoiceResponseModel;

import java.util.List;

public interface CustomerInvoiceService {

    List<CustomerInvoiceResponseModel> getAllInvoices();
    List<CustomerInvoiceResponseModel> getAllInvoicesByCustomerId(String customerId);
    CustomerInvoiceResponseModel addInvoiceToCustomerAccount(String customerId, CustomerInvoiceRequestModel customerInvoiceRequestModel);

<<<<<<< HEAD
    CustomerInvoiceResponseModel getInvoiceById(String invoiceId);

=======
    CustomerInvoiceResponseModel updateCustomerInvoice(String invoiceId, CustomerInvoiceRequestModel customerInvoiceRequestModel);
>>>>>>> f86b5c2 (Backend working)


}
