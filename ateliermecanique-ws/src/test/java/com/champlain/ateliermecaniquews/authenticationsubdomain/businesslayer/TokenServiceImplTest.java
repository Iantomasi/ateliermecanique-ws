package com.champlain.ateliermecaniquews.authenticationsubdomain.businesslayer;

import com.nimbusds.jose.JOSEException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.*;

import org.springframework.web.client.RestTemplate;


import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class TokenServiceImplTest {

    @Mock
    private RestTemplate restTemplate;


    @InjectMocks
    private TokenServiceImpl tokenService;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        //tokenService = new TokenServiceImpl(restTemplate);
    }

    @Test
    void verifyGoogleToken_ExpiredToken_ShouldReturnExpiredMessage() throws JOSEException, ParseException {

        String result = tokenService.verifyGoogleToken("eyJhbGciOiJSUzI1NiIsImtpZCI6IjQ1NmI1MmM4MWUzNmZlYWQyNTkyMzFhNjk0N2UwNDBlMDNlYTEyNjIiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJodHRwczovL2FjY291bnRzLmdvb2dsZS5jb20iLCJhenAiOiI1Mzk2MTY3MTIyMDctYmUzMGJpYjNrYjJmZ3ZhaGRvbDBsNnV2djI2cmEydTYuYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJhdWQiOiI1Mzk2MTY3MTIyMDctYmUzMGJpYjNrYjJmZ3ZhaGRvbDBsNnV2djI2cmEydTYuYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJzdWIiOiIxMDAzODgwNzY4OTU1MDAzODc3MTgiLCJlbWFpbCI6ImNmOTMwODJAZ21haWwuY29tIiwiZW1haWxfdmVyaWZpZWQiOnRydWUsIm5iZiI6MTcwNDExNzU4OSwibmFtZSI6IkNyaXN0aWFuIEJhcnJvcyBGZXJyZWlyYSIsInBpY3R1cmUiOiJodHRwczovL2xoMy5nb29nbGV1c2VyY29udGVudC5jb20vYS9BQ2c4b2NKcjdlLVkyTXpILXd4OS0zMFFRY3pYX3lianQyM1ZDbHltMFFmNTc3RkRqV1E9czk2LWMiLCJnaXZlbl9uYW1lIjoiQ3Jpc3RpYW4iLCJmYW1pbHlfbmFtZSI6IkJhcnJvcyBGZXJyZWlyYSIsImxvY2FsZSI6ImZyLUNBIiwiaWF0IjoxNzA0MTE3ODg5LCJleHAiOjE3MDQxMjE0ODksImp0aSI6IjkwMjQwM2UyNWNkNGRlZjU2YTI0MzExZTA4Yzc4MGRkNjdjMTk4YmUifQ.h5eEAeTF50VzdkinVTA9FRzrf34YiRPja-JGDFe_pjpVIX01d9L47tEBmJQb9yJ6MPAWqLr69rpVgm4fcsPqstXHrqcpTg6ZuuQo0VqikDuk02rwCn46gP0tBPBjVZsj9d0Vsft5XapXczuFeS2bonuMFo5QRAM6qw97R7DU4i0b4G8kQdtb1D1f552I6UJyWq3vk4kIEb4-_cgYhXBXSsD9_gWdoRzE__MU1JlYr5TO5gC4wH21UWIT53QTszY6VRQoakgAtBP-p_Cv-YvLd2euKPY205TYvQWnxjKkzho11yfFYrrLMxwMcWhZi08fki5b7Fl0xe_3AmsuiGI8Cw");

        // Verify the result
        assertEquals("Token has expired.", result);
    }

    @Test
    void verifyGoogleToken_InvalidToken_ShouldReturnInvalidMessage() throws JOSEException, ParseException {

        String result = tokenService.verifyGoogleToken("token");

        // Verify the result
        assertEquals("Invalid JWT format", result);
    }

//    @Test
//    void verifyGoogleToken_InvalidToken_ShouldReturnSignatureMessage() throws JOSEException, ParseException{
//        String validToken = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6IjQ4YTYzYmM0NzY3Zjg1NTBhNTMyZGM2MzBjZjdlYjQ5ZmYzOTdlN2MifQ.eyJpc3MiOiJodHRwczovL2FjY291bnRzLmdvb2dsZS5jb20iLCJhenAiOiI1Mzk2MTY3MTIyMDctYmUzMGJpYjNrYjJmZ3ZhaGRvbDBsNnV2djI2cmEydTYuYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJhdWQiOiI1Mzk2MTY3MTIyMDctYmUzMGJpYjNrYjJmZ3ZhaGRvbDBsNnV2djI2cmEydTYuYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJzdWIiOiIxMDAzODgwNzY4OTU1MDAzODc3MTgiLCJlbWFpbCI6ImNmOTMwODJAZ21haWwuY29tIiwiZW1haWxfdmVyaWZpZWQiOnRydWUsIm5iZiI6MTcwNDIzMDM2OSwibmFtZSI6IkNyaXN0aWFuIEJhcnJvcyBGZXJyZWlyYSIsInBpY3R1cmUiOiJodHRwczovL2xoMy5nb29nbGV1c2VyY29udGVudC5jb20vYS9BQ2c4b2NKcjdlLVkyTXpILXd4OS0zMFFRY3pYX3lianQyM1ZDbHltMFFmNTc3RkRqV1E9czk2LWMiLCJnaXZlbl9uYW1lIjoiQ3Jpc3RpYW4iLCJmYW1pbHlfbmFtZSI6IkJhcnJvcyBGZXJyZWlyYSIsImxvY2FsZSI6ImZyLUNBIiwiaWF0IjoxNzA0MjMwNjY5LCJleHAiOjIwMDQyMzQyNjksImp0aSI6IjkyZDZlYzM3ZTdhMzEzZGM0YzM3OWRmYzgxZDJhMjI1YjVlNzcyMGMifQ.qLXYJn-dUDW5MzBn0NJ5_CTOZ691IP33kjlmoVYEO5Qr_Bd9IT2iO4K8oEY5bpPvuJYU_skPUf5P0aBh-QCiFcdJFYiNE-GM3Kp1ssGfzy2SnwUDk_dvL9LK4q9vdZmLSXgRV1hrf_rPYUFv9R9cxy0ZKf8AVAeY_daRT00zcAkxnxeTLJoUimp2HRcCskM1BhKEkfk8AIe130H_kNmAaElFSO6h5TNjKnkhlDyQDHAfqLjHtNKe35KPROZWBpp66LpHBQRc7Y93Dn2JqH1GozTh0HVRUtc5Iq2pyInIxH1ZBaotDZA0tIwN7MD8MHtOc9ZFHTorEnUQcugkPbPaWQ";
//
//        String result = tokenService.verifyGoogleToken(validToken);
//
//        assertEquals("Token signature verification failed.", result);
//
//    }

//    @Test
//    public void testVerifyGoogleToken_ValidToken_ShouldReturnValidMessage() throws JOSEException, ParseException {
//
//        //This token needs to be changed, log-in with google and open the inspector, go to application and choose the session that has the token
//        //paste the token here or just comment out the test if not needed for coverage.
//        String validToken = "eyJhbGciOiJSUzI1NiIsImtpZCI6IjkxNDEzY2Y0ZmEwY2I5MmEzYzNmNWEwNTQ1MDkxMzJjNDc2NjA5MzciLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJodHRwczovL2FjY291bnRzLmdvb2dsZS5jb20iLCJhenAiOiI1Mzk2MTY3MTIyMDctYmUzMGJpYjNrYjJmZ3ZhaGRvbDBsNnV2djI2cmEydTYuYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJhdWQiOiI1Mzk2MTY3MTIyMDctYmUzMGJpYjNrYjJmZ3ZhaGRvbDBsNnV2djI2cmEydTYuYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJzdWIiOiIxMDAzODgwNzY4OTU1MDAzODc3MTgiLCJlbWFpbCI6ImNmOTMwODJAZ21haWwuY29tIiwiZW1haWxfdmVyaWZpZWQiOnRydWUsIm5iZiI6MTcwNDMzODk5NSwibmFtZSI6IkNyaXN0aWFuIEJhcnJvcyBGZXJyZWlyYSIsInBpY3R1cmUiOiJodHRwczovL2xoMy5nb29nbGV1c2VyY29udGVudC5jb20vYS9BQ2c4b2NKcjdlLVkyTXpILXd4OS0zMFFRY3pYX3lianQyM1ZDbHltMFFmNTc3RkRqV1E9czk2LWMiLCJnaXZlbl9uYW1lIjoiQ3Jpc3RpYW4iLCJmYW1pbHlfbmFtZSI6IkJhcnJvcyBGZXJyZWlyYSIsImxvY2FsZSI6ImZyLUNBIiwiaWF0IjoxNzA0MzM5Mjk1LCJleHAiOjE3MDQzNDI4OTUsImp0aSI6ImU5M2U0YjFjZWMwNmJhMjU3OGY3MjgzYTM5NDAyMGQxOTY1ZjYyZmMifQ.Z7OmG6YQ5TeaUnl2gUge-5ODaCMSYwH4dalgJBmLyhOY7sQWSP_mwnr0yfMnw8WOTuBgCVX_89sp0qRyTQAUgYGa1qlQuJcl9bJ11RVXM8UMksOMmB5FibOfbgwPM7O10LGWyIwsZrZlwZsNQKb7iit8rRPjkE3QVIhpXcT5Pfgts5-f6jpGbaGfZMDzj2uFZDn5SBRATjY6pLI0iVQNwQLzbRghAdHCQ2acor1aLMV6BvaD8gmY_sytyLdc2YtmpKgw2v6Bevq2H5RYqtaCSGTIRe8Q9-Y-jfXFRdQaRBbsSZVbbNxWE9Ks88xo_HZNbnmgvyJrhz_vZPR9s1TBaQ";
//        // Test the verifyGoogleToken method with the valid token
//        String result = tokenService.verifyGoogleToken(validToken);
//
//        // Assert the expected result
//        assertEquals("Token is valid and not expired.", result);
//    }

    @Test
    public void testVerifyGoogleToken_MatchingKeyNotFound() throws JOSEException, ParseException {
        // Mocked JSON response with modified kid values
        String modifiedKeysResponse = "{ \"keys\": [ { \"kid\": \"some_other_kid_value\","+
        "\"use\": \"sig\", \"alg\": \"RS256\", \"n\": \"...\", \"e\": \"AQAB\", \"kty\": \"RSA\" },"+
                "{ \"use\": \"sig\", \"kid\": \"another_kid_value\", \"kty\": \"RSA\", \"alg\": \"RS256\", \"e\": \"AQAB\", \"n\": \"...\" } ] }";


        // Mock RestTemplate
        when(restTemplate.exchange(anyString(), (HttpMethod) any(), (HttpEntity<?>) any(), (Class<Object>) any()))
                .thenReturn(new ResponseEntity<>(modifiedKeysResponse, HttpStatus.OK));

        // Mock the token to contain a specific kid value
        String jwtToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6NDMzfQ.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyLCJleHAiOjIwMzQ3Mjk3OTB9.PgmPIkD6AedmpzOto9ewPuz2ZOcjiJAuSwCislOklQI";

        // Test the verifyGoogleToken method with the modified keys response
        String result = tokenService.verifyGoogleToken(jwtToken);

        // Assert the expected result
        assertEquals("Matching key for kid not found.", result);
    }

    @Test
    public void testVerifyGoogleToken_KidNotFoundInTokenHeader() throws JOSEException, ParseException {

        String jwtToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyLCJleHAiOjIwMzQ3Mjk3OTB9.5-wV1DpeV5ZHK4tNm-rhy_00zgvZU_vHmW2ESnH7mxw";

        // Test the verifyGoogleToken method with the modified keys response
        String result = tokenService.verifyGoogleToken(jwtToken);

        // Assert the expected result
        assertEquals("kid not found in token header.", result);
    }
    @Test
    public void testVerifyFacebookToken_ValidTokenNotExpired_ShouldReturnValidMessage() {

        // Mocked valid response for an expired token
        String mockResponse = "{\n" +
                "   \"data\": {\n" +
                "      \"app_id\": \"888035386206245\",\n" +
                "      \"type\": \"USER\",\n" +
                "      \"application\": \"ACMS\",\n" +
                "      \"data_access_expires_at\": 1712008883,\n" +
                "      \"expires_at\": 2009459200,\n" +
                "      \"is_valid\": true,\n" +
                "      \"scopes\": [\n" +
                "         \"email\",\n" +
                "         \"public_profile\"\n" +
                "      ],\n" +
                "      \"user_id\": \"3146501512161393\"\n" +
                "   }\n" +
                "}";
        ResponseEntity<String> mockResponseEntity = new ResponseEntity<>(mockResponse, HttpStatus.OK);

        // Define behavior for the mocked RestTemplate
        when(restTemplate.exchange(
                ArgumentMatchers.anyString(),
                ArgumentMatchers.eq(HttpMethod.GET),
                ArgumentMatchers.any(),
                ArgumentMatchers.eq(String.class)
        )).thenReturn(mockResponseEntity);



        //THIS ALSO NEEDS TO BE CHANGED SINCE IT WILL BE EXPIRED
        String validToken = "EAAMnqdmZBKCUBO6vEU2oCZA0Y63L9iPMLQDzQT5gLc3XJvqIZCpdvZA8f3iBhXybjJNQZBCygVIhWhkXvHtSLN7cW80HzdJKOz8T18VMZC1rmDxZADrky6qtCZBgPxzO5EZCZCa6OeuBFUGZAMuqF87awEJeLFelLyOyyOUxuGTL0GYE9h6rbZBW9zGuoAOAoHXyyiING5khZAAZBKgNZCDzQnjoHAL828Qa6EZD";

        // Test the verifyFacebookToken method with the valid token
        String result = tokenService.verifyFacebookToken(validToken);

        // Assert the expected result
        assertEquals("Token is valid and not expired.", result);
    }

    @Test
    public void testVerifyFacebookToken_Expired_ShouldReturnExpiredMessage() {

        // Mocked valid response for an expired token
        String mockResponse = "{\n" +
                "   \"data\": {\n" +
                "      \"app_id\": \"888035386206245\",\n" +
                "      \"type\": \"USER\",\n" +
                "      \"application\": \"ACMS\",\n" +
                "      \"data_access_expires_at\": 1712008883,\n" +
                "      \"expires_at\": 1609459200,\n" +
                "      \"is_valid\": true,\n" +
                "      \"scopes\": [\n" +
                "         \"email\",\n" +
                "         \"public_profile\"\n" +
                "      ],\n" +
                "      \"user_id\": \"3146501512161393\"\n" +
                "   }\n" +
                "}";
        ResponseEntity<String> mockResponseEntity = new ResponseEntity<>(mockResponse, HttpStatus.OK);

        // Define behavior for the mocked RestTemplate
        when(restTemplate.exchange(
                ArgumentMatchers.anyString(),
                ArgumentMatchers.eq(HttpMethod.GET),
                ArgumentMatchers.any(),
                ArgumentMatchers.eq(String.class)
        )).thenReturn(mockResponseEntity);

        // Expired token
        String expiredToken = "EAAMnqdmZBKCUBO6vEU2oCZA0Y63L9iPMLQDzQT5gLc3XJvqIZCpdvZA8f3iBhXybjJNQZBCygVIhWhkXvHtSLN7cW80HzdJKOz8T18VMZC1rmDxZADrky6qtCZBgPxzO5EZCZCa6OeuBFUGZAMuqF87awEJeLFelLyOyyOUxuGTL0GYE9h6rbZBW9zGuoAOAoHXyyiING5khZAAZBKgNZCDzQnjoHAL828Qa6EZD";

        // Test the verifyFacebookToken method with the expired token
        String result = tokenService.verifyFacebookToken(expiredToken);

        // Assert the expected result
        assertEquals("Token is valid but has expired.", result);
    }


    @Test
    public void testVerifyFacebookToken_INVALID_ShouldReturnInvalidMessage(){

        String mockResponse = "{\"error\":{\"message\":\"Invalid OAuth access token.\",\"type\":\"OAuthException\",\"code\":190,\"error_subcode\":1234567,\"fbtrace_id\":\"EFGH\"}}";
        ResponseEntity<String> mockResponseEntity = new ResponseEntity<>(mockResponse, HttpStatus.UNAUTHORIZED);

        // Define behavior for the mocked RestTemplate
        when(restTemplate.exchange(ArgumentMatchers.anyString(), ArgumentMatchers.eq(HttpMethod.GET), ArgumentMatchers.any(), ArgumentMatchers.eq(String.class)))
                .thenReturn(mockResponseEntity);

        String validToken = "EAAMnqdmZBKCUBO6vEU2oCZA0Y63L9iPMLQDzQT5gLc3XJvqIZCpdvZA8f3iBhXybjJNQZBCygVIhWhkXvHtSLN7cW80HzdJKOz8T18VMZC1rmDxZADrky6qtCZBgPxzO5EZCZCa6OeuBFUGZAMuqF87awEJeLFelLyOyyOUxuGTL0GYE9h6rbZBW9zGuoAOAoHXyyiING5khZAAZBKgNZCDzQnjoHAL828Qa6EZD";

        String result = tokenService.verifyFacebookToken(validToken);

        assertEquals("Failed to validate token.", result);
    }

    @Test
    public void testVerifyFacebookToken_INVALID_ShouldReturnInvalidMessage2(){

        // Mocked valid response for an expired token
        String mockResponse = "{\n" +
                "   \"data\": {\n" +
                "      \"app_id\": \"888035386206245\",\n" +
                "      \"type\": \"USER\",\n" +
                "      \"application\": \"ACMS\",\n" +
                "      \"data_access_expires_at\": 1712008883,\n" +
                "      \"expires_at\": 1609459200,\n" +
                "      \"is_valid\": false,\n" +
                "      \"scopes\": [\n" +
                "         \"email\",\n" +
                "         \"public_profile\"\n" +
                "      ],\n" +
                "      \"user_id\": \"3146501512161393\"\n" +
                "   }\n" +
                "}";
        ResponseEntity<String> mockResponseEntity = new ResponseEntity<>(mockResponse, HttpStatus.OK);

        // Define behavior for the mocked RestTemplate
        when(restTemplate.exchange(
                ArgumentMatchers.anyString(),
                ArgumentMatchers.eq(HttpMethod.GET),
                ArgumentMatchers.any(),
                ArgumentMatchers.eq(String.class)
        )).thenReturn(mockResponseEntity);

        String validToken = "EAAMnqdmZBKCUBO6vEU2oCZA0Y63L9iPMLQDzQT5gLc3XJvqIZCpdvZA8f3iBhXybjJNQZBCygVIhWhkXvHtSLN7cW80HzdJKOz8T18VMZC1rmDxZADrky6qtCZBgPxzO5EZCZCa6OeuBFUGZAMuqF87awEJeLFelLyOyyOUxuGTL0GYE9h6rbZBW9zGuoAOAoHXyyiING5khZAAZBKgNZCDzQnjoHAL828Qa6EZD";

        String result = tokenService.verifyFacebookToken(validToken);

        assertEquals("Token is not valid.", result);
    }

    @Test
    public void testVerifyInstagramToken_INVALID_ShouldReturnInvalidMessage(){

        String mockResponse = "{\"error\":{\"message\":\"Invalid OAuth access token.\",\"type\":\"OAuthException\",\"code\":190,\"error_subcode\":1234567,\"fbtrace_id\":\"EFGH\"}}";
        ResponseEntity<String> mockResponseEntity = new ResponseEntity<>(mockResponse, HttpStatus.UNAUTHORIZED);

        // Define behavior for the mocked RestTemplate
        when(restTemplate.exchange(ArgumentMatchers.anyString(), ArgumentMatchers.eq(HttpMethod.GET), ArgumentMatchers.any(), ArgumentMatchers.eq(String.class)))
                .thenReturn(mockResponseEntity);

        String validToken = "EAAMnqdmZBKCUBO6vEU2oCZA0Y63L9iPMLQDzQT5gLc3XJvqIZCpdvZA8f3iBhXybjJNQZBCygVIhWhkXvHtSLN7cW80HzdJKOz8T18VMZC1rmDxZADrky6qtCZBgPxzO5EZCZCa6OeuBFUGZAMuqF87awEJeLFelLyOyyOUxuGTL0GYE9h6rbZBW9zGuoAOAoHXyyiING5khZAAZBKgNZCDzQnjoHAL828Qa6EZD";

        String result = tokenService.verifyInstagramToken(validToken);

        assertEquals("Failed to validate Instagram token.", result);
    }

    @Test
    public void testVerifyInstagramToken_InvalidBody_ShouldReturnInvalidMessage(){

        String mockResponse = "{\"error\":{\"message\":\"Invalid OAuth access token.\",\"type\":\"OAuthException\",\"code\":190,\"error_subcode\":1234567,\"fbtrace_id\":\"EFGH\"}}";
        ResponseEntity<String> mockResponseEntity = new ResponseEntity<>(mockResponse, HttpStatus.OK);

        // Define behavior for the mocked RestTemplate
        when(restTemplate.exchange(ArgumentMatchers.anyString(), ArgumentMatchers.eq(HttpMethod.GET), ArgumentMatchers.any(), ArgumentMatchers.eq(String.class)))
                .thenReturn(mockResponseEntity);

        String validToken = "EAAMnqdmZBKCUBO6vEU2oCZA0Y63L9iPMLQDzQT5gLc3XJvqIZCpdvZA8f3iBhXybjJNQZBCygVIhWhkXvHtSLN7cW80HzdJKOz8T18VMZC1rmDxZADrky6qtCZBgPxzO5EZCZCa6OeuBFUGZAMuqF87awEJeLFelLyOyyOUxuGTL0GYE9h6rbZBW9zGuoAOAoHXyyiING5khZAAZBKgNZCDzQnjoHAL828Qa6EZD";

        String result = tokenService.verifyInstagramToken(validToken);

        assertEquals("Invalid Instagram token.", result);
    }

    @Test
    public void testVerifyInstagramToken_Valid_ShouldReturnValidMessage(){

        String mockResponse = "{\"id\":\"someId\",\"username\":\"name\"}";
        ResponseEntity<String> mockResponseEntity = new ResponseEntity<>(mockResponse, HttpStatus.OK);

        // Define behavior for the mocked RestTemplate
        when(restTemplate.exchange(ArgumentMatchers.anyString(), ArgumentMatchers.eq(HttpMethod.GET), ArgumentMatchers.any(), ArgumentMatchers.eq(String.class)))
                .thenReturn(mockResponseEntity);

        String validToken = "EAAMnqdmZBKCUBO6vEU2oCZA0Y63L9iPMLQDzQT5gLc3XJvqIZCpdvZA8f3iBhXybjJNQZBCygVIhWhkXvHtSLN7cW80HzdJKOz8T18VMZC1rmDxZADrky6qtCZBgPxzO5EZCZCa6OeuBFUGZAMuqF87awEJeLFelLyOyyOUxuGTL0GYE9h6rbZBW9zGuoAOAoHXyyiING5khZAAZBKgNZCDzQnjoHAL828Qa6EZD";

        String result = tokenService.verifyInstagramToken(validToken);

        assertEquals("Token is valid and not expired.", result);
    }


}