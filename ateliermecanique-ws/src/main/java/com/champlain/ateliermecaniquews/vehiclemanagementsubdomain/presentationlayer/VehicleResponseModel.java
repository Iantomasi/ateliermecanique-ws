package com.champlain.ateliermecaniquews.vehiclemanagementsubdomain.presentationlayer;

import com.champlain.ateliermecaniquews.vehiclemanagementsubdomain.datalayer.TransmissionType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@AllArgsConstructor
public class VehicleResponseModel {

    private String vehicleId;
    private String customerId;
    private String make;
    private String model;
    private String year;
    private TransmissionType transmission_type;
    private String mileage;
}
