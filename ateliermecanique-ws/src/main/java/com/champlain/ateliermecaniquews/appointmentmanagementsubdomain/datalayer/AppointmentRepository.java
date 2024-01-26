package com.champlain.ateliermecaniquews.appointmentmanagementsubdomain.datalayer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;


public interface AppointmentRepository extends JpaRepository<Appointment, Integer>{

    Appointment findAppointmentByAppointmentIdentifier_AppointmentId(String appointmentId);
    List<Appointment> findAllAppointmentsByCustomerId(String customerId);
    List<Appointment> findAllAppointmentsByStatus(Status status);
    List<Appointment> findAllByAppointmentDateBetween(LocalDateTime startDateTime, LocalDateTime endDateTime);

}
