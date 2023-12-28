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

    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String password;
    private String token;
    private Role roles;

    public CustomerAccount(){this.customerAccountIdentifier = new CustomerAccountIdentifier();}

    public CustomerAccount(String customerFirstName, String customerLastName, String customerEmail, String customerPhoneNumber, String customerPassword,String token) {
        this.customerAccountIdentifier = new CustomerAccountIdentifier();
        this.firstName = customerFirstName;
        this.lastName = customerLastName;
        this.email = customerEmail;
        this.phoneNumber = customerPhoneNumber;
        this.password = customerPassword;
        this.token = token;
        this.roles=Role.CUSTOMER;
    }
    public CustomerAccount(String customerFirstName, String customerLastName, String customerEmail, String customerPhoneNumber, String customerPassword) {
        this.customerAccountIdentifier = new CustomerAccountIdentifier();
        this.firstName = customerFirstName;
        this.lastName = customerLastName;
        this.email = customerEmail;
        this.phoneNumber = customerPhoneNumber;
        this.password = customerPassword;
        this.roles=Role.CUSTOMER;
    }
}
