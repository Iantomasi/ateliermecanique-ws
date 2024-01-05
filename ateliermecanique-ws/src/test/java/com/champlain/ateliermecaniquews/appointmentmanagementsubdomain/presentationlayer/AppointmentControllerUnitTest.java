package com.champlain.ateliermecaniquews.appointmentmanagementsubdomain.presentationlayer;

import com.champlain.ateliermecaniquews.appointmentmanagementsubdomain.businesslayer.AppointmentService;
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
}