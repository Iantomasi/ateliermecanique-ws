package com.champlain.ateliermecaniquews.authenticationsubdomain.presentationlayer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class LoginRequestModel {
    private String firstName;
    private String lastName;
    private String email;
    private String token;
}
