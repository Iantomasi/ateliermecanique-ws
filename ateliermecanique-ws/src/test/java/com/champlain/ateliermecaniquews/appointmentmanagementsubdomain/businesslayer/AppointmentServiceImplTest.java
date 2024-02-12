package com.champlain.ateliermecaniquews.appointmentmanagementsubdomain.businesslayer;

import com.champlain.ateliermecaniquews.appointmentmanagementsubdomain.datalayer.Appointment;
import com.champlain.ateliermecaniquews.appointmentmanagementsubdomain.datalayer.AppointmentRepository;
import com.champlain.ateliermecaniquews.appointmentmanagementsubdomain.datalayer.Status;
import com.champlain.ateliermecaniquews.appointmentmanagementsubdomain.datamapperlayer.AppointmentResponseMapper;
import com.champlain.ateliermecaniquews.appointmentmanagementsubdomain.presentationlayer.AppointmentRequestModel;
import com.champlain.ateliermecaniquews.appointmentmanagementsubdomain.presentationlayer.AppointmentResponseModel;
import com.champlain.ateliermecaniquews.authenticationsubdomain.dataLayer.User;
import com.champlain.ateliermecaniquews.authenticationsubdomain.dataLayer.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

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
    private UserRepository userRepository;

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
    void getAppointmentByAppointmentId_shouldSucceed() {
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

        when(appointmentRepository.findAppointmentByAppointmentIdentifier_AppointmentId(appointmentId)).thenReturn(appointment);
        when(appointmentResponseMapper.entityToResponseModel(appointment)).thenReturn(expectedResponse);

        // Act
        AppointmentResponseModel result = appointmentService.getAppointmentByAppointmentId(appointmentId);

        // Assert
        assertNotNull(result);
        assertEquals(expectedResponse.getAppointmentId(), result.getAppointmentId());
        assertEquals(expectedResponse.getCustomerId(), result.getCustomerId());

        verify(appointmentRepository, times(1)).findAppointmentByAppointmentIdentifier_AppointmentId(appointmentId);
        verify(appointmentResponseMapper, times(1)).entityToResponseModel(appointment);
    }

    @Test
    void getAppointmentByAppointmentId_whenNotFound_shouldReturnNull() {
        // Arrange
        String appointmentId = "non-existent-id";
        when(appointmentRepository.findAppointmentByAppointmentIdentifier_AppointmentId(appointmentId)).thenReturn(null);

        // Act
        AppointmentResponseModel result = appointmentService.getAppointmentByAppointmentId(appointmentId);

        // Assert
        assertNull(result);
        verify(appointmentRepository, times(1)).findAppointmentByAppointmentIdentifier_AppointmentId(appointmentId);
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
    void deleteAppointmentByAppointmentId_shouldSucceed(){
        // Arrange
        String appointmentId = "b7024d89-1a5e-4517-3gba-05178u7ar260";

        // Mock the appointment to be deleted
        Appointment appointmentToDelete = new Appointment(
                appointmentId,
                "132b41b2-2bec-4b98-b08d-c7c0e03fe33e",
                "2024-03-24 11:00",
                "Preventive Maintenance",
                "None",
                Status.PENDING
        );

        when(appointmentRepository.findAppointmentByAppointmentIdentifier_AppointmentId(appointmentId)).thenReturn(appointmentToDelete);

        // Act
        appointmentService.deleteAppointmentByAppointmentId(appointmentId);

        // Assert
        verify(appointmentRepository, times(1)).findAppointmentByAppointmentIdentifier_AppointmentId(appointmentId);
        verify(appointmentRepository, times(1)).delete(appointmentToDelete);
    }

    @Test
    void deleteAppointmentByAppointmentId_appointmentNotFound() {
        // Arrange
        String appointmentId = "non-existent-appointment-id";
        when(appointmentRepository.findAppointmentByAppointmentIdentifier_AppointmentId(appointmentId)).thenReturn(null);
        // Act
        appointmentService.deleteAppointmentByAppointmentId(appointmentId);
        // Assert
        verify(appointmentRepository, times(1)).findAppointmentByAppointmentIdentifier_AppointmentId(appointmentId);
        verify(appointmentRepository, never()).delete(any());
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
   @Test
    void addAppointmentToCustomerAccount_ShouldAddAppointment() {
        // Arrange
        String userId = "testCustomerId";
        LocalDateTime appointmentDateTime = LocalDateTime.now();
        AppointmentRequestModel requestModel = AppointmentRequestModel.builder()
                .customerId(userId)
                .vehicleId("testVehicleId")
                .appointmentDate(appointmentDateTime)
                .services("Test Service")
                .comments("No comments")
                .status(Status.PENDING)
                .build();

        User customerAccount = new User();

        when(userRepository.findUserByUserIdentifier_UserId(userId))
                .thenReturn(customerAccount);

        Appointment savedAppointment = new Appointment();
        // set properties of savedAppointment as needed

        when(appointmentRepository.save(any(Appointment.class)))
                .thenReturn(savedAppointment);

        AppointmentResponseModel expectedResponse = AppointmentResponseModel.builder()
                .appointmentId("1")
                .customerId(userId)
                .vehicleId("testVehicleId")
                .appointmentDate(appointmentDateTime)
                .services("Test Service")
                .comments("No comments")
                .status(Status.PENDING)
                .build();

        when(appointmentResponseMapper.entityToResponseModel(savedAppointment))
                .thenReturn(expectedResponse);

        // Act
        AppointmentResponseModel result = appointmentService.addAppointmentToCustomerAccount(userId, requestModel);

        // Assert
        assertNotNull(result);
        assertEquals(expectedResponse.getCustomerId(), result.getCustomerId());
        assertEquals(expectedResponse.getServices(), result.getServices());
        // Additional assertions as needed

        verify(userRepository, times(1)).findUserByUserIdentifier_UserId(userId);
        verify(appointmentRepository, times(1)).save(any(Appointment.class));
    }

    @Test
    void updateAppointmentByAppointmentId_shouldUpdateSuccessfully() {
        // Arrange
        String appointmentId = "existingAppointmentId";
        LocalDateTime appointmentDateTime = LocalDateTime.now();
        AppointmentRequestModel requestModel = AppointmentRequestModel.builder()
                .customerId("testCustomerId")
                .vehicleId("testVehicleId")
                .appointmentDate(appointmentDateTime)
                .services("Test Service")
                .comments("No comments")
                .status(Status.PENDING)
                .build();

        Appointment existingAppointment = new Appointment();
        // Set properties of existingAppointment as needed

        when(appointmentRepository.findAppointmentByAppointmentIdentifier_AppointmentId(appointmentId))
                .thenReturn(existingAppointment);

        Appointment savedAppointment = new Appointment();


        when(appointmentRepository.save(any(Appointment.class)))
                .thenReturn(savedAppointment);

        AppointmentResponseModel expectedResponse = AppointmentResponseModel.builder()
                .appointmentId(appointmentId)
                .customerId(requestModel.getCustomerId())
                .vehicleId(requestModel.getVehicleId())
                .appointmentDate(appointmentDateTime)
                .services(requestModel.getServices())
                .comments(requestModel.getComments())
                .status(requestModel.getStatus())
                .build();

        when(appointmentResponseMapper.entityToResponseModel(savedAppointment))
                .thenReturn(expectedResponse);

// Act
        AppointmentResponseModel result = appointmentService.updateAppointmentByAppointmentId(requestModel, appointmentId);

// Assert
        assertNotNull(result);
        assertEquals(expectedResponse.getCustomerId(), result.getCustomerId());
        assertEquals(expectedResponse.getServices(), result.getServices());
        assertEquals(expectedResponse.getComments(), result.getComments());
        assertEquals(expectedResponse.getStatus(), result.getStatus());

        verify(appointmentRepository, times(1)).findAppointmentByAppointmentIdentifier_AppointmentId(appointmentId);
        verify(appointmentRepository, times(1)).save(existingAppointment);

    }


    @Test
    void updateAppointmentByAppointmentId_whenNotFound_shouldReturnNull() {
        // Arrange
        String appointmentId = "nonexistentAppointmentId";
        LocalDateTime appointmentDateTime = LocalDateTime.now();
        AppointmentRequestModel requestModel = AppointmentRequestModel.builder()
                .customerId("testCustomerId")
                .vehicleId("testVehicleId")
                .appointmentDate(appointmentDateTime)
                .services("Test Service")
                .comments("No comments")
                .status(Status.PENDING)
                .build();

        // Set properties of requestModel as needed

        when(appointmentRepository.findAppointmentByAppointmentIdentifier_AppointmentId(appointmentId))
                .thenReturn(null);

        // Act
        AppointmentResponseModel result = appointmentService.updateAppointmentByAppointmentId(requestModel, appointmentId);

        // Assert
        assertNull(result);
        verify(appointmentRepository, times(1)).findAppointmentByAppointmentIdentifier_AppointmentId(appointmentId);
        verify(appointmentRepository, never()).save(any(Appointment.class));
    }

    @Test
    void checkTimeSlotAvailability_ShouldReturnCorrectAvailability() {
        // Arrange
        LocalDate testDate = LocalDate.of(2024, 3, 24);
        LocalDateTime startOfDay = testDate.atStartOfDay();
        LocalDateTime endOfDay = testDate.plusDays(1).atStartOfDay();

        LocalDateTime appointmentDateTime1 = LocalDateTime.of(2024, 3, 24, 10, 0); // 10:00 AM
        LocalDateTime appointmentDateTime2 = LocalDateTime.of(2024, 3, 24, 12, 0); // 12:00 PM

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String formattedDateTime1 = startOfDay.plusHours(1).format(formatter);
        String formattedDateTime2 = startOfDay.plusHours(3).format(formatter);

        List<Appointment> appointments = Arrays.asList(
                new Appointment("customerId1", "vehicleId1", formattedDateTime1, "Service1", "None", Status.PENDING),
                new Appointment("customerId2", "vehicleId2", formattedDateTime2, "Service2", "Description2", Status.PENDING)
        );


        when(appointmentRepository.findAllByAppointmentDateBetween(startOfDay, endOfDay)).thenReturn(appointments);


        // Act
        Map<String, Boolean> result = appointmentService.checkTimeSlotAvailability(testDate);

        // Assert
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(true, result.get("10:00"));
        assertEquals(true, result.get("12:00"));
        assertEquals(true, result.get("09:00"));
        assertEquals(true, result.get("11:00"));
        assertEquals(true, result.get("13:00"));
        // ... Assert other time slots as needed

        verify(appointmentRepository, times(1)).findAllByAppointmentDateBetween(startOfDay, endOfDay);
    }










}
