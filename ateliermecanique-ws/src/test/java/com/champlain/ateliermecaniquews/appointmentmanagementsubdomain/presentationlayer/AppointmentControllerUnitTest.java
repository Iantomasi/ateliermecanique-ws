package com.champlain.ateliermecaniquews.appointmentmanagementsubdomain.presentationlayer;

import com.champlain.ateliermecaniquews.appointmentmanagementsubdomain.businesslayer.AppointmentService;
import com.champlain.ateliermecaniquews.appointmentmanagementsubdomain.datalayer.Status;
import com.champlain.ateliermecaniquews.authenticationsubdomain.dataLayer.User;
import com.champlain.ateliermecaniquews.authenticationsubdomain.dataLayer.UserIdentifier;
import com.champlain.ateliermecaniquews.authenticationsubdomain.dataLayer.repositories.UserRepository;
import com.champlain.ateliermecaniquews.authenticationsubdomain.utils.security.services.UserDetailsImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.mail.MessagingException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AppointmentControllerUnitTest {

    @Mock
    private AppointmentService appointmentService;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AppointmentController appointmentController;

    // Sample test data


    @Test
    void testGetAllAppointmentsAdmin() {
        AppointmentResponseModel appointment = AppointmentResponseModel.builder()
                .appointmentId("123")
                .customerId("456")
                .vehicleId("789")
                .appointmentDate(LocalDateTime.now())
                .services("Service A, Service B")
                .comments("Test comments")
                .status(Status.PENDING)
                .build();
        List<AppointmentResponseModel> appointments = new ArrayList<>();

        appointments.add(appointment);

        when(appointmentService.getAllAppointments()).thenReturn(appointments);

        ResponseEntity<List<AppointmentResponseModel>> response = appointmentController.getAllAppointmentsAdmin();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(appointments, response.getBody());
    }

    @Test
    void testGetAllAppointmentsAdmin_NotFound() {

        List<AppointmentResponseModel> appointments = new ArrayList<>();

        when(appointmentService.getAllAppointments()).thenReturn(appointments);

        ResponseEntity<List<AppointmentResponseModel>> response = appointmentController.getAllAppointmentsAdmin();

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testGetAppointmentByIdAdmin() {
        // Create a sample AppointmentResponseModel instance
        AppointmentResponseModel appointment = AppointmentResponseModel.builder()
                .appointmentId("123")
                .customerId("456")
                .vehicleId("789")
                .appointmentDate(LocalDateTime.now())
                .services("Service A, Service B")
                .comments("Test comments")
                .status(Status.PENDING)
                .build();

        // Mock the behavior of the appointmentService
        when(appointmentService.getAppointmentByAppointmentId(anyString())).thenReturn(appointment);

        // Call the controller method
        ResponseEntity<AppointmentResponseModel> response = appointmentController.getAppointmentByIdAdmin("123");

        // Assert the results
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(appointment, response.getBody());
    }

    @Test
    void testGetAppointmentByIdAdmin_NotFound() {


        // Mock the behavior of the appointmentService
        when(appointmentService.getAppointmentByAppointmentId(anyString())).thenReturn(null);

        // Call the controller method
        ResponseEntity<AppointmentResponseModel> response = appointmentController.getAppointmentByIdAdmin("123");

        // Assert the results
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testAddAppointmentToCustomerAccountAdmin() throws MessagingException {
        AppointmentRequestModel appointmentRequestModel =new AppointmentRequestModel("123","123",LocalDateTime.now(),"services","comments",Status.CONFIRMED);


        AppointmentResponseModel appointment = AppointmentResponseModel.builder()
                .appointmentId("123")
                .customerId("123")
                .vehicleId("123")
                .appointmentDate(LocalDateTime.now())
                .services("ervices")
                .comments("comments")
                .status(Status.CONFIRMED)
                .build();

        // Mock the behavior of the appointmentService
        when(appointmentService.addAppointmentToCustomerAccount(anyString(), eq(appointmentRequestModel))).thenReturn(appointment);

        // Call the controller method
        ResponseEntity<AppointmentResponseModel> response = appointmentController.addAppointmentToCustomerAccountAdmin("456", appointmentRequestModel);

        // Assert the results
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(appointment, response.getBody());
    }

    @Test
    void testAddAppointmentToCustomerAccountAdmin_NotFound() throws MessagingException {
        AppointmentRequestModel appointmentRequestModel =new AppointmentRequestModel("123","123",LocalDateTime.now(),"services","comments",Status.CONFIRMED);

        // Mock the behavior of the appointmentService
        when(appointmentService.addAppointmentToCustomerAccount(anyString(), eq(appointmentRequestModel))).thenReturn(null);

        // Call the controller method
        ResponseEntity<AppointmentResponseModel> response = appointmentController.addAppointmentToCustomerAccountAdmin("456", appointmentRequestModel);

        // Assert the results
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testCheckTimeSlotAvailability() {
        LocalDate date = LocalDate.now();

        // Mock the behavior of the appointmentService
        Map<String, Boolean> availability = new HashMap<>();
        availability.put("timeSlot1", true);
        when(appointmentService.checkTimeSlotAvailability(eq(date))).thenReturn(availability);

        // Call the controller method
        ResponseEntity<Map<String, Boolean>> response = appointmentController.checkTimeSlotAvailability(date);

        // Assert the results
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(availability, response.getBody());
    }


    @Test
    void testCheckTimeSlotAvailability_No_Content() {
        LocalDate date = LocalDate.now();

        // Mock the behavior of the appointmentService
        Map<String, Boolean> availability = new HashMap<>();
        availability.put("timeSlot1", true);
        when(appointmentService.checkTimeSlotAvailability(eq(date))).thenReturn(null);

        // Call the controller method
        ResponseEntity<Map<String, Boolean>> response = appointmentController.checkTimeSlotAvailability(date);

        // Assert the results
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void testDeleteAppointmentByAppointmentIdCustomer_AdminRole_UserNotFound() {
        // Arrange
        Authentication authentication = mock(Authentication.class);
        doReturn(Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN")))
                .when(authentication).getAuthorities();
        SecurityContextHolder.getContext().setAuthentication(authentication);

        when(userRepository.findUserByUserIdentifier_UserId(anyString())).thenReturn(null);

        // Act
        ResponseEntity<Void> response = appointmentController.deleteAppointmentByAppointmentIdCustomer("appointmentId", "customerId");

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testDeleteAppointmentByAppointmentIdCustomer_AdminRole_UserFound() {
        // Arrange
        Authentication authentication = mock(Authentication.class);
        doReturn(Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN")))
                .when(authentication).getAuthorities();
        SecurityContextHolder.getContext().setAuthentication(authentication);

        User user = new User();
        when(userRepository.findUserByUserIdentifier_UserId(anyString())).thenReturn(user);

        // Act
        ResponseEntity<Void> response = appointmentController.deleteAppointmentByAppointmentIdCustomer("appointmentId", "customerId");

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(appointmentService, times(1)).deleteAppointmentByAppointmentId(anyString());
    }

    @Test
    void testDeleteAppointmentByAppointmentIdCustomer_CustomerRole_AuthenticatedUserIdNotMatching() {
        // Arrange
        Authentication authentication = mock(Authentication.class);
        doReturn(Collections.singletonList(new SimpleGrantedAuthority("ROLE_CUSTOMER")))
                .when(authentication).getAuthorities();

        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_CUSTOMER"));

        UserDetailsImpl userDetails = new UserDetailsImpl(1,new UserIdentifier().getUserId(),"John","Vegas","444","john@email.com","pass",authorities);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Act
        ResponseEntity<Void> response = appointmentController.deleteAppointmentByAppointmentIdCustomer("appointmentId", "customerId");

        // Assert
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    void testDeleteAppointmentByAppointmentIdCustomer_CustomerRole_AuthenticatedUserIdMatching() {
        // Arrange
        Authentication authentication = mock(Authentication.class);
        doReturn(Collections.singletonList(new SimpleGrantedAuthority("ROLE_CUSTOMER")))
                .when(authentication).getAuthorities();

        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_CUSTOMER"));

        UserDetailsImpl userDetails = new UserDetailsImpl(1,new UserIdentifier().getUserId(),"John","Vegas","444","john@email.com","pass",authorities);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Act
        ResponseEntity<Void> response = appointmentController.deleteAppointmentByAppointmentIdCustomer("appointmentId", userDetails.getUserId());

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(appointmentService, times(1)).deleteAppointmentByAppointmentId(anyString());
    }

    @Test
    void testAddAppointmentToCustomerAccountCustomer_AdminRole_CustomerNotFound() throws MessagingException {
        // Arrange
        Authentication authentication = mock(Authentication.class);
        doReturn(Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN")))
                .when(authentication).getAuthorities();
        SecurityContextHolder.getContext().setAuthentication(authentication);

        AppointmentRequestModel appointmentRequestModel =new AppointmentRequestModel("123","123",LocalDateTime.now(),"services","comments",Status.CONFIRMED);


        when(userRepository.findUserByUserIdentifier_UserId(anyString())).thenReturn(null);

        // Act
        ResponseEntity<AppointmentResponseModel> response = appointmentController.addAppointmentToCustomerAccountCustomer("customerId", appointmentRequestModel);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testAddAppointmentToCustomerAccountCustomer_AdminRole_CustomerFound() throws MessagingException {
        // Arrange

        AppointmentResponseModel appointment = AppointmentResponseModel.builder()
                .appointmentId("123")
                .customerId("123")
                .vehicleId("123")
                .appointmentDate(LocalDateTime.now())
                .services("services")
                .comments("comments")
                .status(Status.CONFIRMED)
                .build();


        Authentication authentication = mock(Authentication.class);
        doReturn(Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN")))
                .when(authentication).getAuthorities();
        SecurityContextHolder.getContext().setAuthentication(authentication);

        AppointmentRequestModel appointmentRequestModel =new AppointmentRequestModel("123","123",LocalDateTime.now(),"services","comments",Status.CONFIRMED);


        User user = new User();
        when(userRepository.findUserByUserIdentifier_UserId(anyString())).thenReturn(user);
        when(appointmentService.addAppointmentToCustomerAccount(any(),any())).thenReturn(appointment);

        // Act
        ResponseEntity<AppointmentResponseModel> response = appointmentController.addAppointmentToCustomerAccountCustomer("customerId", appointmentRequestModel);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testAddAppointmentToCustomerAccountCustomer_CustomerRole_AuthenticatedUserIdNotMatching() throws MessagingException {
        // Arrange
        Authentication authentication = mock(Authentication.class);
        doReturn(Collections.singletonList(new SimpleGrantedAuthority("ROLE_CUSTOMER")))
                .when(authentication).getAuthorities();

        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_CUSTOMER"));

        AppointmentRequestModel appointmentRequestModel =new AppointmentRequestModel("123","123",LocalDateTime.now(),"services","comments",Status.CONFIRMED);


        UserDetailsImpl userDetails = new UserDetailsImpl(1, new UserIdentifier().getUserId(), "John", "Vegas", "444", "john@email.com", "pass", authorities);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Act
        ResponseEntity<AppointmentResponseModel> response = appointmentController.addAppointmentToCustomerAccountCustomer("customerId", appointmentRequestModel);

        // Assert
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    void testAddAppointmentToCustomerAccountCustomer_CustomerRole_AuthenticatedUserIdMatching() throws MessagingException {
        // Arrange

        AppointmentResponseModel appointment = AppointmentResponseModel.builder()
                .appointmentId("123")
                .customerId("123")
                .vehicleId("123")
                .appointmentDate(LocalDateTime.now())
                .services("services")
                .comments("comments")
                .status(Status.CONFIRMED)
                .build();

        Authentication authentication = mock(Authentication.class);
        doReturn(Collections.singletonList(new SimpleGrantedAuthority("ROLE_CUSTOMER")))
                .when(authentication).getAuthorities();

        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_CUSTOMER"));

        AppointmentRequestModel appointmentRequestModel =new AppointmentRequestModel("123","123",LocalDateTime.now(),"services","comments",Status.CONFIRMED);


        UserDetailsImpl userDetails = new UserDetailsImpl(1, new UserIdentifier().getUserId(), "John", "Vegas", "444", "john@email.com", "pass", authorities);
        when(authentication.getPrincipal()).thenReturn(userDetails);

        when(appointmentService.addAppointmentToCustomerAccount(any(),any())).thenReturn(appointment);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Act
        ResponseEntity<AppointmentResponseModel> response = appointmentController.addAppointmentToCustomerAccountCustomer(userDetails.getUserId(),appointmentRequestModel);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testAddAppointmentToCustomerAccountCustomer_CustomerRole_NotFound() throws MessagingException {
        // Arrange
        Authentication authentication = mock(Authentication.class);
        doReturn(Collections.singletonList(new SimpleGrantedAuthority("ROLE_CUSTOMER")))
                .when(authentication).getAuthorities();

        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_CUSTOMER"));

        AppointmentRequestModel appointmentRequestModel =new AppointmentRequestModel("123","123",LocalDateTime.now(),"services","comments",Status.CONFIRMED);


        UserDetailsImpl userDetails = new UserDetailsImpl(1, new UserIdentifier().getUserId(), "John", "Vegas", "444", "john@email.com", "pass", authorities);
        when(authentication.getPrincipal()).thenReturn(userDetails);

        when(appointmentService.addAppointmentToCustomerAccount(any(),any())).thenReturn(null);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Act
        ResponseEntity<AppointmentResponseModel> response = appointmentController.addAppointmentToCustomerAccountCustomer(userDetails.getUserId(),appointmentRequestModel);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }


    @Test
    void testUpdateAppointmentByAppointmentId_AdminRole_CustomerNotFound() {
        // Arrange
        AppointmentRequestModel appointmentRequestModel =new AppointmentRequestModel("123","123",LocalDateTime.now(),"services","comments",Status.CONFIRMED);


        Authentication authentication = mock(Authentication.class);
        doReturn(Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN")))
                .when(authentication).getAuthorities();
        SecurityContextHolder.getContext().setAuthentication(authentication);

        when(userRepository.findUserByUserIdentifier_UserId(anyString())).thenReturn(null);

        // Act
        ResponseEntity<AppointmentResponseModel> response = appointmentController.updateAppointmentByAppointmentId(appointmentRequestModel, "appointmentId", "customerId");

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testUpdateAppointmentByAppointmentId_AdminRole_CustomerFound() {
        // Arrange

        AppointmentResponseModel appointment = AppointmentResponseModel.builder()
                .appointmentId("123")
                .customerId("123")
                .vehicleId("123")
                .appointmentDate(LocalDateTime.now())
                .services("services")
                .comments("comments")
                .status(Status.CONFIRMED)
                .build();

        Authentication authentication = mock(Authentication.class);
        doReturn(Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN")))
                .when(authentication).getAuthorities();
        SecurityContextHolder.getContext().setAuthentication(authentication);

        AppointmentRequestModel appointmentRequestModel =new AppointmentRequestModel("123","123",LocalDateTime.now(),"services","comments",Status.CONFIRMED);


        User user = new User();
        when(userRepository.findUserByUserIdentifier_UserId(anyString())).thenReturn(user);
        when(appointmentService.updateAppointmentByAppointmentId(any(),any())).thenReturn(appointment);
        // Act
        ResponseEntity<AppointmentResponseModel> response = appointmentController.updateAppointmentByAppointmentId(appointmentRequestModel, "appointmentId", "customerId");

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testUpdateAppointmentByAppointmentId_CustomerRole_AuthenticatedUserIdNotMatching() {
        // Arrange
        Authentication authentication = mock(Authentication.class);
        doReturn(Collections.singletonList(new SimpleGrantedAuthority("ROLE_CUSTOMER")))
                .when(authentication).getAuthorities();

        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_CUSTOMER"));

        UserDetailsImpl userDetails = new UserDetailsImpl(1, new UserIdentifier().getUserId(), "John", "Vegas", "444", "john@email.com", "pass", authorities);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        AppointmentRequestModel appointmentRequestModel =new AppointmentRequestModel("123","123",LocalDateTime.now(),"services","comments",Status.CONFIRMED);


        // Act
        ResponseEntity<AppointmentResponseModel> response = appointmentController.updateAppointmentByAppointmentId(appointmentRequestModel, "appointmentId", "customerId");

        // Assert
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    void testUpdateAppointmentByAppointmentId_CustomerRole_AuthenticatedUserIdMatching() {
        // Arrange
        AppointmentResponseModel appointment = AppointmentResponseModel.builder()
                .appointmentId("123")
                .customerId("123")
                .vehicleId("123")
                .appointmentDate(LocalDateTime.now())
                .services("services")
                .comments("comments")
                .status(Status.CONFIRMED)
                .build();

        Authentication authentication = mock(Authentication.class);
        doReturn(Collections.singletonList(new SimpleGrantedAuthority("ROLE_CUSTOMER")))
                .when(authentication).getAuthorities();

        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_CUSTOMER"));

        UserDetailsImpl userDetails = new UserDetailsImpl(1, new UserIdentifier().getUserId(), "John", "Vegas", "444", "john@email.com", "pass", authorities);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        AppointmentRequestModel appointmentRequestModel =new AppointmentRequestModel("123","123",LocalDateTime.now(),"services","comments",Status.CONFIRMED);


        when(appointmentService.updateAppointmentByAppointmentId(any(),any())).thenReturn(appointment);

        // Act
        ResponseEntity<AppointmentResponseModel> response = appointmentController.updateAppointmentByAppointmentId(appointmentRequestModel, "appointmentId", userDetails.getUserId());

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testUpdateAppointmentByAppointmentId_CustomerRole_NotFound() {
        // Arrange
        Authentication authentication = mock(Authentication.class);
        doReturn(Collections.singletonList(new SimpleGrantedAuthority("ROLE_CUSTOMER")))
                .when(authentication).getAuthorities();
        AppointmentRequestModel appointmentRequestModel =new AppointmentRequestModel("123","123",LocalDateTime.now(),"services","comments",Status.CONFIRMED);

        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_CUSTOMER"));

        UserDetailsImpl userDetails = new UserDetailsImpl(1, new UserIdentifier().getUserId(), "John", "Vegas", "444", "john@email.com", "pass", authorities);
        when(authentication.getPrincipal()).thenReturn(userDetails);

        when(appointmentService.updateAppointmentByAppointmentId(any(), any())).thenReturn(null);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Act
        ResponseEntity<AppointmentResponseModel> response = appointmentController.updateAppointmentByAppointmentId(appointmentRequestModel, "appointmentId", userDetails.getUserId());

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testGetAppointmentByIdCustomer_CustomerRole_AuthenticatedUserIdNotMatching() {
        // Arrange
        Authentication authentication = mock(Authentication.class);
        doReturn(Collections.singletonList(new SimpleGrantedAuthority("ROLE_CUSTOMER")))
                .when(authentication).getAuthorities();

        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_CUSTOMER"));

        UserDetailsImpl userDetails = new UserDetailsImpl(1, new UserIdentifier().getUserId(), "John", "Vegas", "444", "john@email.com", "pass", authorities);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Act
        ResponseEntity<AppointmentResponseModel> response = appointmentController.getAppointmentByIdCustomer("appointmentId", "customerId");

        // Assert
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    void testGetAppointmentByIdCustomer_CustomerRole_AuthenticatedUserIdMatching() {
        // Arrange
        AppointmentResponseModel appointment = AppointmentResponseModel.builder()
                .appointmentId("123")
                .customerId("123")
                .vehicleId("123")
                .appointmentDate(LocalDateTime.now())
                .services("services")
                .comments("comments")
                .status(Status.CONFIRMED)
                .build();

        Authentication authentication = mock(Authentication.class);
        doReturn(Collections.singletonList(new SimpleGrantedAuthority("ROLE_CUSTOMER")))
                .when(authentication).getAuthorities();

        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_CUSTOMER"));

        UserDetailsImpl userDetails = new UserDetailsImpl(1, new UserIdentifier().getUserId(), "John", "Vegas", "444", "john@email.com", "pass", authorities);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        when(appointmentService.getAppointmentByAppointmentId(any())).thenReturn(appointment);
        // Act
        ResponseEntity<AppointmentResponseModel> response = appointmentController.getAppointmentByIdCustomer("appointmentId", userDetails.getUserId());

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        // You can add more assertions based on the specific behavior of the method
    }

    @Test
    void testGetAppointmentByIdCustomer_CustomerRole_NotFound() {
        // Arrange
        Authentication authentication = mock(Authentication.class);
        doReturn(Collections.singletonList(new SimpleGrantedAuthority("ROLE_CUSTOMER")))
                .when(authentication).getAuthorities();

        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_CUSTOMER"));

        UserDetailsImpl userDetails = new UserDetailsImpl(1, new UserIdentifier().getUserId(), "John", "Vegas", "444", "john@email.com", "pass", authorities);
        when(authentication.getPrincipal()).thenReturn(userDetails);

        when(appointmentService.getAppointmentByAppointmentId(anyString())).thenReturn(null);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Act
        ResponseEntity<AppointmentResponseModel> response = appointmentController.getAppointmentByIdCustomer("appointmentId", userDetails.getUserId());

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testUpdateAppointmentStatusCustomer_CustomerRole_AuthenticatedUserIdNotMatching() {
        // Arrange
        Authentication authentication = mock(Authentication.class);
        doReturn(Collections.singletonList(new SimpleGrantedAuthority("ROLE_CUSTOMER")))
                .when(authentication).getAuthorities();

        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_CUSTOMER"));

        UserDetailsImpl userDetails = new UserDetailsImpl(1, new UserIdentifier().getUserId(), "John", "Vegas", "444", "john@email.com", "pass", authorities);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Act
        ResponseEntity<AppointmentResponseModel> response = appointmentController.updateAppointmentStatusCustomer("customerId", "appointmentId", true);

        // Assert
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    void testUpdateAppointmentStatusCustomer_CustomerRole_AuthenticatedUserIdMatching() {
        // Arrange
        AppointmentResponseModel appointment = AppointmentResponseModel.builder()
                .appointmentId("123")
                .customerId("123")
                .vehicleId("123")
                .appointmentDate(LocalDateTime.now())
                .services("services")
                .comments("comments")
                .status(Status.CONFIRMED)
                .build();

        Authentication authentication = mock(Authentication.class);
        doReturn(Collections.singletonList(new SimpleGrantedAuthority("ROLE_CUSTOMER")))
                .when(authentication).getAuthorities();

        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_CUSTOMER"));

        UserDetailsImpl userDetails = new UserDetailsImpl(1, new UserIdentifier().getUserId(), "John", "Vegas", "444", "john@email.com", "pass", authorities);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        when(appointmentService.updateAppointmentStatus(any(),anyBoolean())).thenReturn(appointment);
        // Act
        ResponseEntity<AppointmentResponseModel> response = appointmentController.updateAppointmentStatusCustomer(userDetails.getUserId(), "appointmentId", true);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testUpdateAppointmentStatusCustomer_CustomerRole_NotFound() {
        // Arrange
        Authentication authentication = mock(Authentication.class);
        doReturn(Collections.singletonList(new SimpleGrantedAuthority("ROLE_CUSTOMER")))
                .when(authentication).getAuthorities();

        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_CUSTOMER"));

        UserDetailsImpl userDetails = new UserDetailsImpl(1, new UserIdentifier().getUserId(), "John", "Vegas", "444", "john@email.com", "pass", authorities);
        when(authentication.getPrincipal()).thenReturn(userDetails);

        when(appointmentService.updateAppointmentStatus(anyString(), anyBoolean())).thenReturn(null);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Act
        ResponseEntity<AppointmentResponseModel> response = appointmentController.updateAppointmentStatusCustomer(userDetails.getUserId(), "appointmentId", true);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testGetAllAppointmentsByCustomerId_CustomerRole_AuthenticatedUserIdNotMatching() {
        // Arrange
        Authentication authentication = mock(Authentication.class);
        doReturn(Collections.singletonList(new SimpleGrantedAuthority("ROLE_CUSTOMER")))
                .when(authentication).getAuthorities();

        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_CUSTOMER"));

        UserDetailsImpl userDetails = new UserDetailsImpl(1, new UserIdentifier().getUserId(), "John", "Vegas", "444", "john@email.com", "pass", authorities);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Act
        ResponseEntity<List<AppointmentResponseModel>> response = appointmentController.getAllAppointmentsByCustomerId("customerId");

        // Assert
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    void testGetAllAppointmentsByCustomerId_CustomerRole_AuthenticatedUserIdMatching() {
        // Arrange
        // Arrange
        AppointmentResponseModel appointment = AppointmentResponseModel.builder()
                .appointmentId("123")
                .customerId("123")
                .vehicleId("123")
                .appointmentDate(LocalDateTime.now())
                .services("services")
                .comments("comments")
                .status(Status.CONFIRMED)
                .build();

        List<AppointmentResponseModel> appointments = new ArrayList<>();
        appointments.add(appointment);

        Authentication authentication = mock(Authentication.class);
        doReturn(Collections.singletonList(new SimpleGrantedAuthority("ROLE_CUSTOMER")))
                .when(authentication).getAuthorities();

        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_CUSTOMER"));

        UserDetailsImpl userDetails = new UserDetailsImpl(1, new UserIdentifier().getUserId(), "John", "Vegas", "444", "john@email.com", "pass", authorities);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        when(appointmentService.getAllAppointmentsByCustomerId(any())).thenReturn(appointments);
        // Act
        ResponseEntity<List<AppointmentResponseModel>> response = appointmentController.getAllAppointmentsByCustomerId(userDetails.getUserId());

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        // You can add more assertions based on the specific behavior of the method
    }

    @Test
    void testGetAllAppointmentsByCustomerId_CustomerRole_NotFound() {
        // Arrange
        Authentication authentication = mock(Authentication.class);
        doReturn(Collections.singletonList(new SimpleGrantedAuthority("ROLE_CUSTOMER")))
                .when(authentication).getAuthorities();

        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_CUSTOMER"));

        UserDetailsImpl userDetails = new UserDetailsImpl(1, new UserIdentifier().getUserId(), "John", "Vegas", "444", "john@email.com", "pass", authorities);
        when(authentication.getPrincipal()).thenReturn(userDetails);

        when(appointmentService.getAllAppointmentsByCustomerId(anyString())).thenReturn(null);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Act
        ResponseEntity<List<AppointmentResponseModel>> response = appointmentController.getAllAppointmentsByCustomerId(userDetails.getUserId());

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }


}