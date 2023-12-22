package com.champlain.ateliermecaniquews.vehiclemanagementsubdomain.businesslayer;

import com.champlain.ateliermecaniquews.vehiclemanagementsubdomain.presentationlayer.VehicleRequestModel;
import com.champlain.ateliermecaniquews.vehiclemanagementsubdomain.presentationlayer.VehicleResponseModel;

import java.util.List;

public interface VehicleService {

    List<VehicleResponseModel> getAllVehiclesByCustomerId(String customerId);

    VehicleResponseModel getVehicleByVehicleId(String customerId, String vehicleId);
    VehicleResponseModel updateVehicleByVehicleId(VehicleRequestModel vehicleRequestModel, String customerId, String vehicleId);

    VehicleResponseModel addVehicleToCustomerAccount(String customerId, VehicleRequestModel vehicleRequestModel);
    void deleteAllVehiclesByCustomerId(String customerId);

    void deleteVehicleByVehicleId(String customerId, String vehicleId);
}
