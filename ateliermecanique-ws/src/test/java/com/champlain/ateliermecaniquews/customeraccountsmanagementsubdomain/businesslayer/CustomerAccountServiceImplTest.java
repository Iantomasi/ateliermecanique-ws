package com.champlain.ateliermecaniquews.customeraccountsmanagementsubdomain.businesslayer;

import com.champlain.ateliermecaniquews.authenticationsubdomain.dataLayer.ERole;
import com.champlain.ateliermecaniquews.authenticationsubdomain.dataLayer.Role;
import com.champlain.ateliermecaniquews.authenticationsubdomain.dataLayer.User;
import com.champlain.ateliermecaniquews.authenticationsubdomain.dataLayer.repositories.UserRepository;
import com.champlain.ateliermecaniquews.customeraccountsmanagementsubdomain.datamapperlayer.CustomerAccountResponseMapper;
import com.champlain.ateliermecaniquews.customeraccountsmanagementsubdomain.presentationlayer.CustomerAccountRequestModel;
import com.champlain.ateliermecaniquews.customeraccountsmanagementsubdomain.presentationlayer.CustomerAccountResponseModel;
import com.champlain.ateliermecaniquews.vehiclemanagementsubdomain.businesslayer.VehicleService;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SpringBootTest
class CustomerAccountServiceImplTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private CustomerAccountoAuthRequestMapper customerAccountoAuthRequestMapper;

    @Mock
    private CustomerAccountResponseMapper customerAccountResponseMapper;

    @InjectMocks
    private CustomerAccountServiceImpl customerAccountService;

    @Mock
    private VehicleService vehicleService;

    private final CustomerAccountoAuthRequestMapper mapper = Mappers.getMapper(CustomerAccountoAuthRequestMapper.class);


    @Test
    void getAllCustomerAccounts_shouldSucceed() {

        Set<Role> roles = new HashSet<>();
        roles.add(new Role(ERole.ROLE_CUSTOMER));

        User account = new User("Jane", "Doe", "jane@example.com", "1234567890", "testPassword",roles);


        List<User> customerAccounts = Collections.singletonList(account);
        when(userRepository.findAll()).thenReturn(customerAccounts);

        CustomerAccountResponseModel responseModel = CustomerAccountResponseModel.builder()
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
        assertEquals(responseModels.get(0).getId(), result.get(0));

        verify(userRepository, times(1)).findAll();
        verify(customerAccountResponseMapper, times(1)).entityToResponseModelList(customerAccounts);
    }

    @Test
    void getCustomerAccountByCustomerId_shouldSucceed() {
        //Arrange
        String customerId = "1";

        Set<Role> roles = new HashSet<>();
        roles.add(new Role(ERole.ROLE_CUSTOMER));


        User customerAccount = new User("Jane", "Doe", "jane@example.com", "1234567890", "testPassword",roles);

        when(userRepository.findUserByUserIdentifier_UserId(customerId)).thenReturn(customerAccount);

        CustomerAccountResponseModel responseModel = CustomerAccountResponseModel.builder()
                .id(customerId)
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
        assertEquals(customerId, result.getId());
        assertEquals("Jane", result.getFirstName());
        assertEquals("Doe", result.getLastName());
        assertEquals("jane@example.com", result.getEmail());
        assertEquals("1234567890", result.getPhoneNumber());

        verify(userRepository, times(1)).findUserByUserIdentifier_UserId(customerId);
        verify(customerAccountResponseMapper, times(1)).entityToResponseModel(customerAccount);
    }

    @Test
    void getCustomerAccountByInvalidCustomerId_shouldReturnNull() {
        String nonExistentCustomerId = "notARealId";

        when(userRepository.findUserByUserIdentifier_UserId(nonExistentCustomerId)).thenReturn(null);

        CustomerAccountResponseModel result = customerAccountService.getCustomerAccountByCustomerId(nonExistentCustomerId);

        assertNull(result);

        verify(userRepository, times(1)).findUserByUserIdentifier_UserId(nonExistentCustomerId);
    }


    @Test
    void updateCustomerAccountByCustomerId_shouldSucceed() {
        // Arrange
        String userId = "555";
        CustomerAccountRequestModel requestModel = CustomerAccountRequestModel.builder()
                .firstName("JONNY")
                .lastName("DoEEE")
                .email("JONNYDoEEE@example.com")
                .phoneNumber("6789998212")
                .build();

        User existingAccount = new User();
        existingAccount.setFirstName("John");
        existingAccount.setLastName("Doe");
        existingAccount.setEmail("johndoe@example.com");
        existingAccount.setPhoneNumber("123456789");

        when(userRepository.findUserByUserIdentifier_UserId(userId))
                .thenReturn(existingAccount);
        when(userRepository.save(any(User.class)))
                .thenReturn(existingAccount); // Mock saving the account

        CustomerAccountResponseModel expectedResponse = CustomerAccountResponseModel.builder()
                .id(userId)
                .firstName("JONNY")
                .lastName("DoEEE")
                .email("JONNYDoEEE@example.com")
                .phoneNumber("6789998212")
                .build();

        when(customerAccountResponseMapper.entityToResponseModel(any(User.class)))
                .thenReturn(expectedResponse);

        // Act
        CustomerAccountResponseModel actualResponse = customerAccountService.updateCustomerAccountByCustomerId(userId, requestModel);

        // Assert
        assertNotNull(actualResponse);
        assertEquals(expectedResponse, actualResponse);

        // Verify the interactions
        verify(userRepository).save(argThat(account ->
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

        when(userRepository.findUserByUserIdentifier_UserId(nonExistingCustomerId)).thenReturn(null);

        CustomerAccountResponseModel updatedResponse = customerAccountService.updateCustomerAccountByCustomerId(nonExistingCustomerId, requestModel);

        assertNull(updatedResponse);
    }

    @Test
    void deleteCustomerAccountAndVehiclesByCustomerId_shouldSucceed() {
        // Arrange
        String userId = "testCustomerId";
        User customerAccount = new User();
        when(userRepository.findUserByUserIdentifier_UserId(userId)).thenReturn(customerAccount);

        // Act
        customerAccountService.deleteCustomerAccountByCustomerId(userId);

        // Assert
        verify(userRepository).findAll();
        verify(userRepository).delete(customerAccount);
        verify(vehicleService).deleteAllVehiclesByCustomerId(userId);
    }

    @Test
    void deleteCustomerAccountAndVehiclesByInvalidCustomerId_shouldReturnNull() {
        // Arrange
        String userId = "nonExistingCustomerId";
        when(userRepository.findUserByUserIdentifier_UserId(userId)).thenReturn(null);

        // Act
        customerAccountService.deleteCustomerAccountByCustomerId(userId);

        // Assert
        verify(userRepository).findUserByUserIdentifier_UserId(userId);
        verify(userRepository, never()).delete(any());
        verify(vehicleService, never()).deleteAllVehiclesByCustomerId(any());
    }

    @Test
    void createCustomerAccountForoAuth_shouldSucceed() {
        // Arrange
        CustomerAccountoAuthRequestModel requestModel =CustomerAccountoAuthRequestModel.builder()
                .firstName("John")
                .lastName("Doe")
                .email("Johndoe@gmail.com")
                .role("CUSTOMER")
                .token("FakeToken")
                .build();

        Set<Role> roles = new HashSet<>();
        roles.add(new Role(ERole.ROLE_CUSTOMER));

        User account = new User( "John", "Doe", "Johndoe@gmail.com", null, null, roles);

        when(userRepository.save(any())).thenReturn(account);

        CustomerAccountResponseModel expectedResponse = CustomerAccountResponseModel.builder()
                /* build the expected response based on the saved account */
                .build();
        when(customerAccountResponseMapper.entityToResponseModel(account)).thenReturn(expectedResponse);

        // Act
        CustomerAccountResponseModel actualResponse = customerAccountService.createCustomerAccountForoAuth(requestModel);

        // Assert
        assertNotNull(actualResponse);
        assertEquals(expectedResponse, actualResponse);

        verify(userRepository, times(1)).save(any());
        verify(customerAccountResponseMapper, times(1)).entityToResponseModel(account);
    }


    @Test
    void testMappingRequestModelToEntityoAuth() {
        // Arrange
        CustomerAccountoAuthRequestModel requestModel = CustomerAccountoAuthRequestModel.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .token("fakeToken")
                .role("CUSTOMER")
                .build();

        // Act
        User entity = mapper.requestModelToEntity(requestModel);

        // Assert
        assertEquals("John", entity.getFirstName());
        assertEquals("Doe", entity.getLastName());
        assertEquals("john.doe@example.com", entity.getEmail());

    }

}

