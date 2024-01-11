package com.champlain.ateliermecaniquews.appointmentmanagementsubdomain.presentationlayer;

import com.champlain.ateliermecaniquews.appointmentmanagementsubdomain.businesslayer.AppointmentService;

import com.champlain.ateliermecaniquews.appointmentmanagementsubdomain.datalayer.Appointment;
import com.champlain.ateliermecaniquews.appointmentmanagementsubdomain.datalayer.AppointmentIdentifier;
import com.champlain.ateliermecaniquews.appointmentmanagementsubdomain.datalayer.AppointmentRepository;
import com.champlain.ateliermecaniquews.appointmentmanagementsubdomain.datalayer.Status;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
<<<<<<< HEAD
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
=======
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.is;
>>>>>>> 10fcf8b (All testing and front end done)
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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
    void getAppointmentById_shouldReturnAppointment() throws Exception {
        mockMvc.perform(get("/api/v1/appointments/{appointmentId}", testAppointmentId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.appointmentId", is(testAppointmentId)))
                .andExpect(jsonPath("$.services", is("Preventive Maintenance")));
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


}