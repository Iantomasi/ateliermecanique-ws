package com.champlain.ateliermecaniquews.customerinvoicemanagementsubdomain.presentationlayer;

import com.champlain.ateliermecaniquews.customerinvoicemanagementsubdomain.businesslayer.CustomerInvoiceService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CustomerInvoiceController.class)
class CustomerInvoiceControllerUnitTest {
/*
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    CustomerInvoiceService customerInvoiceService;

    @Test
    @WithMockUser(username = "admin@example.com", roles = "ADMIN")
    void getAllInvoices_shouldSucceed() throws Exception {
        // Arrange
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        List<CustomerInvoiceResponseModel> invoices = List.of(
                CustomerInvoiceResponseModel.builder()
                        .invoiceId("1")
                        .customerId("1")
                        .appointmentId("1")
                        .invoiceDate(LocalDateTime.parse("2021-01-01 12:00", formatter))
                        .mechanicNotes("notes")
                        .sumOfServices(100.0)
                        .build(),
                CustomerInvoiceResponseModel.builder()
                        .invoiceId("2")
                        .customerId("2")
                        .appointmentId("2")
                        .invoiceDate(LocalDateTime.parse("2021-01-01 12:00", formatter))
                        .mechanicNotes("notes")
                        .sumOfServices(105.0)
                        .build()
        );

        when(customerInvoiceService.getAllInvoices()).thenReturn(invoices);

        // Act & Assert
        mockMvc.perform(get("/api/v1/invoices"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void getAllInvoices_noInvoices_shouldReturnEmptyList() throws Exception {
        // Arrange
        when(customerInvoiceService.getAllInvoices()).thenReturn(Collections.emptyList());

        // Act & Assert
        mockMvc.perform(get("/api/v1/invoices"))
                .andExpect(status().isNotFound());
    }

 */


}

