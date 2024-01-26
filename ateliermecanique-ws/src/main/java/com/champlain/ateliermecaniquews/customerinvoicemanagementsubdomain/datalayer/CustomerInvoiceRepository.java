package com.champlain.ateliermecaniquews.customerinvoicemanagementsubdomain.datalayer;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerInvoiceRepository extends JpaRepository<CustomerInvoice, Integer> {

    //CustomerInvoice findCustomerInvoiceByInvoiceIdentifier_InvoiceId(String invoiceId);

}
