package com.champlain.ateliermecaniquews.appointmentmanagementsubdomain.datalayer;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer>{

    Appointment findAppointmentByAppointmentIdentifier_AppointmentId(String appointmentId);
    Appointment findAppointmentByCustomerIdAndAppointmentIdentifier_AppointmentId(String customerId, String appointmentId);

}
