package com.champlain.ateliermecaniquews.authenticationsubdomain.presentationlayer.Payload.Request;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class SignupRequest {

    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private Set<String> role;
    private String password;
}
