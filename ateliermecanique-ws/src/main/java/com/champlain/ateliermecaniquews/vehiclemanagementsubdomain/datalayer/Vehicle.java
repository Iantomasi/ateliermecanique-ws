package com.champlain.ateliermecaniquews.vehiclemanagementsubdomain.datalayer;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "vehicles")
@Data
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Embedded
    private VehicleIdentifier vehicleIdentifier;
    @Column(name = "customer_id")
    private String customerId;
    private String make;
    private String model;
    private String year;
    @Enumerated(EnumType.STRING)
    @Column(name = "transmission_type")
    private TransmissionType transmission_type;
    private String mileage;

    public Vehicle(){ this.vehicleIdentifier = new VehicleIdentifier(); }

    public Vehicle(String customerId, String make, String model, String year, TransmissionType transmission_type, String mileage) {
        this.vehicleIdentifier = new VehicleIdentifier();
        this.customerId = customerId;
        this.make = make;
        this.model = model;
        this.year = year;
        this.transmission_type = transmission_type;
        this.mileage = mileage;
    }


}
