package com.champlain.ateliermecaniquews.authenticationsubdomain.presentationlayer.Payload.Request;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class SignupRequest {

    private String username;
    private String email;
    private Set<String> role;
    private String password;
}
