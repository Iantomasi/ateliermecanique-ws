package com.champlain.ateliermecaniquews.customerinvoicemanagementsubdomain.datamapperlayer;

import com.champlain.ateliermecaniquews.customerinvoicemanagementsubdomain.datalayer.CustomerInvoice;
import com.champlain.ateliermecaniquews.customerinvoicemanagementsubdomain.presentationlayer.CustomerInvoiceResponseModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CustomerInvoiceResponseMapper {

    @Mapping(expression = "java(customerInvoice.getCustomerInvoiceIdentifier().getInvoiceId())", target = "invoiceId")
    @Mapping(target = "customerId", source = "customerInvoice.customerId")
    @Mapping(target = "appointmentId", source = "customerInvoice.appointmentId")
    CustomerInvoiceResponseModel entityToResponseModel(CustomerInvoice customerInvoice);

    List<CustomerInvoiceResponseModel> entityToResponseModelList(List<CustomerInvoice> customerInvoices);

}
