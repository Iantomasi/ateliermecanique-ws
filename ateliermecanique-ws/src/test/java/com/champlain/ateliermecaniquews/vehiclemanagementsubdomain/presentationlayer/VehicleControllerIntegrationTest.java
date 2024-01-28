package com.champlain.ateliermecaniquews.vehiclemanagementsubdomain.presentationlayer;

import com.champlain.ateliermecaniquews.vehiclemanagementsubdomain.businesslayer.VehicleService;
import com.champlain.ateliermecaniquews.vehiclemanagementsubdomain.datalayer.TransmissionType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class VehicleControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VehicleService vehicleService;

    @Test
    @WithMockUser(roles = "ADMIN")
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
    @WithMockUser(roles = "ADMIN")
    void getAllVehiclesByCustomerId_noVehicles_shouldReturnNotFound() throws Exception {
        String customerId = "someCustomerId";
        when(vehicleService.getAllVehiclesByCustomerId(customerId)).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/v1/customers/{customerId}/vehicles", customerId))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
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
    @WithMockUser(roles = "ADMIN")
    void getVehicleByInvalidVehicleId_shouldReturnNotFound() throws Exception {
        String vehicleId = "invalidVehicleId";
        String customerId = "someCustomerId";

        when(vehicleService.getVehicleByVehicleId(customerId, vehicleId)).thenReturn(null);

        mockMvc.perform(get("/api/v1/customers/{customerId}/vehicles/{vehicleId}", customerId, vehicleId))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void addVehicleToCustomerAccount_shouldSucceed() throws Exception {
        String customerId = "validCustomerId";
        VehicleRequestModel requestModel = new VehicleRequestModel(customerId, "Tesla", "Model 3", "2021", TransmissionType.AUTOMATIC, "10000");
        VehicleResponseModel responseModel = new VehicleResponseModel("mockVehicleId", customerId, "Tesla", "Model 3", "2021", TransmissionType.AUTOMATIC, "10000");

        when(vehicleService.addVehicleToCustomerAccount(customerId, requestModel)).thenReturn(responseModel);

        mockMvc.perform(post("/api/v1/customers/{customerId}/vehicles", customerId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(requestModel)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vehicleId").value("mockVehicleId"))
                .andExpect(jsonPath("$.make").value("Tesla"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void addVehicleToInvalidCustomerAccount_shouldReturnNotFound() throws Exception {
        String invalidCustomerId = "invalidCustomerId";
        VehicleRequestModel requestModel = new VehicleRequestModel(invalidCustomerId, "Tesla", "Model 3", "2021", TransmissionType.AUTOMATIC, "10000");

        when(vehicleService.addVehicleToCustomerAccount(invalidCustomerId, requestModel)).thenReturn(null);

        mockMvc.perform(post("/api/v1/customers/{invalidCustomerId}/vehicles", invalidCustomerId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(requestModel)))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
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
    @WithMockUser(roles = "ADMIN")
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
    @WithMockUser(roles = "ADMIN")
    void deleteVehicleByVehicleId_shouldSucceed() throws Exception {
        String customerId = "existingCustomerId";
        String vehicleId = "existingVehicleId";

        doNothing().when(vehicleService).deleteVehicleByVehicleId(customerId, vehicleId);

        mockMvc.perform(delete("/api/v1/customers/{customerId}/vehicles/{vehicleId}", customerId, vehicleId))
                .andExpect(status().isNoContent());

        verify(vehicleService).deleteVehicleByVehicleId(customerId, vehicleId);
    }

}
