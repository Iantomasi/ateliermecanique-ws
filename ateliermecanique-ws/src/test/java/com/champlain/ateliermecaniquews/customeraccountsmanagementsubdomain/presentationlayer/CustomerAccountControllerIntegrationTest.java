package com.champlain.ateliermecaniquews.customeraccountsmanagementsubdomain.presentationlayer;

import com.champlain.ateliermecaniquews.authenticationsubdomain.dataLayer.ERole;
import com.champlain.ateliermecaniquews.authenticationsubdomain.dataLayer.Role;
import com.champlain.ateliermecaniquews.authenticationsubdomain.dataLayer.User;
import com.champlain.ateliermecaniquews.authenticationsubdomain.dataLayer.UserIdentifier;
import com.champlain.ateliermecaniquews.authenticationsubdomain.dataLayer.repositories.UserRepository;
import com.champlain.ateliermecaniquews.customeraccountsmanagementsubdomain.businesslayer.CustomerAccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class CustomerAccountControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    private User testAccount;
    private String testAccountId;

    @BeforeEach
    void setUp() {
        // Set up data before each test
        UserIdentifier userIdentifier = new UserIdentifier(); // Create a UUID

        Set<Role> roles = new HashSet<>();
        roles.add(new Role(ERole.ROLE_CUSTOMER));

        testAccount = new User("Jane", "Doe", "1234567890", "jane@example.com", "testPassword",roles, null);

        testAccount.setUserIdentifier(userIdentifier);
        User savedAccount = userRepository.save(testAccount);
        testAccountId = savedAccount.getUserIdentifier().getUserId(); // Get the UUID
    }

    @AfterEach
    void tearDown() {
        // Clean up the database after each test
        userRepository.deleteAll();
    }

    @Test
    void getAllCustomerAccounts_shouldSucceed() throws Exception {
        mockMvc.perform(get("/api/v1/customers"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].email").value("john@example.com"));
    }

    @Test
    void getCustomerAccountByCustomerId_shouldSucceed() throws Exception {
        mockMvc.perform(get("/api/v1/customers/{customerId}", testAccountId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.email").value("john@example.com"));
    }

    @Test
    void getCustomerAccountByInvalidCustomerId_shouldReturnNotFound() throws Exception {
        String randomUUID =  new UserIdentifier().getUserId();
        mockMvc.perform(get("/api/v1/customers/{customerId}", randomUUID))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateCustomerAccountByCustomerId_shouldSucceed() throws Exception {
        CustomerAccountRequestModel requestModel = CustomerAccountRequestModel.builder()
                .firstName("Jane")
                .lastName("Doe")
                .email("jane@example.com")
                .phoneNumber("9876543210")
                .build();

        mockMvc.perform(put("/api/v1/customers/{customerId}", testAccountId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(requestModel)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.email").value("jane@example.com"));
    }

    @Test
    void updateCustomerAccountByInvalidCustomerId_shouldReturnNotFound() throws Exception {
        CustomerAccountRequestModel requestModel = CustomerAccountRequestModel.builder()
                .firstName("Jane")
                .lastName("Doe")
                .email("jane@example.com")
                .phoneNumber("9876543210")
                .build();

        String randomUUID = new UserIdentifier().getUserId();
        mockMvc.perform(put("/api/v1/customers/{customerId}", randomUUID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(requestModel)))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteCustomerAccountAndVehiclesByCustomerId_shouldSucceed() {
        // Arrange
        String customerId = "testCustomerId";
        CustomerAccountService customerAccountService = mock(CustomerAccountService.class);
        CustomerAccountController customerAccountController = new CustomerAccountController(customerAccountService);

        // Act
        ResponseEntity<Void> response = customerAccountController.deleteCustomerAccountByCustomerId(customerId);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(customerAccountService).deleteCustomerAccountByCustomerId(customerId);
    }


    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}