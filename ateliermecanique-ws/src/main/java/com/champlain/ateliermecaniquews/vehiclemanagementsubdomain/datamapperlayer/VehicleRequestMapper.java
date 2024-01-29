package com.champlain.ateliermecaniquews.vehiclemanagementsubdomain.datamapperlayer;

import com.champlain.ateliermecaniquews.vehiclemanagementsubdomain.datalayer.Vehicle;
import com.champlain.ateliermecaniquews.vehiclemanagementsubdomain.presentationlayer.VehicleRequestModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface VehicleRequestMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "vehicleIdentifier", ignore = true)
    @Mapping(source = "customerId", target = "userId")
    Vehicle requestModelToEntity(VehicleRequestModel vehicleRequestModel);
}