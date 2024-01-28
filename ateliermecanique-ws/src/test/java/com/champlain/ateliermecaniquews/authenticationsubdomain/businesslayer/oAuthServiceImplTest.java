package com.champlain.ateliermecaniquews.authenticationsubdomain.businesslayer;

import com.champlain.ateliermecaniquews.authenticationsubdomain.dataLayer.ERole;
import com.champlain.ateliermecaniquews.authenticationsubdomain.dataLayer.Role;
import com.champlain.ateliermecaniquews.authenticationsubdomain.dataLayer.User;
import com.champlain.ateliermecaniquews.authenticationsubdomain.dataLayer.UserIdentifier;
import com.champlain.ateliermecaniquews.authenticationsubdomain.dataLayer.repositories.RoleRepository;
import com.champlain.ateliermecaniquews.authenticationsubdomain.dataLayer.repositories.UserRepository;
import com.champlain.ateliermecaniquews.customeraccountsmanagementsubdomain.businesslayer.CustomerAccountService;
import com.champlain.ateliermecaniquews.customeraccountsmanagementsubdomain.datamapperlayer.CustomerAccountResponseMapper;
import com.nimbusds.jose.JOSEException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.text.ParseException;
import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class oAuthServiceImplTest {

    @Mock
    private TokenService tokenService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private CustomerAccountService customerAccountService;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private CustomerAccountResponseMapper customerAccountResponseMapper;

    @InjectMocks
    private oAuthServiceImpl oAuthService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void facebookLogin_WhenValidTokenAndUserExists_ShouldReturnExistingUser() {
        // Mock token validation result
        String token = "mocked_valid_facebook_token";
        when(tokenService.verifyFacebookToken(token)).thenReturn("Token is valid and not expired.");

        // Mock Facebook API response
        String facebookUserProfileUrl = "https://graph.facebook.com/v12.0/me?fields=email,first_name,last_name,picture&access_token=" + token;
        String responseBody = "{ \"email\": \"test@example.com\", \"first_name\": \"John\", \"last_name\": \"Doe\", \"picture\": { \"data\": { \"url\": \"picture_url\" } } }";
        ResponseEntity<String> responseEntity = new ResponseEntity<>(responseBody, HttpStatus.OK);
        when(restTemplate.exchange(eq(facebookUserProfileUrl), eq(HttpMethod.GET), any(), eq(String.class)))
                .thenReturn(responseEntity);

        // Mock an existing user
        User existingUser = User.builder()
                .userIdentifier(new UserIdentifier())
                .email("test@example.com")
                .firstName("John")
                .lastName("Doe")
                .roles(new HashSet<>())
                .picture("picture_url")
                .build();
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(existingUser));

        // Perform the test
        User result = oAuthService.facebookLogin(token);

        // Verify interactions and assertions
        verify(tokenService).verifyFacebookToken(token);
        verify(restTemplate).exchange(eq(facebookUserProfileUrl), eq(HttpMethod.GET), any(), eq(String.class));
        verify(userRepository).findByEmail("test@example.com");
        verify(userRepository, never()).save(any());
        assertNotNull(result);
        assertEquals(existingUser, result);
    }
    @Test
    void facebookLogin_WhenValidTokenAndUserDoesNotExist_ShouldCreateNewUser() {
        // Mock token validation result
        String token = "mocked_valid_facebook_token";
        when(tokenService.verifyFacebookToken(token)).thenReturn("Token is valid and not expired.");

        // Mock Facebook API response
        String facebookUserProfileUrl = "https://graph.facebook.com/v12.0/me?fields=email,first_name,last_name,picture&access_token=" + token;
        String responseBody = "{ \"email\": \"newuser@example.com\", \"first_name\": \"Jane\", \"last_name\": \"Doe\", \"picture\": { \"data\": { \"url\": \"picture_url\" } } }";
        ResponseEntity<String> responseEntity = new ResponseEntity<>(responseBody, HttpStatus.OK);
        when(restTemplate.exchange(eq(facebookUserProfileUrl), eq(HttpMethod.GET), any(), eq(String.class)))
                .thenReturn(responseEntity);

        // Mock a non-existing user
        when(userRepository.findByEmail("newuser@example.com")).thenReturn(Optional.empty());

        // Mock role retrieval
        Role customerRole = Role.builder().name(ERole.ROLE_CUSTOMER).build();
        when(roleRepository.findByName(ERole.ROLE_CUSTOMER)).thenReturn(Optional.of(customerRole));

        // Perform the test
        User result = oAuthService.facebookLogin(token);

        // Verify interactions and assertions
        verify(tokenService).verifyFacebookToken(token);
        verify(restTemplate).exchange(eq(facebookUserProfileUrl), eq(HttpMethod.GET), any(), eq(String.class));
        verify(userRepository).findByEmail("newuser@example.com");
        verify(userRepository).save(any(User.class));
        assertNotNull(result);
        assertEquals("newuser@example.com", result.getEmail());
        assertEquals("Jane", result.getFirstName());
        assertEquals("Doe", result.getLastName());
        assertEquals(1, result.getRoles().size());
        assertTrue(result.getRoles().contains(customerRole));
    }

    @Test
    void facebookLogin_WhenInvalidToken_ShouldThrowException() {
        // Mock token validation result
        String token = "mocked_invalid_facebook_token";
        when(tokenService.verifyFacebookToken(token)).thenReturn("Token is invalid or expired.");

        // Perform the test and expect an exception to be thrown
        assertThrows(NullPointerException.class, () -> oAuthService.facebookLogin(token));

        // Verify interactions
        verify(tokenService).verifyFacebookToken(token);
        // Add more verifications if needed based on your implementation
    }

//    @Test
//    public void testInstagramLogin_WhenValidTokenAndAccountExists() {
//        // Mock token validation result
//        String token = "mocked_valid_instagram_token";
//        String validatedMessage = "Token is valid and not expired.";
//        when(tokenService.verifyInstagramToken(token)).thenReturn(validatedMessage);
//
//        // Mock an existing customer account
//        User existingCustomerAccount = new User();
//        existingCustomerAccount.setEmail("test@example.com");
//
//        when(userRepository.findUserByUserIdentifier_UserId(anyString())).thenReturn(existingCustomerAccount);
//
//        CustomerAccountResponseModel customerAccountResponseModel = CustomerAccountResponseModel.builder()
//                .email("test@example.com")
//                .firstName("John")
//                .lastName("Doe")
//                .build();
//
//        // Prepare a dummy LoginRequestModel
//        LoginRequestModel loginRequestModel = LoginRequestModel.builder()
//                .email("test@example.com")
//                .firstName("John")
//                .lastName("Doe")
//                .token(token)
//                .build();
//
//        // Perform the test
//        CustomerAccountResponseModel response = oAuthService.instagramLogin(loginRequestModel);
//
//        // Verify interactions and assertions
//        verify(tokenService).verifyInstagramToken(token);
//        verify(userRepository).findByEmail(anyString());
//        // Add assertions for the response if needed
//        assertEquals(response.getLastName(), customerAccountResponseModel.getLastName());
//        assertEquals(response.getFirstName(), customerAccountResponseModel.getFirstName());
//        assertEquals(response.getEmail(), customerAccountResponseModel.getEmail());
//    }
//
//    @Test
//    public void testInstagramLogin_WhenValidTokenAndAccountDoesNotExist() {
//        // Mock token validation result
//        String token = "mocked_valid_instagram_token";
//        String validatedMessage = "Token is valid and not expired.";
//        when(tokenService.verifyInstagramToken(token)).thenReturn(validatedMessage);
//
//        // Mock a non-existing customer account
//        when(userRepository.findByEmail(anyString())).thenReturn(null);
//
//        CustomerAccountResponseModel customerAccountResponseModel = CustomerAccountResponseModel.builder()
//                .email("test@example.com")
//                .firstName("John")
//                .lastName("Doe")
//                .build();
//
//        // Mock the service method calls
//        when(customerAccountService.createCustomerAccountForoAuth(any())).thenReturn(customerAccountResponseModel);
//
//        // Prepare a dummy LoginRequestModel
//        LoginRequestModel loginRequestModel = LoginRequestModel.builder()
//                .email("test@example.com")
//                .firstName("John")
//                .lastName("Doe")
//                .token(token)
//                .build();
//
//        // Perform the test
//        CustomerAccountResponseModel response = oAuthService.instagramLogin(loginRequestModel);
//
//        // Verify interactions and assertions
//        verify(tokenService).verifyInstagramToken(token);
//        verify(userRepository).findByEmail(anyString());
//        verify(customerAccountService).createCustomerAccountForoAuth(any());
//        // Add assertions for the response if needed
//        assertEquals(response.getLastName(), customerAccountResponseModel.getLastName());
//        assertEquals(response.getFirstName(), customerAccountResponseModel.getFirstName());
//        assertEquals(response.getEmail(), customerAccountResponseModel.getEmail());
//    }
//
//    @Test
//    public void testInstagramLogin_WhenInvalidTokenValidation() {
//        // Mock token validation result
//        String token = "mocked_invalid_instagram_token";
//        String invalidMessage = "Invalid token message"; // Simulating an invalid message
//        when(tokenService.verifyInstagramToken(token)).thenReturn(invalidMessage);
//
//        // Prepare a dummy LoginRequestModel
//        LoginRequestModel loginRequestModel = LoginRequestModel.builder()
//                .email("test@example.com")
//                .firstName("John")
//                .lastName("Doe")
//                .token(token)
//                .build();
//
//        // Perform the test and expect an exception
//        assertThrows(NullPointerException.class, () -> oAuthService.instagramLogin(loginRequestModel));
//
//        // Verify interactions
//        verify(tokenService).verifyInstagramToken(token);
//        verify(userRepository, never()).findByEmail(anyString());
//        verify(customerAccountService, never()).createCustomerAccountForoAuth(any());
//    }
//
//

    @Test
    public void testGoogleLogin_WhenInvalidTokenValidation() throws ParseException, JOSEException {
        // Mock an invalid token
        String invalidToken = "invalid_google_token";
        when(tokenService.verifyGoogleToken(invalidToken)).thenReturn("Invalid Token");

        // Perform the test and assertions
        assertThrows(NullPointerException.class, () -> oAuthService.googleLogin(invalidToken));

        // Verify interactions
        verify(tokenService).verifyGoogleToken(invalidToken);
        verifyNoInteractions(userRepository, customerAccountService);
    }
    @Test
    void googleLogin_WhenValidTokenAndUserExists_ShouldReturnExistingUser() throws ParseException, JOSEException {
        // Mock a valid token
        String validToken = "eyJhbGciOiJSUzI1NiIsImtpZCI6IjkxNDEzY2Y0ZmEwY2I5MmEzYzNmNWEwNTQ1MDkxMzJjNDc2NjA5MzciLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJodHRwczovL2FjY291bnRzLmdvb2dsZS5jb20iLCJhenAiOiI1Mzk2MTY3MTIyMDctYmUzMGJpYjNrYjJmZ3ZhaGRvbDBsNnV2djI2cmEydTYuYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJhdWQiOiI1Mzk2MTY3MTIyMDctYmUzMGJpYjNrYjJmZ3ZhaGRvbDBsNnV2djI2cmEydTYuYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJzdWIiOiIxMDAzODgwNzY4OTU1MDAzODc3MTgiLCJlbWFpbCI6ImNmOTMwODJAZ21haWwuY29tIiwiZW1haWxfdmVyaWZpZWQiOnRydWUsIm5iZiI6MTcwNDMzODk5NSwibmFtZSI6IkNyaXN0aWFuIEJhcnJvcyBGZXJyZWlyYSIsInBpY3R1cmUiOiJodHRwczovL2xoMy5nb29nbGV1c2VyY29udGVudC5jb20vYS9BQ2c4b2NKcjdlLVkyTXpILXd4OS0zMFFRY3pYX3lianQyM1ZDbHltMFFmNTc3RkRqV1E9czk2LWMiLCJnaXZlbl9uYW1lIjoiQ3Jpc3RpYW4iLCJmYW1pbHlfbmFtZSI6IkJhcnJvcyBGZXJyZWlyYSIsImxvY2FsZSI6ImZyLUNBIiwiaWF0IjoxNzA0MzM5Mjk1LCJleHAiOjE3MDQzNDI4OTUsImp0aSI6ImU5M2U0YjFjZWMwNmJhMjU3OGY3MjgzYTM5NDAyMGQxOTY1ZjYyZmMifQ.Z7OmG6YQ5TeaUnl2gUge-5ODaCMSYwH4dalgJBmLyhOY7sQWSP_mwnr0yfMnw8WOTuBgCVX_89sp0qRyTQAUgYGa1qlQuJcl9bJ11RVXM8UMksOMmB5FibOfbgwPM7O10LGWyIwsZrZlwZsNQKb7iit8rRPjkE3QVIhpXcT5Pfgts5-f6jpGbaGfZMDzj2uFZDn5SBRATjY6pLI0iVQNwQLzbRghAdHCQ2acor1aLMV6BvaD8gmY_sytyLdc2YtmpKgw2v6Bevq2H5RYqtaCSGTIRe8Q9-Y-jfXFRdQaRBbsSZVbbNxWE9Ks88xo_HZNbnmgvyJrhz_vZPR9s1TBaQ";
        when(tokenService.verifyGoogleToken(validToken)).thenReturn("Token is valid and not expired.");

        // Mock token parts and body
        // Simulate token parts and body extraction

        // Mock an existing user
        User existingUser = User.builder()
                .userIdentifier(new UserIdentifier())
                .email("cf93082@gmail.com")
                .firstName("Cristian")
                .lastName("Barros Ferreira")
                .roles(new HashSet<>())
                .picture(null)
                .build();
        when(userRepository.findByEmail("cf93082@gmail.com")).thenReturn(Optional.of(existingUser));

        // Perform the test
        User result = oAuthService.googleLogin(validToken);

        // Verify interactions and assertions
        verify(tokenService).verifyGoogleToken(validToken);
        verify(userRepository).findByEmail("cf93082@gmail.com");
        verify(userRepository, never()).save(any());


        assertNotNull(result);
        assertEquals(existingUser, result);
    }

    @Test
    void googleLogin_WhenValidTokenAndUserDoesNotExist_ShouldCreateNewUser() throws ParseException, JOSEException {
        // Mock a valid token
        String validToken = "eyJhbGciOiJSUzI1NiIsImtpZCI6IjkxNDEzY2Y0ZmEwY2I5MmEzYzNmNWEwNTQ1MDkxMzJjNDc2NjA5MzciLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJodHRwczovL2FjY291bnRzLmdvb2dsZS5jb20iLCJhenAiOiI1Mzk2MTY3MTIyMDctYmUzMGJpYjNrYjJmZ3ZhaGRvbDBsNnV2djI2cmEydTYuYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJhdWQiOiI1Mzk2MTY3MTIyMDctYmUzMGJpYjNrYjJmZ3ZhaGRvbDBsNnV2djI2cmEydTYuYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJzdWIiOiIxMDAzODgwNzY4OTU1MDAzODc3MTgiLCJlbWFpbCI6ImNmOTMwODJAZ21haWwuY29tIiwiZW1haWxfdmVyaWZpZWQiOnRydWUsIm5iZiI6MTcwNDMzODk5NSwibmFtZSI6IkNyaXN0aWFuIEJhcnJvcyBGZXJyZWlyYSIsInBpY3R1cmUiOiJodHRwczovL2xoMy5nb29nbGV1c2VyY29udGVudC5jb20vYS9BQ2c4b2NKcjdlLVkyTXpILXd4OS0zMFFRY3pYX3lianQyM1ZDbHltMFFmNTc3RkRqV1E9czk2LWMiLCJnaXZlbl9uYW1lIjoiQ3Jpc3RpYW4iLCJmYW1pbHlfbmFtZSI6IkJhcnJvcyBGZXJyZWlyYSIsImxvY2FsZSI6ImZyLUNBIiwiaWF0IjoxNzA0MzM5Mjk1LCJleHAiOjE3MDQzNDI4OTUsImp0aSI6ImU5M2U0YjFjZWMwNmJhMjU3OGY3MjgzYTM5NDAyMGQxOTY1ZjYyZmMifQ.Z7OmG6YQ5TeaUnl2gUge-5ODaCMSYwH4dalgJBmLyhOY7sQWSP_mwnr0yfMnw8WOTuBgCVX_89sp0qRyTQAUgYGa1qlQuJcl9bJ11RVXM8UMksOMmB5FibOfbgwPM7O10LGWyIwsZrZlwZsNQKb7iit8rRPjkE3QVIhpXcT5Pfgts5-f6jpGbaGfZMDzj2uFZDn5SBRATjY6pLI0iVQNwQLzbRghAdHCQ2acor1aLMV6BvaD8gmY_sytyLdc2YtmpKgw2v6Bevq2H5RYqtaCSGTIRe8Q9-Y-jfXFRdQaRBbsSZVbbNxWE9Ks88xo_HZNbnmgvyJrhz_vZPR9s1TBaQ";
        when(tokenService.verifyGoogleToken(validToken)).thenReturn("Token is valid and not expired.");

        // Mock token parts and body
        // Simulate token parts and body extraction

        // Mock a non-existing user
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        // Mock role retrieval
        Role customerRole = Role.builder().name(ERole.ROLE_CUSTOMER).build();
        when(roleRepository.findByName(ERole.ROLE_CUSTOMER)).thenReturn(Optional.of(customerRole));

        // Perform the test
        User result = oAuthService.googleLogin(validToken);

        // Verify interactions and assertions
        verify(tokenService).verifyGoogleToken(validToken);
        verify(userRepository).findByEmail(anyString());
        verify(userRepository).save(any(User.class));


        assertNotNull(result);
        assertEquals("cf93082@gmail.com", result.getEmail());
        assertEquals("Cristian", result.getFirstName());
        assertEquals("Barros Ferreira", result.getLastName());
        assertEquals(1, result.getRoles().size());
        assertTrue(result.getRoles().contains(customerRole));
    }


}