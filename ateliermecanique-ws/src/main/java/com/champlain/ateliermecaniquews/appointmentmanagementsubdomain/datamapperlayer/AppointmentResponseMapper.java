package com.champlain.ateliermecaniquews.appointmentmanagementsubdomain.datamapperlayer;

import com.champlain.ateliermecaniquews.appointmentmanagementsubdomain.datalayer.Appointment;
import com.champlain.ateliermecaniquews.customeraccountsmanagementsubdomain.presentationlayer.AppointmentResponseModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AppointmentResponseMapper {

    @Mapping(expression = "java(appointment.getAppointmentIdentifier().getAppointmentId())", target = "appointmentId")
    @Mapping(target = "customerId", source = "appointment.customerId")
    @Mapping(target = "vehicleId", source = "appointment.vehicleId")
    @Mapping(target= "status", source = "appointment.status")
    AppointmentResponseModel entityToResponseModel(Appointment appointment);

    List<AppointmentResponseModel> entityToResponseModelList(List<Appointment> appointments);

}
