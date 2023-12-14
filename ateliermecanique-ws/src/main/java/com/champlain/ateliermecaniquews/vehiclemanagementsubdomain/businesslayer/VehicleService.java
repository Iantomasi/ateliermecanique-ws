package com.champlain.ateliermecaniquews.vehiclemanagementsubdomain.businesslayer;

import com.champlain.ateliermecaniquews.vehiclemanagementsubdomain.presentationlayer.VehicleRequestModel;
import com.champlain.ateliermecaniquews.vehiclemanagementsubdomain.presentationlayer.VehicleResponseModel;

import java.util.List;

public interface VehicleService {

    List<VehicleResponseModel> getAllVehiclesForCustomer(String customerId);
<<<<<<< HEAD

    VehicleResponseModel getVehicleByVehicleId(String customerId, String vehicleId);
    VehicleResponseModel updateVehicleByVehicleId(VehicleRequestModel vehicleRequestModel, String customerId, String vehicleId);




=======
    VehicleResponseModel addVehicleToCustomer(String customerId, VehicleRequestModel vehicleRequestModel);
>>>>>>> fb03916 (Back end working)
    void deleteAllVehiclesByCustomerId(String customerId);
}
