package com.champlain.ateliermecaniquews.appointmentmanagementsubdomain.presentationlayer;

import com.champlain.ateliermecaniquews.appointmentmanagementsubdomain.businesslayer.AppointmentService;

import com.champlain.ateliermecaniquews.appointmentmanagementsubdomain.datalayer.Appointment;
import com.champlain.ateliermecaniquews.appointmentmanagementsubdomain.datalayer.AppointmentIdentifier;
import com.champlain.ateliermecaniquews.appointmentmanagementsubdomain.datalayer.AppointmentRepository;
import com.champlain.ateliermecaniquews.appointmentmanagementsubdomain.datalayer.Status;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
class AppointmentControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;


    @Autowired
    private AppointmentRepository appointmentRepository;

    private Appointment testAppointment ;
    private String testAppointmentId;

    @BeforeEach
    void setUp() {
        // Set up data before each test
        AppointmentIdentifier identifier = new AppointmentIdentifier(); // Create a UUID
        testAppointment = new Appointment(
                "b7024d89-1a5e-4517-3gba-05178u7ar260",
                "132b41b2-2bec-4b98-b08d-c7c0e03fe33e",
                "2024-03-24 11:00",
                "Preventive Maintenance",
                "None",
                Status.PENDING
        );
        testAppointment.setAppointmentIdentifier(identifier);
        Appointment savedAppointment = appointmentRepository.save(testAppointment);
        testAppointmentId = savedAppointment.getAppointmentIdentifier().getAppointmentId(); // Get the UUID
    }

    @AfterEach
    void tearDown() {
        // Clean up the database after each test
        appointmentRepository.deleteAll();
    }

    @Test
    void getAllAppointments_shouldSucceed() throws Exception {
        mockMvc.perform(get("/api/v1/appointments"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].services").value("Preventive Maintenance"));
    }


}