package com.champlain.ateliermecaniquews.appointmentmanagementsubdomain.datamapperlayer;

import com.champlain.ateliermecaniquews.appointmentmanagementsubdomain.datalayer.Appointment;
import com.champlain.ateliermecaniquews.customeraccountsmanagementsubdomain.presentationlayer.AppointmentRequestModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AppointmentRequestMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "appointmentIdentifier", ignore = true)
    Appointment requestModelToEntity(AppointmentRequestModel appointmentRequestModel);
}
