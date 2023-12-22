package com.champlain.ateliermecaniquews.customeraccountsmanagementsubdomain.businesslayer;

import com.champlain.ateliermecaniquews.customeraccountsmanagementsubdomain.datalayer.CustomerAccount;
import com.champlain.ateliermecaniquews.customeraccountsmanagementsubdomain.datalayer.CustomerAccountRepository;
import com.champlain.ateliermecaniquews.customeraccountsmanagementsubdomain.datamapperlayer.CustomerAccountResponseMapper;
import com.champlain.ateliermecaniquews.customeraccountsmanagementsubdomain.presentationlayer.CustomerAccountRequestModel;
import com.champlain.ateliermecaniquews.customeraccountsmanagementsubdomain.presentationlayer.CustomerAccountResponseModel;
import com.champlain.ateliermecaniquews.vehiclemanagementsubdomain.businesslayer.VehicleService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Collections;
import java.util.List;

@SpringBootTest
class CustomerAccountServiceImplTest {
    @Mock
    private CustomerAccountRepository customerAccountRepository;

    @Mock
    private CustomerAccountResponseMapper customerAccountResponseMapper;

    @InjectMocks
    private CustomerAccountServiceImpl customerAccountService;

    @Mock
    private VehicleService vehicleService;


    @Test
    void getAllCustomerAccounts_shouldSucceed() {
        CustomerAccount customerAccount = new CustomerAccount("Jane", "Doe", "jane@example.com", "1234567890", "testPassword");

        List<CustomerAccount> customerAccounts = Collections.singletonList(customerAccount);
        when(customerAccountRepository.findAll()).thenReturn(customerAccounts);

        CustomerAccountResponseModel responseModel = CustomerAccountResponseModel.builder()
                .customerId("1")
                .firstName("John")
                .lastName("Doe")
                .email("john@example.com")
                .phoneNumber("1234567890")
                .build();
        List<CustomerAccountResponseModel> responseModels = Collections.singletonList(responseModel);

        when(customerAccountResponseMapper.entityToResponseModelList(customerAccounts)).thenReturn(responseModels);

        List<CustomerAccountResponseModel> result = customerAccountService.getAllCustomerAccounts();

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(responseModels.size(), result.size());
        assertEquals(responseModels.get(0).getCustomerId(), result.get(0).getCustomerId());

        verify(customerAccountRepository, times(1)).findAll();
        verify(customerAccountResponseMapper, times(1)).entityToResponseModelList(customerAccounts);
    }

    @Test
    void getCustomerAccountByCustomerId_shouldSucceed() {
        //Arrange
        String customerId = "1";
        CustomerAccount customerAccount = new CustomerAccount("Jane", "Doe", "jane@example.com", "1234567890", "testPassword");

        when(customerAccountRepository.findCustomerAccountByCustomerAccountIdentifier_CustomerId(customerId)).thenReturn(customerAccount);

        CustomerAccountResponseModel responseModel = CustomerAccountResponseModel.builder()
                .customerId(customerId)
                .firstName("Jane")
                .lastName("Doe")
                .email("jane@example.com")
                .phoneNumber("1234567890")
                .build();

        when(customerAccountResponseMapper.entityToResponseModel(customerAccount)).thenReturn(responseModel);

        //Act
        CustomerAccountResponseModel result = customerAccountService.getCustomerAccountByCustomerId(customerId);

        //Assert
        assertNotNull(result);
        assertEquals(customerId, result.getCustomerId());
        assertEquals("Jane", result.getFirstName());
        assertEquals("Doe", result.getLastName());
        assertEquals("jane@example.com", result.getEmail());
        assertEquals("1234567890", result.getPhoneNumber());

        verify(customerAccountRepository, times(1)).findCustomerAccountByCustomerAccountIdentifier_CustomerId(customerId);
        verify(customerAccountResponseMapper, times(1)).entityToResponseModel(customerAccount);
    }

    @Test
    void getCustomerAccountByInvalidCustomerId_shouldReturnNull() {
        String nonExistentCustomerId = "notARealId";

        when(customerAccountRepository.findCustomerAccountByCustomerAccountIdentifier_CustomerId(nonExistentCustomerId)).thenReturn(null);

        CustomerAccountResponseModel result = customerAccountService.getCustomerAccountByCustomerId(nonExistentCustomerId);

        assertNull(result);

        verify(customerAccountRepository, times(1)).findCustomerAccountByCustomerAccountIdentifier_CustomerId(nonExistentCustomerId);
    }


    @Test
    void updateCustomerAccountByCustomerId_shouldSucceed() {
        // Arrange
        String customerId = "555";
        CustomerAccountRequestModel requestModel = CustomerAccountRequestModel.builder()
                .firstName("JONNY")
                .lastName("DoEEE")
                .email("JONNYDoEEE@example.com")
                .phoneNumber("6789998212")
                .build();

        CustomerAccount existingAccount = new CustomerAccount();
        existingAccount.setFirstName("John");
        existingAccount.setLastName("Doe");
        existingAccount.setEmail("johndoe@example.com");
        existingAccount.setPhoneNumber("123456789");

        when(customerAccountRepository.findCustomerAccountByCustomerAccountIdentifier_CustomerId(customerId))
                .thenReturn(existingAccount);
        when(customerAccountRepository.save(any(CustomerAccount.class)))
                .thenReturn(existingAccount); // Mock saving the account

        CustomerAccountResponseModel expectedResponse = CustomerAccountResponseModel.builder()
                .customerId(customerId)
                .firstName("JONNY")
                .lastName("DoEEE")
                .email("JONNYDoEEE@example.com")
                .phoneNumber("6789998212")
                .build();

        when(customerAccountResponseMapper.entityToResponseModel(any(CustomerAccount.class)))
                .thenReturn(expectedResponse);

        // Act
        CustomerAccountResponseModel actualResponse = customerAccountService.updateCustomerAccountByCustomerId(customerId, requestModel);

        // Assert
        assertNotNull(actualResponse);
        assertEquals(expectedResponse, actualResponse);

        // Verify the interactions
        verify(customerAccountRepository).save(argThat(account ->
                "JONNY".equals(account.getFirstName()) &&
                        "DoEEE".equals(account.getLastName()) &&
                        "JONNYDoEEE@example.com".equals(account.getEmail()) &&
                        "6789998212".equals(account.getPhoneNumber())
        ));
    }


    @Test
    void updateCustomerAccountByInvalidCustomerId_shouldReturnNull() {
        String nonExistingCustomerId = "nonExistingId";
        CustomerAccountRequestModel requestModel = CustomerAccountRequestModel.builder()
                .firstName("JONNY")
                .lastName("DoEEE")
                .email("JONNYDoEEE@example.com")
                .phoneNumber("6789998212")
                .build();

        when(customerAccountRepository.findCustomerAccountByCustomerAccountIdentifier_CustomerId(nonExistingCustomerId)).thenReturn(null);

        CustomerAccountResponseModel updatedResponse = customerAccountService.updateCustomerAccountByCustomerId(nonExistingCustomerId, requestModel);

        assertNull(updatedResponse);
    }

    @Test
    void deleteCustomerAccountAndVehiclesByCustomerId_shouldSucceed() {
        // Arrange
        String customerId = "testCustomerId";
        CustomerAccount customerAccount = new CustomerAccount();
        when(customerAccountRepository.findCustomerAccountByCustomerAccountIdentifier_CustomerId(customerId)).thenReturn(customerAccount);

        // Act
        customerAccountService.deleteCustomerAccountByCustomerId(customerId);

        // Assert
        verify(customerAccountRepository).findCustomerAccountByCustomerAccountIdentifier_CustomerId(customerId);
        verify(customerAccountRepository).delete(customerAccount);
        verify(vehicleService).deleteAllVehiclesByCustomerId(customerId);
    }

    @Test
    void deleteCustomerAccountAndVehiclesByInvalidCustomerId_shouldReturnNull() {
        // Arrange
        String customerId = "nonExistingCustomerId";
        when(customerAccountRepository.findCustomerAccountByCustomerAccountIdentifier_CustomerId(customerId)).thenReturn(null);

        // Act
        customerAccountService.deleteCustomerAccountByCustomerId(customerId);

        // Assert
        verify(customerAccountRepository).findCustomerAccountByCustomerAccountIdentifier_CustomerId(customerId);
        verify(customerAccountRepository, never()).delete(any());
        verify(vehicleService, never()).deleteAllVehiclesByCustomerId(any());
    }

}