package com.champlain.ateliermecaniquews.appointmentmanagementsubdomain.businesslayer;

import com.champlain.ateliermecaniquews.appointmentmanagementsubdomain.datalayer.Appointment;
import com.champlain.ateliermecaniquews.appointmentmanagementsubdomain.datalayer.AppointmentRepository;
import com.champlain.ateliermecaniquews.appointmentmanagementsubdomain.datalayer.Status;
import com.champlain.ateliermecaniquews.appointmentmanagementsubdomain.datamapperlayer.AppointmentResponseMapper;
import com.champlain.ateliermecaniquews.appointmentmanagementsubdomain.presentationlayer.AppointmentResponseModel;
import com.champlain.ateliermecaniquews.customeraccountsmanagementsubdomain.datalayer.CustomerAccountRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;

@SpringBootTest
class AppointmentServiceImplTest {

    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
    private AppointmentResponseMapper appointmentResponseMapper;

    @Mock
    private CustomerAccountRepository customerAccountRepository;

    @InjectMocks
    private AppointmentServiceImpl appointmentService;

    @Captor
    private ArgumentCaptor<Appointment> appointmentArgumentCaptor;


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
    @Test
    void getAppointmentById_shouldReturnAppointment() {
        // Arrange
        String appointmentId = "b7024d89-1a5e-4517-3gba-05178u7ar260";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse("2024-03-24 11:00", formatter);

        Appointment appointment = new Appointment(
                appointmentId,
                "132b41b2-2bec-4b98-b08d-c7c0e03fe33e",
                "2024-03-24 11:00",
                "Preventive Maintenance",
                "None",
                Status.PENDING
        );

        AppointmentResponseModel expectedResponse = AppointmentResponseModel.builder()
                .appointmentId(appointmentId)
                .customerId(appointment.getCustomerId())
                .vehicleId(appointment.getVehicleId())
                .appointmentDate(dateTime)
                .services(appointment.getServices())
                .status(appointment.getStatus())
                .build();

        when(appointmentRepository.findByAppointmentIdentifier_AppointmentId(appointmentId)).thenReturn(appointment);
        when(appointmentResponseMapper.entityToResponseModel(appointment)).thenReturn(expectedResponse);

        // Act
        AppointmentResponseModel result = appointmentService.getAppointmentById(appointmentId);

        // Assert
        assertNotNull(result);
        assertEquals(expectedResponse.getAppointmentId(), result.getAppointmentId());
        assertEquals(expectedResponse.getCustomerId(), result.getCustomerId());

        verify(appointmentRepository, times(1)).findByAppointmentIdentifier_AppointmentId(appointmentId);
        verify(appointmentResponseMapper, times(1)).entityToResponseModel(appointment);
    }

    @Test
    void getAppointmentById_whenNotFound_shouldReturnNull() {
        // Arrange
        String appointmentId = "non-existent-id";
        when(appointmentRepository.findByAppointmentIdentifier_AppointmentId(appointmentId)).thenReturn(null);

        // Act
        AppointmentResponseModel result = appointmentService.getAppointmentById(appointmentId);

        // Assert
        assertNull(result);
        verify(appointmentRepository, times(1)).findByAppointmentIdentifier_AppointmentId(appointmentId);
        verify(appointmentResponseMapper, never()).entityToResponseModel(any(Appointment.class));
    }

    @Test
    void getAllAppointmentsByCustomerId_shouldReturnAppointmentResponseModelList() {
        // Arrange
        String customerId = "testCustomerId";
        Appointment appointment = new Appointment();
        List<Appointment> appointments = Arrays.asList(appointment);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse("2024-03-24 11:00", formatter);


        AppointmentResponseModel responseModel = new AppointmentResponseModel(
                "1",
                "testCustomerId",
                "testVehicleId",
                dateTime,
                "Oil Change",
                "1",
                Status.PENDING
        );

        when(appointmentRepository.findAllAppointmentsByCustomerId(customerId)).thenReturn(appointments);
        when(appointmentResponseMapper.entityToResponseModelList(appointments)).thenReturn(Arrays.asList(responseModel));

        // Act
        List<AppointmentResponseModel> result = appointmentService.getAllAppointmentsByCustomerId(customerId);

        // Assert
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals("Oil Change", result.get(0).getServices());
        verify(appointmentRepository).findAllAppointmentsByCustomerId(customerId);
        verify(appointmentResponseMapper).entityToResponseModelList(appointments);
    }

    @Test
    void getAllAppointmentsByCustomerId_noAppointments_shouldReturnEmptyList() {
        String customerId = "someCustomerId";
        when(appointmentRepository.findAllAppointmentsByCustomerId(customerId)).thenReturn(Collections.emptyList());

        List<AppointmentResponseModel> result = appointmentService.getAllAppointmentsByCustomerId(customerId);

        assertTrue(result.isEmpty());
        verify(appointmentRepository).findAllAppointmentsByCustomerId(customerId);
        verify(appointmentResponseMapper, never()).entityToResponseModelList(any());
    }

    @Test
    void getAllAppointmentsByCustomerId_shouldReturnNull() {
        String customerId = "testCustomerId";
        List<Appointment> appointments = Arrays.asList(new Appointment());
        when(appointmentRepository.findAllAppointmentsByCustomerId(customerId)).thenReturn(appointments);
        when(appointmentResponseMapper.entityToResponseModelList(appointments)).thenReturn(null);

        List<AppointmentResponseModel> result = appointmentService.getAllAppointmentsByCustomerId(customerId);

        assertNull(result);
        verify(appointmentRepository).findAllAppointmentsByCustomerId(customerId);
        verify(appointmentResponseMapper).entityToResponseModelList(appointments);
    }

    @Test
    void updateAppointmentStatus_shouldConfirmAppointment() {
        // Arrange
        String appointmentId = "existingAppointmentId";
        boolean isConfirm = true;
        Status expectedStatus = Status.CONFIRMED;

        Appointment existingAppointment = new Appointment();
        existingAppointment.setStatus(Status.PENDING); // initial status

        when(appointmentRepository.findAppointmentByAppointmentIdentifier_AppointmentId(appointmentId))
                .thenReturn(existingAppointment);

        when(appointmentRepository.save(any(Appointment.class))).thenAnswer(invocation -> invocation.getArgument(0));

        when(appointmentResponseMapper.entityToResponseModel(any(Appointment.class)))
                .thenAnswer(invocation -> new AppointmentResponseModel(appointmentId, null, null, null, null, null, expectedStatus));

        // Act
        AppointmentResponseModel result = appointmentService.updateAppointmentStatus(appointmentId, isConfirm);

        // Assert
        assertNotNull(result);
        assertEquals(expectedStatus, result.getStatus());
    }

    @Test
    void updateAppointmentStatus_shouldCancelAppointment() {
        // Arrange
        String appointmentId = "existingAppointmentId";
        boolean isConfirm = false;
        Status expectedStatus = Status.CANCELLED;

        Appointment existingAppointment = new Appointment();
        existingAppointment.setStatus(Status.PENDING); // initial status

        when(appointmentRepository.findAppointmentByAppointmentIdentifier_AppointmentId(appointmentId))
                .thenReturn(existingAppointment);

        when(appointmentRepository.save(any(Appointment.class))).thenAnswer(invocation -> invocation.getArgument(0));

        when(appointmentResponseMapper.entityToResponseModel(any(Appointment.class)))
                .thenAnswer(invocation -> new AppointmentResponseModel(appointmentId, null, null, null, null, null, expectedStatus));

        // Act
        AppointmentResponseModel result = appointmentService.updateAppointmentStatus(appointmentId, isConfirm);

        // Assert
        assertNotNull(result);
        assertEquals(expectedStatus, result.getStatus());
    }

    @Test
    void updateAppointmentStatus_saveFails_shouldThrowException() {
        // Arrange
        String appointmentId = "existingAppointmentId";
        boolean isConfirm = true;
        Appointment existingAppointment = new Appointment();
        existingAppointment.setStatus(Status.PENDING);

        when(appointmentRepository.findAppointmentByAppointmentIdentifier_AppointmentId(appointmentId))
                .thenReturn(existingAppointment);

        // Simulate save method throwing an exception
        when(appointmentRepository.save(any(Appointment.class)))
                .thenThrow(new RuntimeException("Database error"));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            appointmentService.updateAppointmentStatus(appointmentId, isConfirm);
        });
    }


    @Test
    void deleteAllCancelledAppointments_shouldSucceed() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse("2024-03-24 11:00", formatter);

        Appointment appointment = new Appointment(
                "1",
                "132b41b2-2bec-4b98-b08d-c7c0e03fe33e",
                "2024-03-24 11:00",
                "Preventive Maintenance",
                "None",
                Status.PENDING
        );
        // Arrange
        List<Appointment> cancelledAppointments = Arrays.asList(
                new Appointment( "customerId1", "vehicleId1", "2024-03-24 11:00", "Service1", "None", Status.CANCELLED),
                new Appointment( "customerId2", "vehicleId2", "2024-03-25 14:30", "Service2", "Description2", Status.CANCELLED)
        );

        when(appointmentRepository.findAllAppointmentsByStatus(Status.CANCELLED)).thenReturn(cancelledAppointments);

        // Act
        appointmentService.deleteAllCancelledAppointments();

        // Assert
        verify(appointmentRepository, times(1)).deleteAll(cancelledAppointments);
    }

    @Test
    void deleteAllCancelledAppointments_noCancelledAppointments_shouldNotDelete() {
        // Arrange
        when(appointmentRepository.findAllAppointmentsByStatus(Status.CANCELLED)).thenReturn(Collections.emptyList());

        // Act
        appointmentService.deleteAllCancelledAppointments();

        // Assert
        verify(appointmentRepository, never()).deleteAll(anyList());
    }


}
