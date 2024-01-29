//package com.champlain.ateliermecaniquews.vehiclemanagementsubdomain.datalayer;
//
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@DataJpaTest
//class VehicleRepositoryTest {
//
//    @Autowired
//    private VehicleRepository vehicleRepository;
//
//    private String savedCustomerId;
//    private String savedVehicleId;
//
//    @BeforeEach
//    public void setUp() {
//        Vehicle newVehicle = new Vehicle();
//        VehicleIdentifier identifier = new VehicleIdentifier();
//        newVehicle.setVehicleIdentifier(identifier);
//        newVehicle.setUserId("testCustomerId");
//        Vehicle savedVehicle = vehicleRepository.save(newVehicle);
//        savedCustomerId = savedVehicle.getUserId();
//        savedVehicleId = savedVehicle.getVehicleIdentifier().getVehicleId();
//    }
//
//    @AfterEach
//    public void tearDown() {
//        vehicleRepository.deleteAll();
//    }
//
//    @Test
//    public void findAllVehiclesByCustomerId_shouldSucceed() {
//        // Arrange
//        assertNotNull(savedCustomerId);
//
//        // Act
//        List<Vehicle> foundVehicles = vehicleRepository.findAllVehiclesByUserId(savedCustomerId);
//
//        // Assert
//        assertFalse(foundVehicles.isEmpty());
//        assertEquals(savedCustomerId, foundVehicles.get(0).getUserId());
//    }
//
//    @Test
//    public void findAllVehiclesByInvalidCustomerId_thenReturnEmptyList() {
//        // Arrange
//        String nonExistentCustomerId = "nonExistentCustomerId";
//
//        // Act
//        List<Vehicle> foundVehicles = vehicleRepository.findAllVehiclesByUserId(nonExistentCustomerId);
//
//        // Assert
//        assertTrue(foundVehicles.isEmpty());
//    }
//
//    @Test
//    public void findVehicleByCustomerIdAndVehicleId_shouldSucceed() {
//        // Arrange
//        assertNotNull(savedCustomerId);
//        assertNotNull(savedVehicleId);
//
//        // Act
//        Vehicle foundVehicle = vehicleRepository.findVehicleByUserIdAndVehicleIdentifier_VehicleId(savedCustomerId, savedVehicleId);
//
//        // Assert
//        assertNotNull(foundVehicle);
//        assertEquals(savedCustomerId, foundVehicle.getUserId());
//        assertEquals(savedVehicleId, foundVehicle.getVehicleIdentifier().getVehicleId());
//    }
//
//
//    @Test
//    public void findVehicleByInvalidCustomerIdAndVehicleId_thenReturnNull() {
//        // Arrange
//        String nonExistentCustomerId = "nonExistentCustomerId";
//        String nonExistentVehicleId = "nonExistentVehicleId";
//
//        // Act
//        Vehicle foundVehicle = vehicleRepository.findVehicleByUserIdAndVehicleIdentifier_VehicleId(nonExistentCustomerId, nonExistentVehicleId);
//
//        // Assert
//        assertNull(foundVehicle);
//    }
//}
