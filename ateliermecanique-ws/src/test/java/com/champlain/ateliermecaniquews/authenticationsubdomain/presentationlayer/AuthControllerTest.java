package com.champlain.ateliermecaniquews.authenticationsubdomain.presentationlayer;

import com.champlain.ateliermecaniquews.authenticationsubdomain.businesslayer.oAuthService;
import com.champlain.ateliermecaniquews.authenticationsubdomain.dataLayer.ERole;
import com.champlain.ateliermecaniquews.authenticationsubdomain.dataLayer.Role;
import com.champlain.ateliermecaniquews.authenticationsubdomain.dataLayer.User;
import com.champlain.ateliermecaniquews.authenticationsubdomain.dataLayer.UserIdentifier;
import com.champlain.ateliermecaniquews.authenticationsubdomain.dataLayer.repositories.RoleRepository;
import com.champlain.ateliermecaniquews.authenticationsubdomain.dataLayer.repositories.UserRepository;
import com.champlain.ateliermecaniquews.authenticationsubdomain.presentationlayer.Payload.Request.LoginRequest;
import com.champlain.ateliermecaniquews.authenticationsubdomain.presentationlayer.Payload.Request.SignupRequest;
import com.champlain.ateliermecaniquews.authenticationsubdomain.presentationlayer.Payload.Response.JWTResponse;
import com.champlain.ateliermecaniquews.authenticationsubdomain.presentationlayer.Payload.Response.MessageResponse;
import com.champlain.ateliermecaniquews.authenticationsubdomain.presentationlayer.controllers.AuthController;
import com.champlain.ateliermecaniquews.authenticationsubdomain.utils.security.jwt.JwtUtils;
import com.champlain.ateliermecaniquews.authenticationsubdomain.utils.security.services.UserDetailsImpl;
import com.champlain.ateliermecaniquews.customeraccountsmanagementsubdomain.businesslayer.CustomerAccountService;
import com.champlain.ateliermecaniquews.customeraccountsmanagementsubdomain.presentationlayer.CustomerAccountResponseModel;
import com.nimbusds.jose.JOSEException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;

import java.text.ParseException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @InjectMocks
    private AuthController authController;

    @Mock
    private oAuthService oAuthService;

    private MockMvc mockMvc;

    @Mock
    private JwtUtils jwtUtils;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private CustomerAccountService customerAccountService;

    @Mock
    private UserDetailsService userDetailsService;


    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
    }

    @Test
    void authenticateUser_WhenValidCredentials_ShouldReturnJwtResponse() {
        // Mock data
        LoginRequest loginRequest = new LoginRequest("test@example.com", "password");
        AuthenticationManager authenticationManager = mock(AuthenticationManager.class);
        JwtUtils jwtUtils = mock(JwtUtils.class);
        UserDetailsImpl userDetails = new UserDetailsImpl(1, "uuid", "John", "Doe", "123456789", "test@example.com", "password", Collections.emptyList());
        Authentication authentication = mock(Authentication.class);

        when(authenticationManager.authenticate(any())).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(jwtUtils.generateJwtToken(authentication)).thenReturn("mockedJwtToken");

        // Call the method
        AuthController authController = new AuthController(null, null, null, authenticationManager, null, null, jwtUtils, null);
        ResponseEntity<?> responseEntity = authController.authenticateUser(loginRequest);

        // Verify interactions and assertions
        assertNotNull(responseEntity);
        assertEquals(200, responseEntity.getStatusCodeValue());

        JWTResponse jwtResponse = (JWTResponse) responseEntity.getBody();
        assertNotNull(jwtResponse);
        assertEquals("mockedJwtToken", jwtResponse.getToken());
        assertEquals("uuid", jwtResponse.getId());
        assertEquals("John", jwtResponse.getFirstName());
        assertEquals("Doe", jwtResponse.getLastName());
        assertEquals("123456789", jwtResponse.getPhoneNumber());
        assertEquals("test@example.com", jwtResponse.getEmail());
        assertNull(jwtResponse.getPicture());
        assertTrue(jwtResponse.getRoles().isEmpty());
    }

    @Test
    void registerUser_WhenValidSignupRequest_ShouldReturnSuccessMessage() {
        // Mock data
        SignupRequest signupRequest = SignupRequest.builder()
                .firstName("John")
                .lastName("Doe")
                .phoneNumber("123456789")
                .email("test@example.com")
                .password("password")
                .build();
        UserRepository userRepository = mock(UserRepository.class);
        RoleRepository roleRepository = mock(RoleRepository.class);
        PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);

        when(userRepository.existsByEmail(signupRequest.getEmail())).thenReturn(false);

        Role userRole = Role.builder().name(ERole.ROLE_CUSTOMER).build();
        when(roleRepository.findByName(ERole.ROLE_CUSTOMER)).thenReturn(java.util.Optional.of(userRole));

        User savedUser = User.builder().userIdentifier(new UserIdentifier()).firstName("John").lastName("Doe").phoneNumber("123456789").email("test@example.com").password("encodedPassword").build();
        when(userRepository.save(any(User.class))).thenReturn(savedUser);
        when(passwordEncoder.encode(signupRequest.getPassword())).thenReturn("encodedPassword");

        // Call the method
        AuthController authController = new AuthController(null, null, userRepository, null, roleRepository, passwordEncoder, null, null);
        ResponseEntity<?> responseEntity = authController.registerUser(signupRequest);

        // Verify interactions and assertions
        assertNotNull(responseEntity);
        assertEquals(200, responseEntity.getStatusCodeValue());

        MessageResponse messageResponse = (MessageResponse) responseEntity.getBody();
        assertNotNull(messageResponse);
        assertEquals("User registered successfully!", messageResponse.getMessage());
    }

    @Test
    void registerUser_WhenEmailAlreadyInUse_ShouldReturnBadRequest() {
        // Mock data
        SignupRequest signupRequest = SignupRequest.builder()
                .firstName("John")
                .lastName("Doe")
                .phoneNumber("123456789")
                .email("test@example.com")
                .password("password")
                .build();
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.existsByEmail(signupRequest.getEmail())).thenReturn(true);

        // Call the method
        AuthController authController = new AuthController(null, null, userRepository, null, null, null, null, null);
        ResponseEntity<?> responseEntity = authController.registerUser(signupRequest);

        // Verify interactions and assertions
        assertNotNull(responseEntity);
        assertEquals(400, responseEntity.getStatusCodeValue());

        MessageResponse messageResponse = (MessageResponse) responseEntity.getBody();
        assertNotNull(messageResponse);
        assertEquals("Error: email is already in use!", messageResponse.getMessage());

        // Ensure userRepository.save is not called
        verify(userRepository, never()).save(any(User.class));
    }

//    @Test
//    void registerUser_WhenRoleNotFound_ShouldReturnInternalServerError() {
//        // Mock data
//        SignupRequest signupRequest = SignupRequest.builder()
//                .firstName("John")
//                .lastName("Doe")
//                .phoneNumber("123456789")
//                .email("test@example.com")
//                .role(new HashSet<>(Collections.singletonList("invalidRole")))
//                .password("password")
//                .build();
//        UserRepository userRepository = mock(UserRepository.class);
//        RoleRepository roleRepository = mock(RoleRepository.class);
//        when(userRepository.existsByEmail(signupRequest.getEmail())).thenReturn(false);
//        when(roleRepository.findByName(ERole.ROLE_CUSTOMER)).thenReturn(Optional.empty());
//
//        // Call the method
//        AuthController authController = new AuthController(null, null, userRepository, null, roleRepository, null, null, null);
//        ResponseEntity<?> responseEntity = authController.registerUser(signupRequest);
//
//        // Verify interactions and assertions
//        assertNotNull(responseEntity);
//        assertEquals(500, responseEntity.getStatusCodeValue());
//
//        MessageResponse messageResponse = (MessageResponse) responseEntity.getBody();
//        assertNotNull(messageResponse);
//        assertEquals("Error: Role is not found", messageResponse.getMessage());
//
//        // Ensure userRepository.save is not called
//        verify(userRepository, never()).save(any(User.class));
//    }

    @Test
    void googleLogin_WhenValidTokenAndUserExists_ShouldReturnJWTResponse() throws ParseException, JOSEException {
        // Mock
        User existingUser = createUser();
        when(oAuthService.googleLogin(anyString())).thenReturn(existingUser);
        when(userDetailsService.loadUserByUsername(anyString())).thenReturn(createUserDetails(existingUser));

        // Test
        ResponseEntity<?> responseEntity = authController.googleLogin("validToken");

        // Verify
        assertNotNull(responseEntity);
        assertEquals(200, responseEntity.getStatusCodeValue());
        assertTrue(responseEntity.getBody() instanceof JWTResponse);
        JWTResponse jwtResponse = (JWTResponse) responseEntity.getBody();
        assertEquals(existingUser.getEmail(), jwtResponse.getEmail());
    }

    @Test
    void googleLogin_WhenValidTokenAndUserDoesNotExist_ShouldReturnJWTResponse() throws ParseException, JOSEException {
        // Mock
        when(oAuthService.googleLogin(anyString())).thenReturn(createUser());
        when(userDetailsService.loadUserByUsername(anyString())).thenReturn(createUserDetails(null));

        // Test
        ResponseEntity<?> responseEntity = authController.googleLogin("validToken");

        // Verify
        assertNotNull(responseEntity);
        assertEquals(200, responseEntity.getStatusCodeValue());
        assertTrue(responseEntity.getBody() instanceof JWTResponse);
        JWTResponse jwtResponse = (JWTResponse) responseEntity.getBody();
        assertNotNull(jwtResponse.getEmail());
    }

    @Test
    void googleLogin_WhenExceptionThrown_ShouldReturnUnprocessableEntity() throws ParseException, JOSEException {
        // Mock
        when(oAuthService.googleLogin(anyString())).thenThrow(new ParseException("Error", 0));

        // Test
        ResponseEntity<?> responseEntity = authController.googleLogin("invalidToken");

        // Verify
        assertNotNull(responseEntity);
        assertEquals(422, responseEntity.getStatusCodeValue());
        assertNull(responseEntity.getBody());
    }

    @Test
    void facebookToken_WhenValidToken_ShouldReturnJWTResponse() {
        // Mock
        when(oAuthService.facebookLogin(anyString())).thenReturn(createUser());
        when(userDetailsService.loadUserByUsername(anyString())).thenReturn(createUserDetails(createUser()));

        // Test
        ResponseEntity<?> responseEntity = authController.facebookToken("validToken");

        // Verify
        assertNotNull(responseEntity);
        assertEquals(200, responseEntity.getStatusCodeValue());
        assertTrue(responseEntity.getBody() instanceof JWTResponse);
        JWTResponse jwtResponse = (JWTResponse) responseEntity.getBody();
        assertNotNull(jwtResponse.getEmail());
    }



    private User createUser() {
        return User.builder()
                .userIdentifier(new UserIdentifier())
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .roles(Collections.emptySet())
                .build();
    }

    private UserDetailsImpl createUserDetails(User user) {
        return new UserDetailsImpl(
                user != null ? user.getId() : null,
                user != null ? user.getUserIdentifier().getUserId() : "",
                user != null ? user.getFirstName() : "",
                user != null ? user.getLastName() : "",
                user != null ? user.getPhoneNumber() : "",
                user != null ? user.getEmail() : "",
                user != null ? user.getPassword() : "",
                Collections.emptyList()
        );
    }
}
