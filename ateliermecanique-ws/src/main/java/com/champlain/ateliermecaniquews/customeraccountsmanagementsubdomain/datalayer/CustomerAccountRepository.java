package com.champlain.ateliermecaniquews.customeraccountsmanagementsubdomain.datalayer;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerAccountRepository extends JpaRepository<CustomerAccount, Integer> {

    //CustomerAccount findCustomerAccountById(String customerId);
    //Boolean existsCustomerAccountById(String customerId);

}
