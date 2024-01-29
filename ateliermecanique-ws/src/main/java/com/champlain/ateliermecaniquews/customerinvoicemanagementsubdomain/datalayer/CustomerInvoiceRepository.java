package com.champlain.ateliermecaniquews.customerinvoicemanagementsubdomain.datalayer;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerInvoiceRepository extends JpaRepository<CustomerInvoice, Integer> {

    CustomerInvoice findCustomerInvoiceByCustomerInvoiceIdentifier_InvoiceId(String invoiceId);

    List<CustomerInvoice> findAllInvoicesByCustomerId(String customerId);

<<<<<<< HEAD

=======
    CustomerInvoice findCustomerInvoiceByCustomerInvoiceIdentifier_InvoiceId(String invoiceId);
>>>>>>> f86b5c2 (Backend working)
}
