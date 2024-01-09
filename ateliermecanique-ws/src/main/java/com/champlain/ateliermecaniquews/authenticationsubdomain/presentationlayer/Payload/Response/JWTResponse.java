package com.champlain.ateliermecaniquews.authenticationsubdomain.presentationlayer.Payload.Response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
public class JWTResponse {

    private String token;
    private String type = "Bearer";
    private Long id;
    private String username;
    private String email;
    private List<String> roles;

    public JWTResponse(String token, Long id, String username, String email, List<String> roles) {
        this.token = token;
        this.id = id;
        this.username = username;
        this.email = email;
        this.roles = roles;
    }
}
