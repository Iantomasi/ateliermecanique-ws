package com.champlain.ateliermecaniquews.appointmentmanagementsubdomain.datalayer;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class AppointmentRepositoryTest {

    @Autowired
    private AppointmentRepository appointmentRepository;

    private String savedAppointmentId;
    private String savedCustomerId;

    @BeforeEach
    public void setUp() {
        Appointment newAppointment = new Appointment();
        AppointmentIdentifier identifier = new AppointmentIdentifier();
        newAppointment.setAppointmentIdentifier(identifier);
        newAppointment.setCustomerId("testCustomerId"); // Set a test customer id
        Appointment savedAppointment = appointmentRepository.save(newAppointment);
        savedAppointmentId = savedAppointment.getAppointmentIdentifier().getAppointmentId();
        savedCustomerId = savedAppointment.getCustomerId();
    }

    @AfterEach
    public void tearDown() {
        appointmentRepository.deleteAll();
    }

    @Test
    public void findAppointmentByAppointmentId_shouldSucceed() {
        // Arrange
        assertNotNull(savedAppointmentId);

        // Act
        Appointment found = appointmentRepository.findAppointmentByAppointmentIdentifier_AppointmentId(savedAppointmentId);

        // Assert
        assertNotNull(found);
        assertEquals(savedAppointmentId, found.getAppointmentIdentifier().getAppointmentId());
    }

    @Test
    public void findAllAppointmentsByCustomerId_shouldReturnList() {
        // Arrange
        assertNotNull(savedCustomerId);

        // Act
        List<Appointment> foundAppointments = appointmentRepository.findAllAppointmentsByCustomerId(savedCustomerId);

        // Assert
        assertFalse(foundAppointments.isEmpty());
        assertEquals(savedCustomerId, foundAppointments.get(0).getCustomerId());
    }
    

    @Test
    public void findAppointmentByInvalidAppointmentId_thenReturnNull() {
        // Arrange
        String nonExistentAppointmentId = "nonExistent";

        // Act
        Appointment found = appointmentRepository.findAppointmentByAppointmentIdentifier_AppointmentId(nonExistentAppointmentId);

        // Assert
        assertNull(found);
    }

    @Test
    public void findAllAppointmentsByNonExistentCustomerId_thenReturnEmptyList() {
        // Arrange
        String nonExistentCustomerId = "nonExistent";

        // Act
        List<Appointment> foundAppointments = appointmentRepository.findAllAppointmentsByCustomerId(nonExistentCustomerId);

        // Assert
        assertTrue(foundAppointments.isEmpty());
    }

}
