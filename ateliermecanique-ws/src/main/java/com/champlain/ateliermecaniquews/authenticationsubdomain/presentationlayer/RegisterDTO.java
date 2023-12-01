package com.champlain.ateliermecaniquews.authenticationsubdomain.presentationlayer;
import lombok.*;

@Builder
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDTO {


    private String firstName;
    private String lastName;
    private String username;
    private String email;

    private String phoneNumber;
    private String password;

    String role;
}
