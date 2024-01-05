package com.champlain.ateliermecaniquews.appointmentmanagementsubdomain.datalayer;

import jakarta.persistence.Embeddable;

@Embeddable
public class AppointmentIdentifier {

    private String appointmentId;

    public AppointmentIdentifier(){
        this.appointmentId = java.util.UUID.randomUUID().toString();
    }

    public String getAppointmentId() {
        return appointmentId;
    }

}
