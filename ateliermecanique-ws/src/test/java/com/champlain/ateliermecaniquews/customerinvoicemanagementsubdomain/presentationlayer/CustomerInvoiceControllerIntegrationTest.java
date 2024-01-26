package com.champlain.ateliermecaniquews.customerinvoicemanagementsubdomain.presentationlayer;

import com.champlain.ateliermecaniquews.appointmentmanagementsubdomain.businesslayer.AppointmentService;
import com.champlain.ateliermecaniquews.appointmentmanagementsubdomain.datalayer.Appointment;
import com.champlain.ateliermecaniquews.appointmentmanagementsubdomain.datalayer.AppointmentIdentifier;
import com.champlain.ateliermecaniquews.appointmentmanagementsubdomain.datalayer.AppointmentRepository;
import com.champlain.ateliermecaniquews.appointmentmanagementsubdomain.datalayer.Status;
import com.champlain.ateliermecaniquews.appointmentmanagementsubdomain.presentationlayer.AppointmentResponseModel;
import com.champlain.ateliermecaniquews.customerinvoicemanagementsubdomain.businesslayer.CustomerInvoiceService;
import com.champlain.ateliermecaniquews.customerinvoicemanagementsubdomain.datalayer.CustomerInvoice;
import com.champlain.ateliermecaniquews.customerinvoicemanagementsubdomain.datalayer.CustomerInvoiceIdentifier;
import com.champlain.ateliermecaniquews.customerinvoicemanagementsubdomain.datalayer.CustomerInvoiceRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
class CustomerInvoiceControllerIntegrationTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerInvoiceService customerInvoiceService;

    @Autowired
    private CustomerInvoiceRepository customerInvoiceRepository;

    private CustomerInvoice testInvoice ;
    private String testInvoiceId;

    @BeforeEach
    void setUp() {
        // Set up data before each test
        CustomerInvoiceIdentifier identifier = new CustomerInvoiceIdentifier(); // Create a UUID
        testInvoice = new CustomerInvoice(
                "yzab8cd5-3e6f-8796-2abi-96330c6bw164",
                "1008dc5c-d460-443f-8f37-a174284f868l",
                "2024-02-04 19:00",
                "Muffler was fixed",
                118.95
        );

        testInvoice.setCustomerInvoiceIdentifier(identifier);
        CustomerInvoice savedInvoice = customerInvoiceRepository.save(testInvoice);
        testInvoiceId = savedInvoice.getCustomerInvoiceIdentifier().getInvoiceId(); // Get the UUID
    }

    @AfterEach
    void tearDown() {
        customerInvoiceRepository.deleteAll();
    }

    @Test
    @WithMockUser(username = "admin@example.com", roles = "ADMIN")
    void getAllInvoices_shouldSucceed() throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse("2024-03-24 11:00", formatter);
        // Arrange - Mock the service layer response
        when(customerInvoiceService.getAllInvoices())
                .thenReturn(Collections.singletonList(
                        new CustomerInvoiceResponseModel(testInvoiceId, "customerId", "appointmentId", dateTime , "Preventive Maintenance", 100.0)));

        // Act & Assert
        mockMvc.perform(get("/api/v1/invoices"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].sumOfServices").value(100.00));
    }




}