package com.champlain.ateliermecaniquews.customeraccountsmanagementsubdomain.presentationlayer;

import lombok.*;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CustomerAccountResponseModel {

    private String customerId;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
}
