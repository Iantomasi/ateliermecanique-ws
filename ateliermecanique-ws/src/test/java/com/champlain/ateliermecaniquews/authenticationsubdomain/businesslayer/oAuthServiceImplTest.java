package com.champlain.ateliermecaniquews.authenticationsubdomain.businesslayer;

import com.champlain.ateliermecaniquews.authenticationsubdomain.presentationlayer.Payload.Request.CustomerAccountoAuthRequestModel;
import com.champlain.ateliermecaniquews.authenticationsubdomain.presentationlayer.Payload.Request.LoginRequestModel;
import com.champlain.ateliermecaniquews.customeraccountsmanagementsubdomain.businesslayer.CustomerAccountService;
import com.champlain.ateliermecaniquews.customeraccountsmanagementsubdomain.datalayer.CustomerAccount;
import com.champlain.ateliermecaniquews.customeraccountsmanagementsubdomain.datalayer.CustomerAccountRepository;
import com.champlain.ateliermecaniquews.customeraccountsmanagementsubdomain.datalayer.Role;
import com.champlain.ateliermecaniquews.customeraccountsmanagementsubdomain.datamapperlayer.CustomerAccountResponseMapper;
import com.champlain.ateliermecaniquews.customeraccountsmanagementsubdomain.presentationlayer.CustomerAccountResponseModel;
import com.nimbusds.jose.JOSEException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;


class oAuthServiceImplTest {

    @Mock
    private TokenService tokenService;

    @Mock
    private CustomerAccountRepository customerAccountRepository;

    @Mock
    private CustomerAccountService customerAccountService;

    @Mock
    private CustomerAccountResponseMapper customerAccountResponseMapper;

    @InjectMocks
    private oAuthServiceImpl oAuthService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFacebookLogin_WhenValidTokenAndAccountExists() {
        // Mock token validation result
        String token = "mocked_valid_facebook_token";
        String validatedMessage = "Token is valid and not expired.";
        when(tokenService.verifyFacebookToken(token)).thenReturn(validatedMessage);

        // Mock an existing customer account
        CustomerAccount existingCustomerAccount = new CustomerAccount();
        existingCustomerAccount.setEmail("test@example.com");

        when(customerAccountRepository.findCustomerAccountByEmail(anyString())).thenReturn(existingCustomerAccount);

        CustomerAccountResponseModel customerAccountResponseModel = CustomerAccountResponseModel.builder()
                .email("test@example.com")
                        .firstName("John")
                                .lastName("Doe")
                                        .build();
        // Mock the service method calls
        when(customerAccountService.updateCustomerToken(anyString(), anyString())).thenReturn(customerAccountResponseModel);

        // Prepare a dummy LoginRequestModel
        LoginRequestModel loginRequestModel = LoginRequestModel.builder()
                .email("test@example.com")
                .firstName("John")
                .lastName("Doe")
                .token(token)
                .build();


        // Perform the test
        CustomerAccountResponseModel response = oAuthService.facebookLogin(loginRequestModel);

        // Verify interactions and assertions
        verify(tokenService).verifyFacebookToken(token);
        verify(customerAccountRepository).findCustomerAccountByEmail(anyString());
        verify(customerAccountService).updateCustomerToken(anyString(), anyString());
        // Add assertions for the response if needed
        assertEquals(response.getLastName(),customerAccountResponseModel.getLastName());
        assertEquals(response.getFirstName(),customerAccountResponseModel.getFirstName());
        assertEquals(response.getEmail(),customerAccountResponseModel.getEmail());
        assertEquals(response.getToken(),customerAccountResponseModel.getToken());
    }

    @Test
    public void testFacebookLogin_WhenValidTokenAndAccountDoesNotExist() {
        // Mock token validation result
        String token = "mocked_valid_facebook_token";
        String validatedMessage = "Token is valid and not expired.";
        when(tokenService.verifyFacebookToken(token)).thenReturn(validatedMessage);

        // Mock that the customer account does not exist
        when(customerAccountRepository.findCustomerAccountByEmail(anyString())).thenReturn(null);

        // Mock the service method call to create a new account
        CustomerAccountResponseModel createdAccountResponse = CustomerAccountResponseModel.builder()
                .email("test@example.com")
                .firstName("John")
                .lastName("Doe")
                .build();
        when(customerAccountService.createCustomerAccountForoAuth(any(CustomerAccountoAuthRequestModel.class)))
                .thenReturn(createdAccountResponse);

        // Prepare a dummy LoginRequestModel
        LoginRequestModel loginRequestModel = LoginRequestModel.builder()
                .email("test@example.com")
                .firstName("John")
                .lastName("Doe")
                .token(token)
                .build();

        // Perform the test
        CustomerAccountResponseModel response = oAuthService.facebookLogin(loginRequestModel);

        // Verify interactions and assertions
        verify(tokenService).verifyFacebookToken(token);
        verify(customerAccountRepository).findCustomerAccountByEmail(anyString());
        verify(customerAccountService).createCustomerAccountForoAuth(any(CustomerAccountoAuthRequestModel.class));

        // Add assertions for the response
        assertNotNull(response);
        assertEquals(createdAccountResponse.getLastName(), response.getLastName());
        assertEquals(createdAccountResponse.getFirstName(), response.getFirstName());
        assertEquals(createdAccountResponse.getEmail(), response.getEmail());
        assertEquals(createdAccountResponse.getToken(), response.getToken());
    }

    @Test
    public void testFacebookLogin_WhenInvalidToken() {
        // Mock token validation result
        String token = "mocked_invalid_facebook_token";
        String invalidMessage = "Token is invalid or expired.";
        when(tokenService.verifyFacebookToken(token)).thenReturn(invalidMessage);

        // Prepare a dummy LoginRequestModel
        LoginRequestModel loginRequestModel = LoginRequestModel.builder()
                .email("test@example.com")
                .firstName("John")
                .lastName("Doe")
                .token(token)
                .build();

        // Perform the test and expect an exception to be thrown
        assertThrows(NullPointerException.class, () -> oAuthService.facebookLogin(loginRequestModel));

        // Verify interactions
        verify(tokenService).verifyFacebookToken(token);
        // Add more verifications if needed based on your implementation
    }

    @Test
    public void testInstagramLogin_WhenValidTokenAndAccountExists() {
        // Mock token validation result
        String token = "mocked_valid_instagram_token";
        String validatedMessage = "Token is valid and not expired.";
        when(tokenService.verifyInstagramToken(token)).thenReturn(validatedMessage);

        // Mock an existing customer account
        CustomerAccount existingCustomerAccount = new CustomerAccount();
        existingCustomerAccount.setEmail("test@example.com");

        when(customerAccountRepository.findCustomerAccountByEmail(anyString())).thenReturn(existingCustomerAccount);

        CustomerAccountResponseModel customerAccountResponseModel = CustomerAccountResponseModel.builder()
                .email("test@example.com")
                .firstName("John")
                .lastName("Doe")
                .build();

        // Mock the service method calls
        when(customerAccountService.updateCustomerToken(anyString(), anyString())).thenReturn(customerAccountResponseModel);

        // Prepare a dummy LoginRequestModel
        LoginRequestModel loginRequestModel = LoginRequestModel.builder()
                .email("test@example.com")
                .firstName("John")
                .lastName("Doe")
                .token(token)
                .build();

        // Perform the test
        CustomerAccountResponseModel response = oAuthService.instagramLogin(loginRequestModel);

        // Verify interactions and assertions
        verify(tokenService).verifyInstagramToken(token);
        verify(customerAccountRepository).findCustomerAccountByEmail(anyString());
        verify(customerAccountService).updateCustomerToken(anyString(), anyString());
        // Add assertions for the response if needed
        assertEquals(response.getLastName(), customerAccountResponseModel.getLastName());
        assertEquals(response.getFirstName(), customerAccountResponseModel.getFirstName());
        assertEquals(response.getEmail(), customerAccountResponseModel.getEmail());
        assertEquals(response.getToken(), customerAccountResponseModel.getToken());
    }

    @Test
    public void testInstagramLogin_WhenValidTokenAndAccountDoesNotExist() {
        // Mock token validation result
        String token = "mocked_valid_instagram_token";
        String validatedMessage = "Token is valid and not expired.";
        when(tokenService.verifyInstagramToken(token)).thenReturn(validatedMessage);

        // Mock a non-existing customer account
        when(customerAccountRepository.findCustomerAccountByEmail(anyString())).thenReturn(null);

        CustomerAccountResponseModel customerAccountResponseModel = CustomerAccountResponseModel.builder()
                .email("test@example.com")
                .firstName("John")
                .lastName("Doe")
                .build();

        // Mock the service method calls
        when(customerAccountService.createCustomerAccountForoAuth(any())).thenReturn(customerAccountResponseModel);

        // Prepare a dummy LoginRequestModel
        LoginRequestModel loginRequestModel = LoginRequestModel.builder()
                .email("test@example.com")
                .firstName("John")
                .lastName("Doe")
                .token(token)
                .build();

        // Perform the test
        CustomerAccountResponseModel response = oAuthService.instagramLogin(loginRequestModel);

        // Verify interactions and assertions
        verify(tokenService).verifyInstagramToken(token);
        verify(customerAccountRepository).findCustomerAccountByEmail(anyString());
        verify(customerAccountService).createCustomerAccountForoAuth(any());
        // Add assertions for the response if needed
        assertEquals(response.getLastName(), customerAccountResponseModel.getLastName());
        assertEquals(response.getFirstName(), customerAccountResponseModel.getFirstName());
        assertEquals(response.getEmail(), customerAccountResponseModel.getEmail());
        assertEquals(response.getToken(), customerAccountResponseModel.getToken());
    }

    @Test
    public void testInstagramLogin_WhenInvalidTokenValidation() {
        // Mock token validation result
        String token = "mocked_invalid_instagram_token";
        String invalidMessage = "Invalid token message"; // Simulating an invalid message
        when(tokenService.verifyInstagramToken(token)).thenReturn(invalidMessage);

        // Prepare a dummy LoginRequestModel
        LoginRequestModel loginRequestModel = LoginRequestModel.builder()
                .email("test@example.com")
                .firstName("John")
                .lastName("Doe")
                .token(token)
                .build();

        // Perform the test and expect an exception
        assertThrows(NullPointerException.class, () -> oAuthService.instagramLogin(loginRequestModel));

        // Verify interactions
        verify(tokenService).verifyInstagramToken(token);
        verify(customerAccountRepository, never()).findCustomerAccountByEmail(anyString());
        verify(customerAccountService, never()).createCustomerAccountForoAuth(any());
    }

    @Test
    public void testFindCustomerByToken_WhenCustomerFound() {
        // Mock a token
        String token = "mocked_valid_token";

        // Mock the customer account found by the token
        CustomerAccount mockCustomerAccount = new CustomerAccount();
        mockCustomerAccount.setEmail("test@example.com");

        when(customerAccountRepository.findCustomerAccountByToken(token)).thenReturn(mockCustomerAccount);

        // Mock the response model from the mapper
        CustomerAccountResponseModel mockResponseModel = CustomerAccountResponseModel.builder()
                .email("test@example.com")
                .firstName("John")
                .lastName("Doe")
                .build();

        when(customerAccountResponseMapper.entityToResponseModel(mockCustomerAccount)).thenReturn(mockResponseModel);

        // Perform the test
        CustomerAccountResponseModel response = oAuthService.findCustomerByToken(token);

        // Verify interactions and assertions
        verify(customerAccountRepository).findCustomerAccountByToken(token);
        verify(customerAccountResponseMapper).entityToResponseModel(mockCustomerAccount);

        assertEquals(response.getLastName(), mockResponseModel.getLastName());
        assertEquals(response.getFirstName(), mockResponseModel.getFirstName());
        assertEquals(response.getEmail(), mockResponseModel.getEmail());
    }

    @Test
    public void testFindCustomerByToken_WhenCustomerNotFound() {
        // Mock a token
        String token = "non_existent_token";

        // Mock the customer account not found by the token
        when(customerAccountRepository.findCustomerAccountByToken(token)).thenReturn(null);

        // Perform the test and assertions
        assertThrows(NullPointerException.class, () -> oAuthService.findCustomerByToken(token));

        // Verify interactions
        verify(customerAccountRepository).findCustomerAccountByToken(token);
        verifyNoInteractions(customerAccountResponseMapper);
    }

    @Test
    public void testGoogleLogin_WhenInvalidTokenValidation() throws ParseException, JOSEException {
        // Mock an invalid token
        String invalidToken = "invalid_google_token";
        when(tokenService.verifyGoogleToken(invalidToken)).thenReturn("Invalid Token");

        // Perform the test and assertions
        assertThrows(NullPointerException.class, () -> oAuthService.googleLogin(invalidToken));

        // Verify interactions
        verify(tokenService).verifyGoogleToken(invalidToken);
        verifyNoInteractions(customerAccountRepository, customerAccountService);
    }

    @Test
    public void testGoogleLogin_WhenValidTokenAndExistingAccount() throws ParseException, JOSEException {
        // Mock a valid token
        String validToken = "eyJhbGciOiJSUzI1NiIsImtpZCI6IjkxNDEzY2Y0ZmEwY2I5MmEzYzNmNWEwNTQ1MDkxMzJjNDc2NjA5MzciLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJodHRwczovL2FjY291bnRzLmdvb2dsZS5jb20iLCJhenAiOiI1Mzk2MTY3MTIyMDctYmUzMGJpYjNrYjJmZ3ZhaGRvbDBsNnV2djI2cmEydTYuYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJhdWQiOiI1Mzk2MTY3MTIyMDctYmUzMGJpYjNrYjJmZ3ZhaGRvbDBsNnV2djI2cmEydTYuYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJzdWIiOiIxMDAzODgwNzY4OTU1MDAzODc3MTgiLCJlbWFpbCI6ImNmOTMwODJAZ21haWwuY29tIiwiZW1haWxfdmVyaWZpZWQiOnRydWUsIm5iZiI6MTcwNDMzODk5NSwibmFtZSI6IkNyaXN0aWFuIEJhcnJvcyBGZXJyZWlyYSIsInBpY3R1cmUiOiJodHRwczovL2xoMy5nb29nbGV1c2VyY29udGVudC5jb20vYS9BQ2c4b2NKcjdlLVkyTXpILXd4OS0zMFFRY3pYX3lianQyM1ZDbHltMFFmNTc3RkRqV1E9czk2LWMiLCJnaXZlbl9uYW1lIjoiQ3Jpc3RpYW4iLCJmYW1pbHlfbmFtZSI6IkJhcnJvcyBGZXJyZWlyYSIsImxvY2FsZSI6ImZyLUNBIiwiaWF0IjoxNzA0MzM5Mjk1LCJleHAiOjE3MDQzNDI4OTUsImp0aSI6ImU5M2U0YjFjZWMwNmJhMjU3OGY3MjgzYTM5NDAyMGQxOTY1ZjYyZmMifQ.Z7OmG6YQ5TeaUnl2gUge-5ODaCMSYwH4dalgJBmLyhOY7sQWSP_mwnr0yfMnw8WOTuBgCVX_89sp0qRyTQAUgYGa1qlQuJcl9bJ11RVXM8UMksOMmB5FibOfbgwPM7O10LGWyIwsZrZlwZsNQKb7iit8rRPjkE3QVIhpXcT5Pfgts5-f6jpGbaGfZMDzj2uFZDn5SBRATjY6pLI0iVQNwQLzbRghAdHCQ2acor1aLMV6BvaD8gmY_sytyLdc2YtmpKgw2v6Bevq2H5RYqtaCSGTIRe8Q9-Y-jfXFRdQaRBbsSZVbbNxWE9Ks88xo_HZNbnmgvyJrhz_vZPR9s1TBaQ";
        when(tokenService.verifyGoogleToken(validToken)).thenReturn("Token is valid and not expired.");

        // Mock an existing customer account
        CustomerAccount existingCustomerAccount = new CustomerAccount();
        existingCustomerAccount.setEmail("test@example.com");
        when(customerAccountRepository.findCustomerAccountByEmail(anyString())).thenReturn(existingCustomerAccount);

        // Mock service method calls
        CustomerAccountResponseModel customerAccountResponseModel = CustomerAccountResponseModel.builder()
                .email("test@example.com")
                .firstName("John")
                .lastName("Doe")
                .build();
        when(customerAccountService.updateCustomerToken(anyString(), anyString())).thenReturn(customerAccountResponseModel);

        // Prepare a dummy JWT
        // Simulate token parts and body extraction

        // Perform the test
        CustomerAccountResponseModel response = oAuthService.googleLogin(validToken);

        // Verify interactions and assertions
        verify(tokenService).verifyGoogleToken(validToken);
        verify(customerAccountRepository).findCustomerAccountByEmail(anyString());
        verify(customerAccountService).updateCustomerToken(anyString(), anyString());
        // Add assertions for the response if needed
        assertEquals(response.getLastName(), customerAccountResponseModel.getLastName());
        assertEquals(response.getFirstName(), customerAccountResponseModel.getFirstName());
        assertEquals(response.getEmail(), customerAccountResponseModel.getEmail());
        assertEquals(response.getToken(), customerAccountResponseModel.getToken());
    }

    @Test
    public void testGoogleLogin_WhenValidTokenAndAccountNotFoundByEmail() throws ParseException, JOSEException {
        // Mock a valid token
        String validToken = "eyJhbGciOiJSUzI1NiIsImtpZCI6IjkxNDEzY2Y0ZmEwY2I5MmEzYzNmNWEwNTQ1MDkxMzJjNDc2NjA5MzciLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJodHRwczovL2FjY291bnRzLmdvb2dsZS5jb20iLCJhenAiOiI1Mzk2MTY3MTIyMDctYmUzMGJpYjNrYjJmZ3ZhaGRvbDBsNnV2djI2cmEydTYuYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJhdWQiOiI1Mzk2MTY3MTIyMDctYmUzMGJpYjNrYjJmZ3ZhaGRvbDBsNnV2djI2cmEydTYuYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJzdWIiOiIxMDAzODgwNzY4OTU1MDAzODc3MTgiLCJlbWFpbCI6ImNmOTMwODJAZ21haWwuY29tIiwiZW1haWxfdmVyaWZpZWQiOnRydWUsIm5iZiI6MTcwNDMzODk5NSwibmFtZSI6IkNyaXN0aWFuIEJhcnJvcyBGZXJyZWlyYSIsInBpY3R1cmUiOiJodHRwczovL2xoMy5nb29nbGV1c2VyY29udGVudC5jb20vYS9BQ2c4b2NKcjdlLVkyTXpILXd4OS0zMFFRY3pYX3lianQyM1ZDbHltMFFmNTc3RkRqV1E9czk2LWMiLCJnaXZlbl9uYW1lIjoiQ3Jpc3RpYW4iLCJmYW1pbHlfbmFtZSI6IkJhcnJvcyBGZXJyZWlyYSIsImxvY2FsZSI6ImZyLUNBIiwiaWF0IjoxNzA0MzM5Mjk1LCJleHAiOjE3MDQzNDI4OTUsImp0aSI6ImU5M2U0YjFjZWMwNmJhMjU3OGY3MjgzYTM5NDAyMGQxOTY1ZjYyZmMifQ.Z7OmG6YQ5TeaUnl2gUge-5ODaCMSYwH4dalgJBmLyhOY7sQWSP_mwnr0yfMnw8WOTuBgCVX_89sp0qRyTQAUgYGa1qlQuJcl9bJ11RVXM8UMksOMmB5FibOfbgwPM7O10LGWyIwsZrZlwZsNQKb7iit8rRPjkE3QVIhpXcT5Pfgts5-f6jpGbaGfZMDzj2uFZDn5SBRATjY6pLI0iVQNwQLzbRghAdHCQ2acor1aLMV6BvaD8gmY_sytyLdc2YtmpKgw2v6Bevq2H5RYqtaCSGTIRe8Q9-Y-jfXFRdQaRBbsSZVbbNxWE9Ks88xo_HZNbnmgvyJrhz_vZPR9s1TBaQ";
        when(tokenService.verifyGoogleToken(validToken)).thenReturn("Token is valid and not expired.");

        // Mock account not found by email
        when(customerAccountRepository.findCustomerAccountByEmail(anyString())).thenReturn(null);

        // Mock service method calls
        CustomerAccountoAuthRequestModel requestModel = CustomerAccountoAuthRequestModel.builder()
                .email("cf93082@gmail.com")
                .firstName("Cristian")
                .lastName("Barros Ferreira")
                .token(validToken)
                .role(String.valueOf(Role.CUSTOMER))
                .build();
        CustomerAccountResponseModel customerAccountResponseModel = CustomerAccountResponseModel.builder()
                .email("cf93082@gmail.com")
                .firstName("Cristian")
                .lastName("Barros Ferreira")
                .build();
        when(customerAccountService.createCustomerAccountForoAuth(requestModel)).thenReturn(customerAccountResponseModel);

        // Perform the test
        CustomerAccountResponseModel response = oAuthService.googleLogin(validToken);

        // Verify interactions and assertions
        verify(tokenService).verifyGoogleToken(validToken);
        verify(customerAccountRepository).findCustomerAccountByEmail(anyString());
        verify(customerAccountService).createCustomerAccountForoAuth(requestModel);
        // Add assertions for the response if needed
        assertEquals(response.getLastName(), customerAccountResponseModel.getLastName());
        assertEquals(response.getFirstName(), customerAccountResponseModel.getFirstName());
        assertEquals(response.getEmail(), customerAccountResponseModel.getEmail());
        assertEquals(response.getToken(), customerAccountResponseModel.getToken());
    }


}