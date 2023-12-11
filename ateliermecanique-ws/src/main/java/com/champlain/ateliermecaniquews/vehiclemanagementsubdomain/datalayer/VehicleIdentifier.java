package com.champlain.ateliermecaniquews.vehiclemanagementsubdomain.datalayer;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.UUID;

@Embeddable
public class VehicleIdentifier {
    private String vehicleId;

    public VehicleIdentifier(){
       this.vehicleId = UUID.randomUUID().toString();
    }

    public String getVehicleId() {
        return vehicleId;
    }
}
