package com.champlain.ateliermecaniquews.appointmentmanagementsubdomain.datalayer;

import com.champlain.ateliermecaniquews.appointmentmanagementsubdomain.datamapperlayer.AppointmentRequestMapper;
import com.champlain.ateliermecaniquews.appointmentmanagementsubdomain.datamapperlayer.AppointmentResponseMapper;
import com.champlain.ateliermecaniquews.appointmentmanagementsubdomain.presentationlayer.AppointmentRequestModel;
import com.champlain.ateliermecaniquews.appointmentmanagementsubdomain.presentationlayer.AppointmentResponseModel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static com.champlain.ateliermecaniquews.appointmentmanagementsubdomain.datalayer.Status.PENDING;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

@DataJpaTest
class AppointmentRepositoryTest {

    @Autowired
    private AppointmentRepository appointmentRepository;

    private String savedAppointmentId;
    private String savedCustomerId;

    private final AppointmentRequestMapper mapper = Mappers.getMapper(AppointmentRequestMapper.class);

    private final AppointmentResponseMapper responseMapper =  Mappers.getMapper(AppointmentResponseMapper.class);
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
    void testRequestModelToEntity() {
        // Arrange
        AppointmentRequestModel requestModel = AppointmentRequestModel.builder()
                .customerId("123")
                .vehicleId("456")
                .appointmentDate(LocalDateTime.now())
                .services("Service 1, Service 2")
                .comments("Some comments")
                .status(Status.PENDING)
                .build();

        // Act
        Appointment appointment = mapper.requestModelToEntity(requestModel);

        // Assert

        // Add more specific assertions based on your actual mapping logic
        assertEquals("123", appointment.getCustomerId());
        assertEquals("456", appointment.getVehicleId());
        // Assuming LocalDateTime.now() is close enough
        assertEquals(requestModel.getAppointmentDate(), appointment.getAppointmentDate());
        assertEquals("Service 1, Service 2", appointment.getServices());
        assertEquals("Some comments", appointment.getComments());
        assertEquals(Status.PENDING, appointment.getStatus());
    }


    @Test
    void testEntityToResponseModel() {
        // Arrange
        Appointment appointment = createSampleAppointment();

        // Act
        AppointmentResponseModel responseModel = responseMapper.entityToResponseModel(appointment);

        // Assert
        assertNotNull(responseModel);
        assertEquals(appointment.getAppointmentIdentifier().getAppointmentId(), responseModel.getAppointmentId());
        assertEquals(appointment.getCustomerId(), responseModel.getCustomerId());
        assertEquals(appointment.getVehicleId(), responseModel.getVehicleId());
        assertEquals(appointment.getStatus(), responseModel.getStatus());
        // Add more assertions based on your actual mapping logic
    }

    @Test
    void testEntityToResponseModelList() {
        // Arrange
        List<Appointment> appointments = Arrays.asList(createSampleAppointment(), createSampleAppointment());

        // Act
        List<AppointmentResponseModel> responseModels = responseMapper.entityToResponseModelList(appointments);

        // Assert
        assertNotNull(responseModels);
        assertEquals(appointments.size(), responseModels.size());
        // Add more assertions based on your actual mapping logic
    }

    private Appointment createSampleAppointment() {
        // Create and return a sample Appointment with necessary data
        return new Appointment();
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



    @Test
    public void findAllAppointmentsByStatus_shouldReturnList() {
        // Arrange
        Status targetStatus = PENDING;  // You can change this to the status you want to test

        // Create a new appointment with the target status
        Appointment appointmentWithTargetStatus = new Appointment();
        appointmentWithTargetStatus.setAppointmentIdentifier(new AppointmentIdentifier());
        appointmentWithTargetStatus.setStatus(targetStatus);
        appointmentRepository.save(appointmentWithTargetStatus);

        // Act
        List<Appointment> foundAppointments = appointmentRepository.findAllAppointmentsByStatus(targetStatus);

        // Assert
        assertFalse(foundAppointments.isEmpty());
        assertEquals(targetStatus, foundAppointments.get(0).getStatus());
    }



}
