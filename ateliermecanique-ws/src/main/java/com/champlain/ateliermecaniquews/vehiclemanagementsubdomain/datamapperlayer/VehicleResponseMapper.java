package com.champlain.ateliermecaniquews.vehiclemanagementsubdomain.datamapperlayer;


import com.champlain.ateliermecaniquews.vehiclemanagementsubdomain.datalayer.Vehicle;
import com.champlain.ateliermecaniquews.vehiclemanagementsubdomain.presentationlayer.VehicleResponseModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface VehicleResponseMapper {

    @Mapping(expression = "java(vehicle.getVehicleIdentifier().getVehicleId())", target = "vehicleId")
    @Mapping(target = "userId", source = "vehicle.userId")
    @Mapping(target = "transmission_type", source = "vehicle.transmission_type")

    VehicleResponseModel entityToResponseModel(Vehicle vehicle);

    List<VehicleResponseModel> entityToResponseModelList(List<Vehicle> vehicles);
}
