package com.champlain.ateliermecaniquews.appointmentmanagementsubdomain.presentationlayer;

import com.champlain.ateliermecaniquews.appointmentmanagementsubdomain.businesslayer.AppointmentService;

import com.champlain.ateliermecaniquews.appointmentmanagementsubdomain.datalayer.Appointment;
import com.champlain.ateliermecaniquews.appointmentmanagementsubdomain.datalayer.AppointmentIdentifier;
import com.champlain.ateliermecaniquews.appointmentmanagementsubdomain.datalayer.AppointmentRepository;
import com.champlain.ateliermecaniquews.appointmentmanagementsubdomain.datalayer.Status;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyString;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
class AppointmentControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AppointmentService appointmentService;

    @Autowired
    private AppointmentRepository appointmentRepository;

    private Appointment testAppointment ;
    private String testAppointmentId;

    @BeforeEach
    void setUp() {
        // Set up data before each test
        AppointmentIdentifier identifier = new AppointmentIdentifier(); // Create a UUID
        testAppointment = new Appointment(
                "b7024d89-1a5e-4517-3gba-05178u7ar260",
                "132b41b2-2bec-4b98-b08d-c7c0e03fe33e",
                "2024-03-24 11:00",
                "Preventive Maintenance",
                "None",
                Status.PENDING
        );
        testAppointment.setAppointmentIdentifier(identifier);
        Appointment savedAppointment = appointmentRepository.save(testAppointment);
        testAppointmentId = savedAppointment.getAppointmentIdentifier().getAppointmentId(); // Get the UUID
    }

    @AfterEach
    void tearDown() {
        appointmentRepository.deleteAll();
    }

    @Test
    void getAllAppointments_shouldSucceed() throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse("2024-03-24 11:00", formatter);
        // Arrange - Mock the service layer response
        when(appointmentService.getAllAppointments())
                .thenReturn(Collections.singletonList(
                        new AppointmentResponseModel(testAppointmentId, "customerId", "vehicleId", dateTime , "Preventive Maintenance", "None", Status.PENDING)));

        // Act & Assert
        mockMvc.perform(get("/api/v1/appointments"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].services").value("Preventive Maintenance"));
    }
    @Test
    void getAppointmentByAppointmentId_shouldSucceed() throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse("2024-03-24 11:00", formatter);
        when(appointmentService.getAppointmentByAppointmentId(testAppointmentId)).thenReturn(
                new AppointmentResponseModel(testAppointmentId, "customerId", "vehicleId", dateTime , "Preventive Maintenance", "None", Status.PENDING));

        mockMvc.perform(get("/api/v1/appointments/{appointmentId}", testAppointmentId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.appointmentId").value(testAppointmentId));;
    }
    
    @Test
    void updateAppointmentStatusAdmin_shouldUpdateStatus() throws Exception {
        // Mock the service layer response
        when(appointmentService.updateAppointmentStatus(anyString(), anyBoolean()))
                .thenReturn(new AppointmentResponseModel(testAppointmentId, null, null, null, null, null, Status.CONFIRMED));

        mockMvc.perform(put("/api/v1/appointments/{appointmentId}/updateStatus?isConfirm=true", testAppointmentId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("CONFIRMED"));
    }

    // Test for customer updating appointment status
    @Test
    void updateAppointmentStatusCustomer_shouldUpdateStatus() throws Exception {
        // Mock the service layer response
        when(appointmentService.updateAppointmentStatus(anyString(), anyBoolean()))
                .thenReturn(new AppointmentResponseModel(testAppointmentId, null, null, null, null, null, Status.CANCELLED));

        mockMvc.perform(put("/api/v1/customers/{customerId}/appointments/{appointmentId}/updateStatus?isConfirm=false", "customer123", testAppointmentId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("CANCELLED"));
    }

    // Test for admin updating appointment status when appointment not found
    @Test
    void updateAppointmentStatusAdmin_appointmentNotFound() throws Exception {
        // Mock the service layer response for not found
        when(appointmentService.updateAppointmentStatus(anyString(), anyBoolean()))
                .thenReturn(null);

        mockMvc.perform(put("/api/v1/appointments/{appointmentId}/updateStatus?isConfirm=true", "nonExistingAppointmentId"))
                .andExpect(status().isNotFound());
    }

    // Test for customer updating appointment status when appointment not found
    @Test
    void updateAppointmentStatusCustomer_appointmentNotFound() throws Exception {
        // Mock the service layer response for not found
        when(appointmentService.updateAppointmentStatus(anyString(), anyBoolean()))
                .thenReturn(null);

        mockMvc.perform(put("/api/v1/customers/{customerId}/appointments/{appointmentId}/updateStatus?isConfirm=false", "customer123", "nonExistingAppointmentId"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getAllAppointmentsByCustomerId_shouldSucceed() throws Exception {
        // Mock the service layer response for customer appointments
        when(appointmentService.getAllAppointmentsByCustomerId(anyString()))
                .thenReturn(Collections.singletonList(new AppointmentResponseModel(testAppointmentId, "customerId", "vehicleId", null, "Oil Change", "1", Status.PENDING)));

        mockMvc.perform(get("/api/v1/customers/{customerId}/appointments", "customerId"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].services").value("Oil Change"));
    }

    @Test
    void getAllAppointmentsByCustomerId_notFound() throws Exception {
        // Mock the service layer response for no appointments
        when(appointmentService.getAllAppointmentsByCustomerId(anyString()))
                .thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/v1/customers/{customerId}/appointments", "customerId"))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteAppointmentByAppointmentId_shouldSucceed() throws Exception {
        // Arrange
        String appointmentId = testAppointmentId;
        doNothing().when(appointmentService).deleteAppointmentByAppointmentId(appointmentId);

        // Act & Assert
        mockMvc.perform(delete("/api/v1/appointments/{appointmentId}", appointmentId))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteAppointmentByAppointmentId_notFound() throws Exception {
        // Arrange
        String nonExistentAppointmentId = "nonExistentAppointmentId";
        doThrow(new NotFoundException("Appointment not found")).when(appointmentService).deleteAppointmentByAppointmentId(nonExistentAppointmentId);

        // Act & Assert
        mockMvc.perform(delete("/api/v1/appointments/{appointmentId}", nonExistentAppointmentId))
                .andExpect(status().isNotFound());
    }


    @Test
    void deleteAllCancelledAppointments_shouldSucceed() throws Exception {
        // Arrange
        doNothing().when(appointmentService).deleteAllCancelledAppointments();

        // Act & Assert
        mockMvc.perform(delete("/api/v1/appointments/cancelled"))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteAllCancelledAppointments_exceptionThrown_shouldReturnInternalServerError() throws Exception {
        // Arrange
        doThrow(new RuntimeException("Internal Server Error")).when(appointmentService).deleteAllCancelledAppointments();

        // Act & Assert
        mockMvc.perform(delete("/api/v1/appointments/cancelled"))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void updateAppointmentByAppointmentId_shouldSucceed() throws Exception {
        // Arrange
        String appointmentId = testAppointmentId;
        String requestJson = """
            {
                "customerId": "testCustomerId",
                "vehicleId": "testVehicleId",
                "appointmentDate": "2024-03-24T11:00",
                "services": "Test Service",
                "comments": "No comments",
                "status": "PENDING"
            }""";

        AppointmentResponseModel responseModel = AppointmentResponseModel.builder()
                .appointmentId(appointmentId)
                .customerId("testCustomerId")
                .vehicleId("testVehicleId")
                .appointmentDate(LocalDateTime.parse("2024-03-24T11:00"))
                .services("Test Service")
                .comments("No comments")
                .status(Status.PENDING)
                .build();

        when(appointmentService.updateAppointmentByAppointmentId(any(AppointmentRequestModel.class), eq(appointmentId)))
                .thenReturn(responseModel);

        // Act & Assert
        mockMvc.perform(put("/api/v1/appointments/{appointmentId}", appointmentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.services", is("Test Service")));
    }

    @Test
    void updateAppointmentByAppointmentId_whenNotFound_shouldReturnNotFound() throws Exception {
        // Arrange
        String nonExistentAppointmentId = "nonExistentAppointmentId";
        String requestJson = """
            {
                "customerId": "testCustomerId",
                "vehicleId": "testVehicleId",
                "appointmentDate": "2024-03-24T11:00",
                "services": "Test Service",
                "comments": "No comments",
                "status": "PENDING"
            }""";

        when(appointmentService.updateAppointmentByAppointmentId(any(AppointmentRequestModel.class), eq(nonExistentAppointmentId)))
                .thenReturn(null);

        // Act & Assert
        mockMvc.perform(put("/api/v1/appointments/{appointmentId}", nonExistentAppointmentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isNotFound());
    }




}