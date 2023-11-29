package com.champlain.ateliermecaniquews.customeraccountsmanagementsubdomain.datalayer;

import jakarta.persistence.Embeddable;

import java.util.UUID;

@Embeddable
public class CustomerAccountIdentifier {

    private String customerAccountId;


   public CustomerAccountIdentifier(){
        this.customerAccountId = UUID.randomUUID().toString();
    }

    public String getCustomerAccountId() {
        return customerAccountId;
    }

    //test
}
