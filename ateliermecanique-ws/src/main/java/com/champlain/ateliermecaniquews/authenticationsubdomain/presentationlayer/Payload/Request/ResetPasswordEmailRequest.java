package com.champlain.ateliermecaniquews.authenticationsubdomain.presentationlayer.Payload.Request;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResetPasswordEmailRequest {
    private String email;
}
