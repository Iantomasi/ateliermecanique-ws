package com.champlain.ateliermecaniquews.appointmentmanagementsubdomain.businesslayer;

import com.champlain.ateliermecaniquews.appointmentmanagementsubdomain.presentationlayer.AppointmentResponseModel;

import java.util.List;

public interface AppointmentService {

    List<AppointmentResponseModel> getAllAppointments();
<<<<<<< HEAD
    List<AppointmentResponseModel> getAllAppointmentsByCustomerId(String customerId);
    AppointmentResponseModel updateAppointmentStatus(String appointmentId, boolean isConfirm);
=======

    AppointmentResponseModel getAppointmentById(String appointmentId);

>>>>>>> a59de34 (Back end working)
}
