package com.champlain.ateliermecaniquews.customeraccountsmanagementsubdomain.presentationlayer;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;


import com.champlain.ateliermecaniquews.customeraccountsmanagementsubdomain.businesslayer.CustomerAccountService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
    void getAllCustomerAccounts_shouldSucceed() throws Exception {
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
    void getCustomerAccountByCustomerId_shouldSucceed() throws Exception {
        // Arrange
        String validCustomerId = "1";
        CustomerAccountResponseModel responseModel = CustomerAccountResponseModel.builder()
                .customerId(validCustomerId)
                .firstName("Cristiano")
                .lastName("Ronaldo")
                .email("cr7@example.com")
                .phoneNumber("1234567890")
                .build();

        when(customerAccountService.getCustomerAccountByCustomerId(validCustomerId)).thenReturn(responseModel);

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
    void getCustomerAccountByInvalidCustomerId_shouldReturnNotFound() throws Exception {
        // Arrange
        String invalidCustomerId = "notGoodBuddy";

        when(customerAccountService.getCustomerAccountByCustomerId(invalidCustomerId)).thenReturn(null);

        // Act & Assert
        mockMvc.perform(get("/api/v1/customers/{customerId}", invalidCustomerId))
                .andExpect(status().isNotFound());
    }


    @Test
    void updateCustomerAccountByCustomerId_shouldSucceed() {
        // Arrange
        CustomerAccountService accountService = mock(CustomerAccountService.class);
        CustomerAccountController accountController = new CustomerAccountController(accountService);
        String customerId = "validId";
        CustomerAccountRequestModel requestModel = CustomerAccountRequestModel.builder()
                .firstName("Jane")
                .lastName("Doe")
                .email("jane@example.com")
                .phoneNumber("9876543210")
                .build();
        CustomerAccountResponseModel expectedResponse = CustomerAccountResponseModel.builder()
                .customerId(customerId)
                .firstName("Jane")
                .lastName("Doe")
                .email("jane@example.com")
                .phoneNumber("9876543210")
                .build();
        when(accountService.updateCustomerAccountByCustomerId(customerId, requestModel)).thenReturn(expectedResponse);

        // Act
        ResponseEntity<CustomerAccountResponseModel> responseEntity = accountController.updateCustomerAccountByCustomerId(customerId, requestModel);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedResponse, responseEntity.getBody());
    }

    @Test
    void updateCustomerAccountByInvalidCustomerId_shouldReturnNotFound() {
        // Arrange
        CustomerAccountService accountService = mock(CustomerAccountService.class);
        CustomerAccountController accountController = new CustomerAccountController(accountService);
        String invalidId = "invalidId";
        CustomerAccountRequestModel requestModel = CustomerAccountRequestModel.builder()
                .firstName("Jane")
                .lastName("Doe")
                .email("jane@example.com")
                .phoneNumber("9876543210")
                .build();
        when(accountService.updateCustomerAccountByCustomerId(invalidId, requestModel)).thenReturn(null);

        // Act
        ResponseEntity<CustomerAccountResponseModel> responseEntity = accountController.updateCustomerAccountByCustomerId(invalidId, requestModel);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }



}