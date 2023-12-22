package com.champlain.ateliermecaniquews.vehiclemanagementsubdomain.presentationlayer;

import com.champlain.ateliermecaniquews.vehiclemanagementsubdomain.businesslayer.VehicleService;
import com.champlain.ateliermecaniquews.vehiclemanagementsubdomain.datalayer.TransmissionType;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    void getAllVehiclesByCustomerId_shouldSucceed() throws Exception {
        String customerId = "someCustomerId";
        List<VehicleResponseModel> vehicles = Arrays.asList(
                new VehicleResponseModel("1", "someCustomerId", "Honda", "Civic", "2020", TransmissionType.MANUAL, "1234567890"),
                new VehicleResponseModel("2", "someCustomerId", "Honda", "Civic", "2020", TransmissionType.MANUAL, "1234567890")
        );

        when(vehicleService.getAllVehiclesByCustomerId(customerId)).thenReturn(vehicles);

        mockMvc.perform(get("/api/v1/customers/{customerId}/vehicles", customerId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(vehicles.size())))
                .andExpect(jsonPath("$[0].vehicleId").value("1"))
                .andExpect(jsonPath("$[1].vehicleId").value("2"));
    }

    @Test
    void getAllVehiclesByCustomerId_noVehicles_shouldReturnNotFound() throws Exception {
        String customerId = "someCustomerId";
        when(vehicleService.getAllVehiclesByCustomerId(customerId)).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/v1/customers/{customerId}/vehicles", customerId))
                .andExpect(status().isNotFound());
    }


    @Test
    void getVehicleByVehicleId_shouldSucceed() throws Exception {
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
    void getVehicleByInvalidVehicleId_shouldReturnNotFound() throws Exception {
        String vehicleId = "invalidVehicleId";
        String customerId = "someCustomerId";

        when(vehicleService.getVehicleByVehicleId(customerId, vehicleId)).thenReturn(null);

        mockMvc.perform(get("/api/v1/customers/{customerId}/vehicles/{vehicleId}", customerId, vehicleId))
                .andExpect(status().isNotFound());
    }

    @Test
    void addVehicleToCustomerAccount_shouldSucceed() {
        // Arrange
        VehicleService vehicleService = mock(VehicleService.class);
        VehicleController vehicleController = new VehicleController(vehicleService);
        String validCustomerId = "validCustomerId";
        String mockVehicleId = "mockVehicleId";
        VehicleRequestModel requestModel = new VehicleRequestModel(validCustomerId, "Tesla", "Model 3", "2021", TransmissionType.AUTOMATIC, "10000");
        VehicleResponseModel responseModel = new VehicleResponseModel(mockVehicleId, validCustomerId, "Tesla", "Model 3", "2021", TransmissionType.AUTOMATIC, "10000");
        when(vehicleService.addVehicleToCustomerAccount(validCustomerId, requestModel)).thenReturn(responseModel);

        // Act
        ResponseEntity<VehicleResponseModel> responseEntity = vehicleController.addVehicleToCustomerAccount(validCustomerId, requestModel);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(responseModel, responseEntity.getBody());
    }

    @Test
    void addVehicleToInvalidCustomerAccount_shouldReturnNotFound() {
        // Arrange
        VehicleService vehicleService = mock(VehicleService.class);
        VehicleController vehicleController = new VehicleController(vehicleService);
        String invalidCustomerId = "invalidCustomerId";
        VehicleRequestModel requestModel = new VehicleRequestModel(invalidCustomerId, "Tesla", "Model 3", "2021", TransmissionType.AUTOMATIC, "10000");
        when(vehicleService.addVehicleToCustomerAccount(invalidCustomerId, requestModel)).thenReturn(null);

        // Act
        ResponseEntity<VehicleResponseModel> responseEntity = vehicleController.addVehicleToCustomerAccount(invalidCustomerId, requestModel);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }


    @Test
    void updateVehicleByVehicleId_shouldSucceed() throws Exception {
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
    void updateVehicleByInvalidVehicleId_shouldReturnNotFound() throws Exception {
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
    void deleteVehicleByVehicleId_shouldSucceed() {
        // Arrange
        VehicleController vehicleController = new VehicleController(vehicleService);
        String customerId = "existingCustomerId";
        String vehicleId = "existingVehicleId";

        doNothing().when(vehicleService).deleteVehicleByVehicleId(customerId, vehicleId);

        // Act
        vehicleController.deleteVehicleByVehicleId(customerId, vehicleId);

        // Assert
        verify(vehicleService).deleteVehicleByVehicleId(customerId, vehicleId);
    }

    @Test
    void deleteVehicleByInvalidVehicleId_shouldReturnNull() {
        // Arrange
        VehicleController vehicleController = new VehicleController(vehicleService);
        String customerId = "nonExistingCustomerId";
        String vehicleId = "nonExistingVehicleId";

        // Assuming your service returns null if vehicle is not found
        doNothing().when(vehicleService).deleteVehicleByVehicleId(customerId, vehicleId);

    }

}
