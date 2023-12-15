package com.champlain.ateliermecaniquews.vehiclemanagementsubdomain.presentationlayer;

import com.champlain.ateliermecaniquews.vehiclemanagementsubdomain.businesslayer.VehicleService;
import com.champlain.ateliermecaniquews.vehiclemanagementsubdomain.datalayer.TransmissionType;
import com.champlain.ateliermecaniquews.vehiclemanagementsubdomain.datalayer.Vehicle;
import com.champlain.ateliermecaniquews.vehiclemanagementsubdomain.presentationlayer.VehicleResponseModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(VehicleController.class)
class VehicleControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VehicleService vehicleService;

    @Test
    void getAllVehiclesForCustomer_withVehicles_shouldReturnOk() throws Exception {
        String customerId = "someCustomerId";
        List<VehicleResponseModel> vehicles = Arrays.asList(
                new VehicleResponseModel("1", "someCustomerId", "Honda", "Civic", "2020", TransmissionType.MANUAL, "1234567890"),
                new VehicleResponseModel("2", "someCustomerId", "Honda", "Civic", "2020", TransmissionType.MANUAL, "1234567890")
        );

        when(vehicleService.getAllVehiclesForCustomer(customerId)).thenReturn(vehicles);

        mockMvc.perform(get("/api/v1/customers/{customerId}/vehicles", customerId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(vehicles.size())))
                .andExpect(jsonPath("$[0].vehicleId").value("1"))
                .andExpect(jsonPath("$[1].vehicleId").value("2"));
    }

    @Test
    void getAllVehiclesForCustomer_noVehicles_shouldReturnNotFound() throws Exception {
        String customerId = "someCustomerId";
        when(vehicleService.getAllVehiclesForCustomer(customerId)).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/v1/customers/{customerId}/vehicles", customerId))
                .andExpect(status().isNotFound());
    }


    @Test
    void getVehicleById_validId_shouldReturnOk() throws Exception {
        String vehicleId = "validVehicleId";
        String customerId = "someCustomerId";
        VehicleResponseModel vehicle = new VehicleResponseModel(vehicleId, customerId, "Toyota", "Camry", "2019", TransmissionType.AUTOMATIC, "9876543210");

        when(vehicleService.getVehicleByVehicleId(customerId, vehicleId)).thenReturn(vehicle);

        mockMvc.perform(get("/api/v1/customers/{customerId}/vehicles/{vehicleId}", customerId, vehicleId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.vehicleId").value(vehicleId));
    }

    @Test
    void getVehicleById_invalidId_shouldReturnNotFound() throws Exception {
        String vehicleId = "invalidVehicleId";
        String customerId = "someCustomerId";

        when(vehicleService.getVehicleByVehicleId(customerId, vehicleId)).thenReturn(null);

        mockMvc.perform(get("/api/v1/customers/{customerId}/vehicles/{vehicleId}", customerId, vehicleId))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateVehicle_validData_shouldReturnOk() throws Exception {
        String vehicleId = "validVehicleId";
        String customerId = "someCustomerId";
        VehicleRequestModel vehicleRequestModel = new VehicleRequestModel( customerId, "Ford", "Fiesta", "2021", TransmissionType.AUTOMATIC, "1233211234");
        VehicleResponseModel updatedVehicle = new VehicleResponseModel(vehicleId, customerId, "Ford", "Fiesta", "2021", TransmissionType.AUTOMATIC, "1233211234");

        when(vehicleService.updateVehicleByVehicleId(vehicleRequestModel, customerId, vehicleId)).thenReturn(updatedVehicle);

        mockMvc.perform(put("/api/v1/customers/{customerId}/vehicles/{vehicleId}", customerId, vehicleId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(vehicleRequestModel)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vehicleId").value(vehicleId))
                .andExpect(jsonPath("$.make").value("Ford"));
    }

    @Test
    void updateVehicle_invalidData_shouldReturnNotFound() throws Exception {
        String vehicleId = "invalidVehicleId";
        String customerId = "someCustomerId";
        VehicleRequestModel invalidVehicleRequestModel = new VehicleRequestModel(customerId, "UnknownMake", "UnknownModel", "0000", TransmissionType.MANUAL, "0000000000");

        when(vehicleService.updateVehicleByVehicleId(invalidVehicleRequestModel, customerId, vehicleId)).thenReturn(null);

        mockMvc.perform(put("/api/v1/customers/{customerId}/vehicles/{vehicleId}", customerId, vehicleId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(invalidVehicleRequestModel)))
                .andExpect(status().isNotFound());
    }

    @Test
    void addVehicleToCustomer_invalidCustomerId_shouldReturnNotFound() {
        // Arrange
        VehicleService vehicleService = mock(VehicleService.class);
        VehicleController vehicleController = new VehicleController(vehicleService);
        String invalidCustomerId = "invalidCustomerId";
        VehicleRequestModel requestModel = new VehicleRequestModel(invalidCustomerId, "Tesla", "Model 3", "2021", TransmissionType.AUTOMATIC, "10000");
        when(vehicleService.addVehicleToCustomer(invalidCustomerId, requestModel)).thenReturn(null);

        // Act
        ResponseEntity<VehicleResponseModel> responseEntity = vehicleController.addVehicleToCustomer(invalidCustomerId, requestModel);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    void addVehicleToCustomer_validCustomerId_shouldReturnVehicleResponseModel() {
        // Arrange
        VehicleService vehicleService = mock(VehicleService.class);
        VehicleController vehicleController = new VehicleController(vehicleService);
        String validCustomerId = "validCustomerId";
        String mockVehicleId = "mockVehicleId";
        VehicleRequestModel requestModel = new VehicleRequestModel(validCustomerId, "Tesla", "Model 3", "2021", TransmissionType.AUTOMATIC, "10000");
        VehicleResponseModel responseModel = new VehicleResponseModel(mockVehicleId, validCustomerId, "Tesla", "Model 3", "2021", TransmissionType.AUTOMATIC, "10000");
        when(vehicleService.addVehicleToCustomer(validCustomerId, requestModel)).thenReturn(responseModel);

        // Act
        ResponseEntity<VehicleResponseModel> responseEntity = vehicleController.addVehicleToCustomer(validCustomerId, requestModel);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(responseModel, responseEntity.getBody());
    }
}