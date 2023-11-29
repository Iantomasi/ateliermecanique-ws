package com.champlain.ateliermecaniquews.customeraccountsmanagementsubdomain.datalayer;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "customers")
@Data
public class CustomerAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Embedded
    private CustomerAccountIdentifier customerAccountIdentifier;

    private String customerFirstName;
    private String customerLastName;
    private String customerEmail;
    private String customerPhoneNumber;

    private String customerPassword;

    CustomerAccount(){this.customerAccountIdentifier = new CustomerAccountIdentifier();}

    public CustomerAccount(String customerFirstName, String customerLastName, String customerEmail, String customerPhoneNumber, String customerPassword) {
        this.customerAccountIdentifier = new CustomerAccountIdentifier();
        this.customerFirstName = customerFirstName;
        this.customerLastName = customerLastName;
        this.customerEmail = customerEmail;
        this.customerPhoneNumber = customerPhoneNumber;
        this.customerPassword = customerPassword;
    }
}
