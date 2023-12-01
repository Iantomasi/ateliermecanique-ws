package com.champlain.ateliermecaniquews.authenticationsubdomain.datalayer;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.util.UUID;

@Embeddable
public class UserIdentifier {

    @Column(name = "customer_id")
    private String userId;

    public UserIdentifier() {
        this.userId = UUID.randomUUID().toString();
    }

    public String getUserId() {
        return userId;
    }
}
