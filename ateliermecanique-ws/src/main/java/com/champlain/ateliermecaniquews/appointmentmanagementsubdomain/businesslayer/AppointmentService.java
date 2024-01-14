package com.champlain.ateliermecaniquews.appointmentmanagementsubdomain.businesslayer;

import com.champlain.ateliermecaniquews.appointmentmanagementsubdomain.presentationlayer.AppointmentRequestModel;
import com.champlain.ateliermecaniquews.appointmentmanagementsubdomain.presentationlayer.AppointmentResponseModel;

import java.util.List;

public interface AppointmentService {

    List<AppointmentResponseModel> getAllAppointments();

    List<AppointmentResponseModel> getAllAppointmentsByCustomerId(String customerId);
    AppointmentResponseModel updateAppointmentStatus(String appointmentId, boolean isConfirm);
    AppointmentResponseModel addAppointmentToCustomerAccount(String customerId, AppointmentRequestModel appointmentRequestModel);

    AppointmentResponseModel getAppointmentByAppointmentId(String appointmentId);
    void deleteAllCancelledAppointments();
    void deleteAppointmentByAppointmentId(String appointmentId);
}
