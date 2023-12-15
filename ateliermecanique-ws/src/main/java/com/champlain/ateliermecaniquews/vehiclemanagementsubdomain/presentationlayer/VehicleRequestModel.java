package com.champlain.ateliermecaniquews.vehiclemanagementsubdomain.presentationlayer;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

//@AllArgsConstructor (access = AccessLevel.PRIVATE)

@Value
@Builder
@AllArgsConstructor
public class VehicleRequestModel {

    private String customerId;
    private String make;
    private String model;
    private String year;
    private String transmissionType;
    private String mileage;
}
