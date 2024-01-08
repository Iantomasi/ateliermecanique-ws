package com.champlain.ateliermecaniquews.appointmentmanagementsubdomain.businesslayer;

import com.champlain.ateliermecaniquews.appointmentmanagementsubdomain.presentationlayer.AppointmentResponseModel;

import java.util.List;

public interface AppointmentService {

    List<AppointmentResponseModel> getAllAppointments();
    AppointmentResponseModel updateAppointmentStatusAdmin(String appointmentId, boolean isConfirm);
    AppointmentResponseModel updateAppointmentStatusCustomer(String customerId, String appointmentId, boolean isConfirm);
}
