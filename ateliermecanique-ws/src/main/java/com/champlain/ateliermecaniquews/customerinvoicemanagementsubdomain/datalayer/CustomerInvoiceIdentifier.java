package com.champlain.ateliermecaniquews.customerinvoicemanagementsubdomain.datalayer;

import jakarta.persistence.Embeddable;

@Embeddable
public class CustomerInvoiceIdentifier {

    private String invoiceId;

    public CustomerInvoiceIdentifier(){
        this.invoiceId = java.util.UUID.randomUUID().toString();
    }

    public String getInvoiceId() {
        return invoiceId;
    }


}
