package com.champlain.ateliermecaniquews.authenticationsubdomain.presentationlayer;

import com.champlain.ateliermecaniquews.authenticationsubdomain.businesslayer.TokenService;
import com.champlain.ateliermecaniquews.authenticationsubdomain.businesslayer.oAuthService;
import com.champlain.ateliermecaniquews.authenticationsubdomain.utils.InvalidAccessTokenException;
import com.champlain.ateliermecaniquews.customeraccountsmanagementsubdomain.presentationlayer.CustomerAccountResponseModel;
import com.nimbusds.jose.JOSEException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class HomeControllerTest {

    private TokenService tokenService;
    private oAuthService oAuthService;
    private RestTemplate restTemplate;
    private HomeController homeController;

    @BeforeEach
    void setUp() {
        tokenService = mock(TokenService.class);
        oAuthService = mock(oAuthService.class);
        restTemplate = mock(RestTemplate.class);
        homeController = new HomeController(tokenService, oAuthService, restTemplate);
    }

    @Test
    void testVerifyGoogleToken() throws Exception {
        String validJWT = "valid.JWT.string";
        when(tokenService.verifyGoogleToken(validJWT)).thenReturn("Verification successful");

        ResponseEntity<String> response = homeController.verifyGoogleToken(validJWT);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Verification successful", response.getBody());
        verify(tokenService, times(1)).verifyGoogleToken(validJWT);
    }

    @Test
    void testGoogleLogin() throws Exception {
        String validJWT = "valid.JWT.string";
        CustomerAccountResponseModel customerAccountResponseModel =CustomerAccountResponseModel.builder()
                        .firstName("John")
                                .lastName("Doe").build();
        when(oAuthService.googleLogin(validJWT)).thenReturn(customerAccountResponseModel);

        ResponseEntity<CustomerAccountResponseModel> response = homeController.googleLogin(validJWT);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        // Add assertions for the response body if needed
        verify(oAuthService, times(1)).googleLogin(validJWT);
    }

    @Test
    void testVerifyFacebookToken() throws Exception {
        String accessToken = "valid_access_token";
        when(tokenService.verifyFacebookToken(accessToken)).thenReturn("Verification successful");

        ResponseEntity<String> response = homeController.verifyFacebookToken(accessToken);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Verification successful", response.getBody());
        verify(tokenService, times(1)).verifyFacebookToken(accessToken);
    }

    @Test
    void testFacebookToken() throws Exception {
        LoginRequestModel loginRequestModel = LoginRequestModel.builder()
                .firstName("John")
                .lastName("Doe").build();

        CustomerAccountResponseModel customerAccountResponseModel =CustomerAccountResponseModel.builder()
                .firstName("John")
                .lastName("Doe").build();
        when(oAuthService.facebookLogin(loginRequestModel)).thenReturn(customerAccountResponseModel);

        ResponseEntity<CustomerAccountResponseModel> response = homeController.facebookToken(loginRequestModel);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        verify(oAuthService, times(1)).facebookLogin(loginRequestModel);
    }

    @Test
    void testVerifyInstagramToken() throws Exception {
        String accessToken = "valid_access_token";
        when(tokenService.verifyInstagramToken(accessToken)).thenReturn("Verification successful");

        ResponseEntity<String> response = homeController.verifyInstagramToken(accessToken);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Verification successful", response.getBody());
        verify(tokenService, times(1)).verifyInstagramToken(accessToken);
    }

    @Test
    void testInstagramLogin() throws Exception {
        LoginRequestModel loginRequestModel = LoginRequestModel.builder()
                .firstName("John")
                .lastName("Doe").build();

        CustomerAccountResponseModel customerAccountResponseModel =CustomerAccountResponseModel.builder()
                .firstName("John")
                .lastName("Doe").build();
        when(oAuthService.instagramLogin(loginRequestModel)).thenReturn(customerAccountResponseModel);

        ResponseEntity<CustomerAccountResponseModel> response = homeController.instagramLogin(loginRequestModel);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        // Add assertions for the response body if needed
        verify(oAuthService, times(1)).instagramLogin(loginRequestModel);
    }

    @Test
    void testGetCustomerByToken() throws Exception {
        String accessToken = "valid_access_token";
        CustomerAccountResponseModel customerAccountResponseModel =CustomerAccountResponseModel.builder()
                .firstName("John")
                .lastName("Doe").build();
        when(oAuthService.findCustomerByToken(accessToken)).thenReturn(customerAccountResponseModel);

        ResponseEntity<CustomerAccountResponseModel> response = homeController.getCustomerByToken(accessToken);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        // Add assertions for the response body if needed
        verify(oAuthService, times(1)).findCustomerByToken(accessToken);
    }

    @Test
    void testVerifyGoogleToken_Exception() throws Exception {
        String invalidJWT = "invalid.JWT.string";
        when(tokenService.verifyGoogleToken(invalidJWT)).thenThrow(new JOSEException("Invalid JWT"));

        ResponseEntity<String> response = homeController.verifyGoogleToken(invalidJWT);

        assertEquals(422, response.getStatusCodeValue());
        assertEquals("Invalid JWT", response.getBody());
        verify(tokenService, times(1)).verifyGoogleToken(invalidJWT);
    }

    @Test
    void testGoogleLogin_Exception() throws Exception {
        String invalidJWT = "invalid.JWT.string";
        when(oAuthService.googleLogin(invalidJWT)).thenThrow(new JOSEException("Invalid JWT"));

        ResponseEntity<CustomerAccountResponseModel> response = homeController.googleLogin(invalidJWT);

        assertEquals(422, response.getStatusCodeValue());
        assertNull(response.getBody());
        verify(oAuthService, times(1)).googleLogin(invalidJWT);
    }



    // Then in the test method
    @Test
    void testVerifyFacebookToken_Exception() {
        String invalidAccessToken = "invalid_access_token";
        when(tokenService.verifyFacebookToken(invalidAccessToken)).thenThrow(new InvalidAccessTokenException("Invalid access token"));

        ResponseEntity<String> response = homeController.verifyFacebookToken(invalidAccessToken);

        assertEquals(422, response.getStatusCodeValue());
        assertEquals("Invalid access token", response.getBody());
        verify(tokenService, times(1)).verifyFacebookToken(invalidAccessToken);
    }

    @Test
    void testVerifyInstagramToken_Exception() throws Exception {
        String invalidAccessToken = "invalid_access_token";
        when(tokenService.verifyInstagramToken(invalidAccessToken)).thenThrow(new InvalidAccessTokenException("Invalid access token"));

        ResponseEntity<String> response = homeController.verifyInstagramToken(invalidAccessToken);

        assertEquals(422, response.getStatusCodeValue());
        assertEquals("Invalid access token", response.getBody());
        verify(tokenService, times(1)).verifyInstagramToken(invalidAccessToken);
    }


}