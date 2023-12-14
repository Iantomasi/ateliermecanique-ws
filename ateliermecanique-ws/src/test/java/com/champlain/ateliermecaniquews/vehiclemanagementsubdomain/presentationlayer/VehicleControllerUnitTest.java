package com.champlain.ateliermecaniquews.vehiclemanagementsubdomain.presentationlayer;

import com.champlain.ateliermecaniquews.vehiclemanagementsubdomain.businesslayer.VehicleService;
import com.champlain.ateliermecaniquews.vehiclemanagementsubdomain.datalayer.TransmissionType;
import com.champlain.ateliermecaniquews.vehiclemanagementsubdomain.datalayer.Vehicle;
import com.champlain.ateliermecaniquews.vehiclemanagementsubdomain.presentationlayer.VehicleResponseModel;
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
                new VehicleResponseModel("1", "Honda", "Civic", "2020", "Automatic", TransmissionType.MANUAL, "1234567890"),
                new VehicleResponseModel("2", "Toyota", "Corolla", "2019", "Manual", TransmissionType.AUTOMATIC, "9876543210")
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
