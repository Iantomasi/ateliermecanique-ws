package com.champlain.ateliermecaniquews.appointmentmanagementsubdomain.datalayer;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer>{

    Appointment findAppointmentByAppointmentIdentifier_AppointmentId(String appointmentId);
    List<Appointment> findAllAppointmentsByCustomerId(String customerId);
    Appointment findByAppointmentIdentifier_AppointmentId(String appointmentId);

    List<Appointment> findAllAppointmentsByStatus(Status status);


    List<Appointment> findAllAppointmentsByStatus(Status status);

}
