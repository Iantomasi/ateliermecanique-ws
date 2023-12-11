package com.champlain.ateliermecaniquews.customeraccountsmanagementsubdomain.presentationlayer;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;


import com.champlain.ateliermecaniquews.customeraccountsmanagementsubdomain.businesslayer.CustomerAccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;

import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CustomerAccountController.class)
class CustomerAccountControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;


    @MockBean
    private CustomerAccountService customerAccountService;


    @Test
    void getAllCustomerAccounts_withAccounts_shouldReturnOk() throws Exception {
        // Arrange
        List<CustomerAccountResponseModel> accounts = Arrays.asList(
                CustomerAccountResponseModel.builder()
                        .customerId("1")
                        .firstName("John")
                        .lastName("Doe")
                        .email("john@example.com")
                        .phoneNumber("1234567890")
                        .build(),
                CustomerAccountResponseModel.builder()
                        .customerId("2")
                        .firstName("Jane")
                        .lastName("Doe")
                        .email("jane@example.com")
                        .phoneNumber("0987654321")
                        .build()
        );

        when(customerAccountService.getAllCustomerAccounts()).thenReturn(accounts);

        // Act & Assert
        mockMvc.perform(get("/api/v1/customers"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(accounts.size())))
                .andExpect(jsonPath("$[0].customerId").value("1"))
                .andExpect(jsonPath("$[1].customerId").value("2"));
    }

    @Test
    void getAllCustomerAccounts_noAccounts_shouldReturnNotFound() throws Exception {
        // Arrange
        when(customerAccountService.getAllCustomerAccounts()).thenReturn(Collections.emptyList());

        // Act & Assert
        mockMvc.perform(get("/api/v1/customers"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getCustomerAccountById_validId_shouldSucceed() throws Exception {
        // Arrange
        String validCustomerId = "1";
        CustomerAccountResponseModel responseModel = CustomerAccountResponseModel.builder()
                .customerId(validCustomerId)
                .firstName("Cristiano")
                .lastName("Ronaldo")
                .email("cr7@example.com")
                .phoneNumber("1234567890")
                .build();

        when(customerAccountService.getCustomerAccountById(validCustomerId)).thenReturn(responseModel);

        // Act & Assert
        mockMvc.perform(get("/api/v1/customers/{customerId}", validCustomerId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.customerId").value("1"))
                .andExpect(jsonPath("$.firstName").value("Cristiano"))
                .andExpect(jsonPath("$.lastName").value("Ronaldo"))
                .andExpect(jsonPath("$.email").value("cr7@example.com"))
                .andExpect(jsonPath("$.phoneNumber").value("1234567890"));
    }

    @Test
    void getCustomerAccountById_invalidId_shouldThrowNotFoundException() throws Exception {
        // Arrange
        String invalidCustomerId = "notGoodBuddy";

        when(customerAccountService.getCustomerAccountById(invalidCustomerId)).thenReturn(null);

        // Act & Assert
        mockMvc.perform(get("/api/v1/customers/{customerId}", invalidCustomerId))
                .andExpect(status().isNotFound());
    }



}