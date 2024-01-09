package com.champlain.ateliermecaniquews.appointmentmanagementsubdomain.presentationlayer;

import com.champlain.ateliermecaniquews.appointmentmanagementsubdomain.businesslayer.AppointmentService;
import com.champlain.ateliermecaniquews.appointmentmanagementsubdomain.datalayer.Status;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AppointmentController.class)
class AppointmentControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;


    @MockBean
    private AppointmentService appointmentService;


    @Test
    void getAllAppointments_shouldSucceed() throws Exception {
        // Arrange
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        List<AppointmentResponseModel> appointments = List.of(
                AppointmentResponseModel.builder()
                        .appointmentId("1")
                        .customerId("1")
                        .vehicleId("1")
                        .appointmentDate(LocalDateTime.parse("2021-01-01 12:00", formatter))
                        .build(),
                AppointmentResponseModel.builder()
                        .appointmentId("2")
                        .customerId("2")
                        .vehicleId("2")
                        .appointmentDate(LocalDateTime.parse("2021-01-01 12:05", formatter))
                        .build()
        );

        when(appointmentService.getAllAppointments()).thenReturn(appointments);

        // Act & Assert
        mockMvc.perform(get("/api/v1/appointments"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void getAllAppointments_noAppointments_shouldReturnEmptyList() throws Exception {
        // Arrange
        when(appointmentService.getAllAppointments()).thenReturn(Collections.emptyList());

        // Act & Assert
        mockMvc.perform(get("/api/v1/appointments"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getAllAppointmentsByCustomerId_shouldSucceed() throws Exception {
        // Arrange
        String customerId = "customer123";
        List<AppointmentResponseModel> appointments = Arrays.asList(
                new AppointmentResponseModel("1", customerId, "vehicle1", LocalDateTime.now(), "Service1", "Comment1", Status.PENDING)
        );
        when(appointmentService.getAllAppointmentsByCustomerId(customerId)).thenReturn(appointments);

        // Act & Assert
        mockMvc.perform(get("/api/v1/customers/{customerId}/appointments", customerId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    void getAllAppointmentsByCustomerId_notFound() throws Exception {
        // Arrange
        String customerId = "customer123";
        when(appointmentService.getAllAppointmentsByCustomerId(customerId)).thenReturn(Collections.emptyList());

        // Act & Assert
        mockMvc.perform(get("/api/v1/customers/{customerId}/appointments", customerId))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateAppointmentStatusAdmin_shouldUpdateStatus() throws Exception {
        // Arrange
        String appointmentId = "1";
        boolean isConfirm = true;
        when(appointmentService.updateAppointmentStatusAdmin(appointmentId, isConfirm))
                .thenReturn(new AppointmentResponseModel(appointmentId, null, null, null, null, null, Status.CONFIRMED));

        // Act & Assert
        mockMvc.perform(put("/api/v1/appointments/{appointmentId}/updateStatus?isConfirm=true", appointmentId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("CONFIRMED"));
    }

    @Test
    void updateAppointmentStatusCustomer_shouldUpdateStatus() throws Exception {
        // Arrange
        String customerId = "customer123";
        String appointmentId = "1";
        boolean isConfirm = false;
        when(appointmentService.updateAppointmentStatusCustomer(customerId, appointmentId, isConfirm))
                .thenReturn(new AppointmentResponseModel(appointmentId, customerId, null, null, null, null, Status.CANCELLED));

        // Act & Assert
        mockMvc.perform(put("/api/v1/customers/{customerId}/appointments/{appointmentId}/updateStatus?isConfirm=false", customerId, appointmentId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("CANCELLED"));
    }
}
