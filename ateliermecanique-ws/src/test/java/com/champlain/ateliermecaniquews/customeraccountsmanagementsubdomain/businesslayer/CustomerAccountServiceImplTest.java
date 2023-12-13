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

    @Mock
    private VehicleService vehicleService;


    @Test
    void getAllCustomerAccountsTest() {
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
    void getCustomerByCustomerIdTest() {
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

        CustomerAccountResponseModel result = customerAccountService.getCustomerAccountById(customerId);

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
    void getCustomerByNonExistentCustomerIdTest() {
        String nonExistentCustomerId = "notARealId";

        when(customerAccountRepository.findCustomerAccountByCustomerAccountIdentifier_CustomerId(nonExistentCustomerId)).thenReturn(null);

        // Assuming the service method returns null or throws an exception for a non-existent customer
        CustomerAccountResponseModel result = customerAccountService.getCustomerAccountById(nonExistentCustomerId);

        assertNull(result);

        verify(customerAccountRepository, times(1)).findCustomerAccountByCustomerAccountIdentifier_CustomerId(nonExistentCustomerId);
    }

    @Test
    void testUpdateCustomerById_WhenAccountExists_ThenReturnUpdatedResponseModel() {
        // Mock data
        String customerId = "b7024d89-1a5e-4517-3gba-05178u7ar260";
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

        // Mock behavior of repository and mapper
        when(customerAccountRepository.findCustomerAccountByCustomerAccountIdentifier_CustomerId(customerId))
                .thenReturn(existingAccount);

        when(customerAccountRepository.save(existingAccount)).thenReturn(existingAccount); // Return the same account for simplicity

        CustomerAccountResponseModel updatedResponseMock = CustomerAccountResponseModel.builder()
                .customerId(customerId)
                .firstName("JONNY")
                .lastName("DoEEE")
                .email("JONNYDoEEE@example.com")
                .phoneNumber("6789998212")
                .build();

        when(customerAccountResponseMapper.entityToResponseModel(existingAccount)).thenReturn(updatedResponseMock);

        // Invoke the method
        CustomerAccountResponseModel updatedResponse = customerAccountService.updateCustomerById(customerId, requestModel);

        // Verify
        assertNotNull(updatedResponse);
        assertEquals("JONNY", updatedResponse.getFirstName());
        assertEquals("DoEEE", updatedResponse.getLastName());
        assertEquals("JONNYDoEEE@example.com", updatedResponse.getEmail());
        assertEquals("6789998212", updatedResponse.getPhoneNumber());

        verify(customerAccountRepository).findCustomerAccountByCustomerAccountIdentifier_CustomerId(customerId);
        verify(customerAccountRepository).save(existingAccount);
        verify(customerAccountResponseMapper).entityToResponseModel(existingAccount);
    }

    @Test
    void testUpdateCustomerById_WhenAccountNotExists_ThenReturnNull() {
        // Mock data
        String nonExistingCustomerId = "nonExistingId";
        CustomerAccountRequestModel requestModel = CustomerAccountRequestModel.builder()
                .firstName("JONNY")
                .lastName("DoEEE")
                .email("JONNYDoEEE@example.com")
                .phoneNumber("6789998212")
                .build();

        when(customerAccountRepository.findCustomerAccountByCustomerAccountIdentifier_CustomerId(nonExistingCustomerId)).thenReturn(null);

        // Invoke the method
        CustomerAccountResponseModel updatedResponse = customerAccountService.updateCustomerById(nonExistingCustomerId, requestModel);

        // Verify
        assertNull(updatedResponse);
    }

    @Test
    void deleteCustomerById_WhenAccountExists_ShouldDeleteAccountAndVehicles() {
        // Arrange
        String customerId = "testCustomerId";
        CustomerAccount customerAccount = new CustomerAccount();
        when(customerAccountRepository.findCustomerAccountByCustomerAccountIdentifier_CustomerId(customerId)).thenReturn(customerAccount);

        // Act
        customerAccountService.deleteCustomerById(customerId);

        // Assert
        verify(customerAccountRepository).findCustomerAccountByCustomerAccountIdentifier_CustomerId(customerId);
        verify(customerAccountRepository).delete(customerAccount);
        verify(vehicleService).deleteAllVehiclesByCustomerId(customerId);
    }

    @Test
    void deleteCustomerById_WhenAccountNotExists_ShouldNotDelete() {
        // Arrange
        String customerId = "nonExistingCustomerId";
        when(customerAccountRepository.findCustomerAccountByCustomerAccountIdentifier_CustomerId(customerId)).thenReturn(null);

        // Act
        customerAccountService.deleteCustomerById(customerId);

        // Assert
        verify(customerAccountRepository).findCustomerAccountByCustomerAccountIdentifier_CustomerId(customerId);
        verify(customerAccountRepository, never()).delete(any());
        verify(vehicleService, never()).deleteAllVehiclesByCustomerId(any());
    }

}