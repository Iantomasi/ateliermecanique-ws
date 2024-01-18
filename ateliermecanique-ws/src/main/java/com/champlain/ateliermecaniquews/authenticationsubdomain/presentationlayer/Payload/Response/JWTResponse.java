package com.champlain.ateliermecaniquews.authenticationsubdomain.presentationlayer.Payload.Response;

import lombok.Data;

import java.util.List;

@Data
public class JWTResponse {

    private String token;
    private String type = "Bearer";
    private String id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String picture;
    private List<String> roles;

    public JWTResponse(String token, String id, String firstName, String lastName, String phoneNumber, String email,String picture, List<String> roles) {
        this.token = token;
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.picture = picture;
        this.roles = roles;
    }

}
