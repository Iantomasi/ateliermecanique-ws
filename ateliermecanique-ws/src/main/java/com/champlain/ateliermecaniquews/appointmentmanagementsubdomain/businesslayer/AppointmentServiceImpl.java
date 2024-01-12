package com.champlain.ateliermecaniquews.appointmentmanagementsubdomain.businesslayer;

import com.champlain.ateliermecaniquews.appointmentmanagementsubdomain.datalayer.Appointment;
import com.champlain.ateliermecaniquews.appointmentmanagementsubdomain.datalayer.AppointmentRepository;
import com.champlain.ateliermecaniquews.appointmentmanagementsubdomain.datalayer.Status;
import com.champlain.ateliermecaniquews.appointmentmanagementsubdomain.datamapperlayer.AppointmentRequestMapper;
import com.champlain.ateliermecaniquews.appointmentmanagementsubdomain.datamapperlayer.AppointmentResponseMapper;
import com.champlain.ateliermecaniquews.appointmentmanagementsubdomain.presentationlayer.AppointmentResponseModel;
import com.champlain.ateliermecaniquews.customeraccountsmanagementsubdomain.datalayer.CustomerAccountRepository;
import com.champlain.ateliermecaniquews.vehiclemanagementsubdomain.datalayer.VehicleRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class AppointmentServiceImpl implements AppointmentService{

    private AppointmentRepository appointmentRepository;
    private CustomerAccountRepository customerAccountRepository;
    private VehicleRepository vehicleRepository;
    private AppointmentResponseMapper appointmentResponseMapper;
    private AppointmentRequestMapper appointmentRequestMapper;

    @Override
    public List<AppointmentResponseModel> getAllAppointments() {
        return appointmentResponseMapper.entityToResponseModelList(appointmentRepository.findAll());
    }

    @Override
    public List<AppointmentResponseModel> getAllAppointmentsByCustomerId(String customerId) {
        List<Appointment> appointments = appointmentRepository.findAllAppointmentsByCustomerId(customerId);
        log.info("Fetching appointments for customer ID: {}", customerId);

        // Check if the appointments list is empty or null
        if (appointments.isEmpty()) {
            log.warn("No appointments found for customer ID: {} (appointments list is empty)", customerId);
            return Collections.emptyList();
        } else {
            log.info("Number of appointments found for customer ID {}: {}", customerId, appointments.size());
        }
        List<AppointmentResponseModel> appointmentResponseModels = appointmentResponseMapper.entityToResponseModelList(appointments);
        if (appointmentResponseModels == null) {
            log.warn("AppointmentResponseModels is null after mapping");
        } else {
            log.info("Mapping successful. Number of AppointmentResponseModels: {}", appointmentResponseModels.size());
        }
        return appointmentResponseModels;
    }

    @Override
    public AppointmentResponseModel updateAppointmentStatus(String appointmentId, boolean isConfirm) {
        Appointment appointment = appointmentRepository.findAppointmentByAppointmentIdentifier_AppointmentId(appointmentId);

        // Set the status based on the button pressed in the frontend
        if (isConfirm) {
            appointment.setStatus(Status.CONFIRMED);
        } else {
            appointment.setStatus(Status.CANCELLED);
        }

        appointmentRepository.save(appointment);
        return appointmentResponseMapper.entityToResponseModel(appointment);
    }

    @Override

    public AppointmentResponseModel getAppointmentById(String appointmentId) {
        Appointment appointment = appointmentRepository.findByAppointmentIdentifier_AppointmentId(appointmentId);

        if (appointment == null) {
            log.warn("No appointment found for appointment ID: {}", appointmentId);
            return null;
        }

        return appointmentResponseMapper.entityToResponseModel(appointment);
    }

    @Override
    public void deleteAllCancelledAppointments() {
        List<Appointment> cancelledAppointments =  appointmentRepository.findAllAppointmentsByStatus(Status.CANCELLED);
        if(!cancelledAppointments.isEmpty()){
            appointmentRepository.deleteAll(cancelledAppointments);
        }

    }


}
