package com.champlain.ateliermecaniquews.customeraccountsmanagementsubdomain.presentationlayer;

import com.champlain.ateliermecaniquews.customeraccountsmanagementsubdomain.datalayer.CustomerAccount;
import com.champlain.ateliermecaniquews.customeraccountsmanagementsubdomain.datalayer.CustomerAccountIdentifier;
import com.champlain.ateliermecaniquews.customeraccountsmanagementsubdomain.datalayer.CustomerAccountRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class CustomerAccountControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CustomerAccountRepository customerAccountRepository;

    private CustomerAccount testAccount;
    private String testAccountId;

    @BeforeEach
    void setUp() {
        // Set up data before each test
        CustomerAccountIdentifier identifier = new CustomerAccountIdentifier(); // Create a UUID
        testAccount = new CustomerAccount("John", "Doe", "john@example.com", "1234567890", "password123");
        testAccount.setCustomerAccountIdentifier(identifier);
        CustomerAccount savedAccount = customerAccountRepository.save(testAccount);
        testAccountId = savedAccount.getCustomerAccountIdentifier().getCustomerId(); // Get the UUID
    }

    @AfterEach
    void tearDown() {
        // Clean up the database after each test
        customerAccountRepository.deleteAll();
    }

    @Test
    void getAllCustomerAccountsTest() throws Exception {
        mockMvc.perform(get("/api/v1/customers"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].email").value("john@example.com"));
    }

    @Test
    void getCustomerAccountById_validId_shouldSucceed() throws Exception {
        mockMvc.perform(get("/api/v1/customers/{customerId}", testAccountId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.email").value("john@example.com"));
    }

    @Test
    void getCustomerAccountById_invalidId_shouldReturnNotFound() throws Exception {
        String randomUUID =  new CustomerAccountIdentifier().getCustomerId();
        mockMvc.perform(get("/api/v1/customers/{customerId}", randomUUID))
                .andExpect(status().isNotFound());
    }


}