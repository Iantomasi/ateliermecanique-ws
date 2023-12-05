package com.champlain.ateliermecaniquews.customeraccountsmanagementsubdomain.datalayer;

import jakarta.persistence.Embeddable;

import java.util.UUID;

@Embeddable
public class CustomerAccountIdentifier {

    private String customerId;


   public CustomerAccountIdentifier(){
        this.customerId = UUID.randomUUID().toString();
    }

    public String getCustomerId() {
        return customerId;
    }

    //test
}
