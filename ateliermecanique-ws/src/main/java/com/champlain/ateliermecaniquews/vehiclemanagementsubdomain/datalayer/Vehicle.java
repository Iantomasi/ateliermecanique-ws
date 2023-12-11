package com.champlain.ateliermecaniquews.vehiclemanagementsubdomain.datalayer;

import com.champlain.ateliermecaniquews.customeraccountsmanagementsubdomain.datalayer.CustomerAccountIdentifier;
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

    @Embedded
    private CustomerAccountIdentifier customerAccountIdentifier;

    private String make;
    private String model;
    private String year;
    @Column(name = "transmissionType")
    private TransmissionType transmissionType;
    private String mileage;

}
