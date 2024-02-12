package com.champlain.ateliermecaniquews.appointmentmanagementsubdomain.businesslayer;

import com.champlain.ateliermecaniquews.appointmentmanagementsubdomain.presentationlayer.AppointmentRequestModel;
import com.champlain.ateliermecaniquews.appointmentmanagementsubdomain.presentationlayer.AppointmentResponseModel;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface AppointmentService {

    List<AppointmentResponseModel> getAllAppointments();

    List<AppointmentResponseModel> getAllAppointmentsByCustomerId(String customerId);
    AppointmentResponseModel updateAppointmentStatus(String appointmentId, boolean isConfirm);
    AppointmentResponseModel updateAppointmentByAppointmentId(AppointmentRequestModel appointmentRequestModel, String appointmentId);
    AppointmentResponseModel addAppointmentToCustomerAccount(String customerId, AppointmentRequestModel appointmentRequestModel);

    AppointmentResponseModel getAppointmentByAppointmentId(String appointmentId);
    void deleteAllCancelledAppointments();
    void deleteAppointmentByAppointmentId(String appointmentId);

    Map<String, Boolean> checkTimeSlotAvailability(LocalDate date);
}

