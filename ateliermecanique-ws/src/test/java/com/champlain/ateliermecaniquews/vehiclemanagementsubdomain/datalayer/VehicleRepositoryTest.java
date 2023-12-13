//package com.champlain.ateliermecaniquews.vehiclemanagementsubdomain.datalayer;
//
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@DataJpaTest
//class VehicleRepositoryTest {
//
//    @Autowired
//    private VehicleRepository vehicleRepository;
//
//    private String savedVehicleId;
//
//    @BeforeEach
//    public void setUp() {
//        Vehicle newVehicle = new Vehicle();
//        VehicleIdentifier identifier = new VehicleIdentifier();
//        newVehicle.setVehicleIdentifier(identifier);
//        Vehicle savedVehicle = vehicleRepository.save(newVehicle);
//        savedVehicleId = savedVehicle.getVehicleIdentifier().getVehicleId();
//    }
//
//    @AfterEach
//    public void tearDown() {
//        vehicleRepository.deleteAll();
//    }
//
//    @Test
//    public void whenFindByVehicleId_thenReturnVehicle() {
//        // Arrange
//        assertNotNull(savedVehicleId);
//
//        // Act
//        Vehicle found = vehicleRepository.findVehicleByVehicleIdentifier_VehicleId(savedVehicleId);
//
//        // Assert
//        assertNotNull(found);
//        assertEquals(savedVehicleId, found.getVehicleIdentifier().getVehicleId());
//    }
//
//    @Test
//    public void whenFindByNonExistentVehicleId_thenReturnNull() {
//        // Arrange
//        String nonExistentVehicleId = new VehicleIdentifier().getVehicleId();
//
//        // Act
//        Vehicle found = vehicleRepository.findVehicleByVehicleIdentifier_VehicleId(nonExistentVehicleId);
//
//        // Assert
//        assertNull(found);
//    }
//
//
//}