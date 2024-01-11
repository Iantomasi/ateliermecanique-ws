package com.champlain.ateliermecaniquews.appointmentmanagementsubdomain.businesslayer;

import com.champlain.ateliermecaniquews.appointmentmanagementsubdomain.presentationlayer.AppointmentResponseModel;

import java.util.List;

public interface AppointmentService {

    List<AppointmentResponseModel> getAllAppointments();

    List<AppointmentResponseModel> getAllAppointmentsByCustomerId(String customerId);
    AppointmentResponseModel updateAppointmentStatus(String appointmentId, boolean isConfirm);

<<<<<<< HEAD
    AppointmentResponseModel getAppointmentById(String appointmentId);

=======
    // delete all cancelled
>>>>>>> eddc6f1 (setting up canceling appointment methods in serviceImp and repository)
    void deleteAllCancelledAppointments();
}
