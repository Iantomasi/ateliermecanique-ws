package com.champlain.ateliermecaniquews.authenticationsubdomain.presentationlayer;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;



@SpringBootTest
@AutoConfigureMockMvc
class ContentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void allAccess() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/content/all"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Public Content."));
    }

    @Test
    @WithMockUser(roles = "CUSTOMER")
    void userAccessAsCustomer() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/content/user"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("User Content."));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void userAccessAsAdmin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/content/user"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("User Content."));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void adminAccessAsAdmin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/content/admin"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Admin Content."));
    }

    @Test
    @WithMockUser(roles = "CUSTOMER")
    void adminAccessAsCustomerShouldReturnForbidden() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/content/admin"))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }


    @Test
    void shouldReturnUnauthorizedResponse() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/content/admin"))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(401))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error").value("Unauthorized"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Full authentication is required to access this resource"));
    }

}