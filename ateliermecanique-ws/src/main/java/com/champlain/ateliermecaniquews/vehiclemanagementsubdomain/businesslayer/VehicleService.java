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
>>>>>>> 0bbc5e0690ad55cfcdba4dc945376bac24b283cb
    VehicleResponseModel addVehicleToCustomer(String customerId, VehicleRequestModel vehicleRequestModel);
    void deleteAllVehiclesByCustomerId(String customerId);
}
