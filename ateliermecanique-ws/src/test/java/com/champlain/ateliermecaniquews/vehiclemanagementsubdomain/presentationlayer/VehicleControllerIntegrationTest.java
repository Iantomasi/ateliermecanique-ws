//package com.champlain.ateliermecaniquews.vehiclemanagementsubdomain.presentationlayer;
//
//import com.champlain.ateliermecaniquews.vehiclemanagementsubdomain.datalayer.TransmissionType;
//import com.champlain.ateliermecaniquews.vehiclemanagementsubdomain.datalayer.Vehicle;
//import com.champlain.ateliermecaniquews.vehiclemanagementsubdomain.datalayer.VehicleRepository;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//
//import static org.hamcrest.Matchers.hasSize;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//class VehicleControllerIntegrationTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private VehicleRepository vehicleRepository;
//
//    private Vehicle testVehicle;
//    private String testVehicleId;
//
//    @BeforeEach
//    void setUp() {
//        // Set up data before each test
//        testVehicle = new Vehicle();
//        testVehicle.setMake("Toyota");
//        testVehicle.setModel("Corolla");
//        testVehicle.setYear("2019");
//        testVehicle.setTransmission_type(TransmissionType.MANUAL);
//        testVehicle.setMileage("45000");
//        Vehicle savedVehicle = vehicleRepository.save(testVehicle);
//        testVehicleId = savedVehicle.getVehicleIdentifier().getVehicleId();
//    }
//
//    @AfterEach
//    void tearDown() {
//        // Clean up the database after each test
//        vehicleRepository.deleteAll();
//    }
//
//    @Test
//    void getAllVehiclesForCustomerTest() throws Exception {
//        String customerId = "someCustomerId";
//        mockMvc.perform(get("/api/v1/customers/{customerId}/vehicles", customerId))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$", hasSize(1)))
//                .andExpect(jsonPath("$[0].make").value("Toyota"));
//    }
//
//    @Test
//    void getVehicleById_validId_shouldSucceed() throws Exception {
//        mockMvc.perform(get("/api/v1/vehicles/{vehicleId}", testVehicleId))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.make").value("Toyota"));
//    }
//
//    @Test
//    void getVehicleById_invalidId_shouldReturnNotFound() throws Exception {
//        String randomVehicleId = "nonExistingId";
//        mockMvc.perform(get("/api/v1/vehicles/{vehicleId}", randomVehicleId))
//                .andExpect(status().isNotFound());
//    }
//}
