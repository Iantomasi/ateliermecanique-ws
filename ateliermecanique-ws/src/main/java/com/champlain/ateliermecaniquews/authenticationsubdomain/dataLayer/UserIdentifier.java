package com.champlain.ateliermecaniquews.authenticationsubdomain.dataLayer;

import jakarta.persistence.Embeddable;

import java.util.UUID;

@Embeddable
public class UserIdentifier {

    private String userId;


    public UserIdentifier(){
        this.userId = UUID.randomUUID().toString();
    }

    public String getUserId() {
        return userId;
    }

}
