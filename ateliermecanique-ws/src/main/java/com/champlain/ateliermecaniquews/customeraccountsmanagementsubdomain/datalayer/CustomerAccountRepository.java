package com.champlain.ateliermecaniquews.customeraccountsmanagementsubdomain.datalayer;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerAccountRepository extends JpaRepository<CustomerAccount, String> {

    CustomerAccount findCustomerAccountByCustomerAccountIdentifier_CustomerId(String customerId);
    CustomerAccount findCustomerAccountByEmail(String email);
}
