package com.champlain.ateliermecaniquews.vehiclemanagementsubdomain.datamapperlayer;


import com.champlain.ateliermecaniquews.vehiclemanagementsubdomain.datalayer.Vehicle;
import com.champlain.ateliermecaniquews.vehiclemanagementsubdomain.presentationlayer.VehicleResponseModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface VehicleResponseMapper {

    @Mapping(expression = "java(vehicle.getVehicleIdentifier().getVehicleId())", target = "vehicleId")
    VehicleResponseModel entityToResponseModel(Vehicle vehicle);

    List<VehicleResponseModel> entityToResponseModelList(List<Vehicle> vehicles);
}
