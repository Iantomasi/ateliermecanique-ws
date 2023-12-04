package com.champlain.ateliermecaniquews.customeraccountsmanagementsubdomain.presentationlayer;

import static org.junit.jupiter.api.Assertions.*;

import com.champlain.ateliermecaniquews.customeraccountsmanagementsubdomain.businesslayer.CustomerAccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Collections;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CustomerAccountController.class)
@ExtendWith(MockitoExtension.class)
class CustomerAccountControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerAccountService customerAccountService;

    @Test
    public void getAllCustomerAccountsTest() throws Exception {

        CustomerAccountResponseModel responseModel = CustomerAccountResponseModel.builder()
                .customerAccountId("1")
                .customerFirstName("John")
                .customerLastName("Doe")
                .customerEmail("john@example.com")
                .customerPhoneNumber("1234567890")
                .build();
        List<CustomerAccountResponseModel> responseModels = Collections.singletonList(responseModel);

        given(customerAccountService.getAllCustomerAccounts()).willReturn(responseModels);

        mockMvc.perform(get("/api/v1/customers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].customerAccountId").value("1"))
                .andExpect(jsonPath("$[0].customerFirstName").value("John"))
                .andExpect(jsonPath("$[0].customerLastName").value("Doe"))
                .andExpect(jsonPath("$[0].customerEmail").value("john@example.com"))
                .andExpect(jsonPath("$[0].customerPhoneNumber").value("1234567890"));
    }

}