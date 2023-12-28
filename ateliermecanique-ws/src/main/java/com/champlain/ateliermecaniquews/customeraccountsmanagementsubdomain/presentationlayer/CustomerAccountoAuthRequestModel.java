package com.champlain.ateliermecaniquews.customeraccountsmanagementsubdomain.presentationlayer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class CustomerAccountoAuthRequestModel {
    private String firstName;
    private String lastName;
    private String email;
    private String token;
    private String role;
}
