package com.champlain.ateliermecaniquews.customeraccountsmanagementsubdomain.presentationlayer;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;


//@AllArgsConstructor (access = AccessLevel.PRIVATE)

@Value
@Builder
@AllArgsConstructor
public class CustomerAccountRequestModel {

    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;

}
