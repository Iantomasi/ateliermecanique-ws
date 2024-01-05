package com.champlain.ateliermecaniquews.appointmentmanagementsubdomain.businesslayer;

import com.champlain.ateliermecaniquews.appointmentmanagementsubdomain.datalayer.Appointment;
import com.champlain.ateliermecaniquews.appointmentmanagementsubdomain.datalayer.AppointmentRepository;
import com.champlain.ateliermecaniquews.appointmentmanagementsubdomain.datalayer.Status;
import com.champlain.ateliermecaniquews.appointmentmanagementsubdomain.datamapperlayer.AppointmentResponseMapper;
import com.champlain.ateliermecaniquews.appointmentmanagementsubdomain.presentationlayer.AppointmentRequestModel;
import com.champlain.ateliermecaniquews.appointmentmanagementsubdomain.presentationlayer.AppointmentResponseModel;
import com.champlain.ateliermecaniquews.customeraccountsmanagementsubdomain.datalayer.CustomerAccount;
import com.champlain.ateliermecaniquews.customeraccountsmanagementsubdomain.presentationlayer.CustomerAccountResponseModel;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class AppointmentServiceImplTest {

    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
    private AppointmentResponseMapper appointmentResponseMapper;

    @InjectMocks
    private AppointmentServiceImpl appointmentService;


    @Test
    void getAllAppointments_shouldSucceed() {
        // Arrange
        Appointment appointment = new Appointment(
                "b7024d89-1a5e-4517-3gba-05178u7ar260",
                "132b41b2-2bec-4b98-b08d-c7c0e03fe33e",
                "2024-03-24 11:00",
                "Preventive Maintenance",
                "None",
                 Status.PENDING
        );

        List<Appointment> appointments = Collections.singletonList(appointment);
        when(appointmentRepository.findAll()).thenReturn(appointments);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse("2024-03-24 11:00", formatter);

        AppointmentResponseModel responseModel = AppointmentResponseModel.builder()
                .appointmentId(appointment.getAppointmentIdentifier().getAppointmentId().toString())
                .customerId(appointment.getCustomerId())
                .vehicleId(appointment.getVehicleId())
                .appointmentDate(dateTime)
                .services(appointment.getServices())
                .status(appointment.getStatus())
                .build();

        List<AppointmentResponseModel> responseModels = Collections.singletonList(responseModel);

        when(appointmentResponseMapper.entityToResponseModelList(appointments)).thenReturn(responseModels);

        // Act
        List<AppointmentResponseModel> result = appointmentService.getAllAppointments();

        // Assert
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(responseModels.size(), result.size());
        assertEquals(responseModels.get(0).getAppointmentId(), result.get(0).getAppointmentId());

        verify(appointmentRepository, times(1)).findAll();
        verify(appointmentResponseMapper, times(1)).entityToResponseModelList(appointments);
    }

}