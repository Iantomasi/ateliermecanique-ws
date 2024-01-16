package com.champlain.ateliermecaniquews.appointmentmanagementsubdomain.presentationlayer;

import com.champlain.ateliermecaniquews.appointmentmanagementsubdomain.businesslayer.AppointmentService;
import com.champlain.ateliermecaniquews.appointmentmanagementsubdomain.datalayer.Status;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.NotFoundException;
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
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AppointmentController.class)
class AppointmentControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;



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
        when(appointmentService.updateAppointmentStatus(appointmentId, isConfirm))
                .thenReturn(new AppointmentResponseModel(appointmentId, null, null, null, null, null, Status.CONFIRMED));

        // Act & Assert
        mockMvc.perform(put("/api/v1/appointments/{appointmentId}/updateStatus?isConfirm=true", appointmentId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("CONFIRMED"));
    }

    @Test
    void updateAppointmentStatusCustomer_shouldUpdateStatus() throws Exception {
        // Arrange
        String appointmentId = "1";
        String customerId = "customer123";
        boolean isConfirm = false;
        when(appointmentService.updateAppointmentStatus(appointmentId, isConfirm))
                .thenReturn(new AppointmentResponseModel(appointmentId, customerId, null, null, null, null, Status.CANCELLED));

        // Act & Assert
        mockMvc.perform(put("/api/v1/customers/{customerId}/appointments/{appointmentId}/updateStatus?isConfirm=false", customerId, appointmentId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("CANCELLED"));
    }

    @Test
    void getAppointmentById_shouldReturnAppointment() throws Exception {
        // Arrange
        String appointmentId = "1";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        AppointmentResponseModel appointment = AppointmentResponseModel.builder()
                .appointmentId(appointmentId)
                .customerId("1")
                .vehicleId("1")
                .appointmentDate(LocalDateTime.parse("2021-01-01 12:00", formatter))
                .build();

        when(appointmentService.getAppointmentByAppointmentId(appointmentId)).thenReturn(appointment);

        // Act & Assert
        mockMvc.perform(get("/api/v1/appointments/{appointmentId}", appointmentId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.appointmentId", is(appointmentId)));
    }

    @Test
    void getAppointmentById_notFound_shouldReturnNotFound() throws Exception {
        // Arrange
        String appointmentId = "non-existent-id";
        when(appointmentService.getAppointmentByAppointmentId(appointmentId)).thenReturn(null);

        // Act & Assert
        mockMvc.perform(get("/api/v1/appointments/{appointmentId}", appointmentId))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteAppointmentByAppointmentId_shouldSucceed() throws Exception {
        // Arrange
        String appointmentId = "validAppointmentId";
        doNothing().when(appointmentService).deleteAppointmentByAppointmentId(appointmentId);

        // Act & Assert
        mockMvc.perform(delete("/api/v1/appointments/{appointmentId}", appointmentId))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteAppointmentByAppointmentId_notFound() throws Exception {
        // Arrange
        String appointmentId = "nonExistentAppointmentId";
        doThrow(new NotFoundException("Appointment not found")).when(appointmentService).deleteAppointmentByAppointmentId(appointmentId);

        // Act & Assert
        mockMvc.perform(delete("/api/v1/appointments/{appointmentId}", appointmentId))
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
    void deleteAllCancelledAppointments_exceptionThrown_shouldReturnNoContent() throws Exception {
        // Arrange
        doThrow(new RuntimeException("Internal Server Error")).when(appointmentService).deleteAllCancelledAppointments();

        // Act & Assert
        mockMvc.perform(delete("/api/v1/appointments/cancelled"))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void addAppointmentToCustomerAccount_shouldSucceed() throws Exception {
        // Arrange
        String customerId = "testCustomerId";
        LocalDateTime appointmentDateTime = LocalDateTime.now();
        AppointmentRequestModel requestModel = AppointmentRequestModel.builder()
                .customerId(customerId)
                .vehicleId("testVehicleId")
                .appointmentDate(appointmentDateTime)
                .services("Test Service")
                .comments("No comments")
                .status(Status.PENDING)
                .build();

        AppointmentResponseModel responseModel = AppointmentResponseModel.builder()
                .appointmentId("1")
                .customerId(customerId)
                .vehicleId("testVehicleId")
                .appointmentDate(appointmentDateTime)
                .services("Test Service")
                .comments("No comments")
                .status(Status.PENDING)
                .build();

        when(appointmentService.addAppointmentToCustomerAccount(eq(customerId), any(AppointmentRequestModel.class)))
                .thenReturn(responseModel);

        String requestJson = objectMapper.writeValueAsString(requestModel);

        // Act & Assert
        mockMvc.perform(post("/api/v1/customers/{customerId}/appointments", customerId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk());
    }

    @Test
    void updateAppointmentByAppointmentId_shouldSucceed() throws Exception {
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

        AppointmentResponseModel responseModel = AppointmentResponseModel.builder()
                .appointmentId(appointmentId)
                .customerId("testCustomerId")
                .vehicleId("testVehicleId")
                .appointmentDate(appointmentDateTime)
                .services("Test Service")
                .comments("No comments")
                .status(Status.PENDING)
                .build();

        when(appointmentService.updateAppointmentByAppointmentId(any(AppointmentRequestModel.class), eq(appointmentId)))
                .thenReturn(responseModel);

        String requestJson = objectMapper.writeValueAsString(requestModel);

        // Act & Assert
        mockMvc.perform(put("/api/v1/appointments/{appointmentId}", appointmentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk());
    }

    @Test
    void updateAppointmentByAppointmentId_whenNotFound_shouldReturnNotFound() throws Exception {
        // Arrange
        String appointmentId = "nonExistentAppointmentId";
        LocalDateTime appointmentDateTime = LocalDateTime.now();
        AppointmentRequestModel requestModel = AppointmentRequestModel.builder()
                .customerId("testCustomerId")
                .vehicleId("testVehicleId")
                .appointmentDate(appointmentDateTime)
                .services("Test Service")
                .comments("No comments")
                .status(Status.PENDING)
                .build();

        when(appointmentService.updateAppointmentByAppointmentId(any(AppointmentRequestModel.class), eq(appointmentId)))
                .thenReturn(null);

        String requestJson = objectMapper.writeValueAsString(requestModel);

        // Act & Assert
        mockMvc.perform(put("/api/v1/appointments/{appointmentId}", appointmentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isNotFound());
    }


}




