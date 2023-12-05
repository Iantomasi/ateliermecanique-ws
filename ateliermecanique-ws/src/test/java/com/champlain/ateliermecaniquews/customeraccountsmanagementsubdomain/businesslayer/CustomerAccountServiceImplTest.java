package com.champlain.ateliermecaniquews.customeraccountsmanagementsubdomain.businesslayer;

import com.champlain.ateliermecaniquews.customeraccountsmanagementsubdomain.datalayer.CustomerAccount;
import com.champlain.ateliermecaniquews.customeraccountsmanagementsubdomain.datalayer.CustomerAccountRepository;
import com.champlain.ateliermecaniquews.customeraccountsmanagementsubdomain.datamapperlayer.CustomerAccountResponseMapper;
import com.champlain.ateliermecaniquews.customeraccountsmanagementsubdomain.presentationlayer.CustomerAccountResponseModel;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CustomerAccountServiceImplTest {
    @Mock
    private CustomerAccountRepository customerAccountRepository;

    @Mock
    private CustomerAccountResponseMapper customerAccountResponseMapper;

    @InjectMocks
    private CustomerAccountServiceImpl customerAccountService;

    @Test
    void getAllCustomerAccountsTest() {
        CustomerAccount customerAccount = new CustomerAccount("Jane", "Doe", "jane@example.com", "1234567890", "testPassword");

        List<CustomerAccount> customerAccounts = Collections.singletonList(customerAccount);
        when(customerAccountRepository.findAll()).thenReturn(customerAccounts);

        CustomerAccountResponseModel responseModel = CustomerAccountResponseModel.builder()
                .customerAccountId("1")
                .customerFirstName("John")
                .customerLastName("Doe")
                .customerEmail("john@example.com")
                .customerPhoneNumber("1234567890")
                .build();
        List<CustomerAccountResponseModel> responseModels = Collections.singletonList(responseModel);

        when(customerAccountResponseMapper.entityToResponseModelList(customerAccounts)).thenReturn(responseModels);

        List<CustomerAccountResponseModel> result = customerAccountService.getAllCustomerAccounts();

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(responseModels.size(), result.size());
        assertEquals(responseModels.get(0).getCustomerAccountId(), result.get(0).getCustomerAccountId());

        verify(customerAccountRepository, times(1)).findAll();
        verify(customerAccountResponseMapper, times(1)).entityToResponseModelList(customerAccounts);
    }


}