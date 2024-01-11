package com.champlain.ateliermecaniquews.vehiclemanagementsubdomain.businesslayer;
import com.champlain.ateliermecaniquews.authenticationsubdomain.dataLayer.User;
import com.champlain.ateliermecaniquews.authenticationsubdomain.dataLayer.UserIdentifier;
import com.champlain.ateliermecaniquews.authenticationsubdomain.dataLayer.repositories.UserRepository;
import com.champlain.ateliermecaniquews.vehiclemanagementsubdomain.datalayer.TransmissionType;
import com.champlain.ateliermecaniquews.vehiclemanagementsubdomain.datalayer.Vehicle;
import com.champlain.ateliermecaniquews.vehiclemanagementsubdomain.datalayer.VehicleIdentifier;
import com.champlain.ateliermecaniquews.vehiclemanagementsubdomain.datalayer.VehicleRepository;
import com.champlain.ateliermecaniquews.vehiclemanagementsubdomain.datamapperlayer.VehicleResponseMapper;
import com.champlain.ateliermecaniquews.vehiclemanagementsubdomain.presentationlayer.VehicleRequestModel;
import com.champlain.ateliermecaniquews.vehiclemanagementsubdomain.presentationlayer.VehicleResponseModel;
import org.springframework.boot.test.context.SpringBootTest;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@SpringBootTest
class VehicleServiceImplTest {

    @Mock
    private VehicleRepository vehicleRepository;

    @Mock
    private VehicleResponseMapper vehicleResponseMapper;

    @InjectMocks
    private VehicleServiceImpl vehicleService;

    @Mock
    private UserRepository userRepository;


    @Test
    void getAllVehiclesByCustomerId_shouldReturnVehicleResponseModelList() {
        // Arrange
        String userId = "testCustomerId";
        Vehicle vehicle = new Vehicle();
        vehicle.setVehicleIdentifier(new VehicleIdentifier());
        List<Vehicle> vehicles = Arrays.asList(vehicle);

        VehicleResponseModel responseModel = new VehicleResponseModel(
                "1",
                "testCustomerId",
                "Honda",
                "Civic",
                "2023",
                TransmissionType.AUTOMATIC,
                "100000"
        );

        when(vehicleRepository.findAllVehiclesByUserId(userId)).thenReturn(vehicles);
        when(vehicleResponseMapper.entityToResponseModelList(vehicles)).thenReturn(Arrays.asList(responseModel));

        // Act
        List<VehicleResponseModel> result = vehicleService.getAllVehiclesByCustomerId(userId);

        // Assert
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals("Honda", result.get(0).getMake());
        verify(vehicleRepository).findAllVehiclesByUserId(userId);
        verify(vehicleResponseMapper).entityToResponseModelList(vehicles);
    }


    @Test
    void getAllVehiclesByCustomerId_noVehicles_shouldReturnEmptyList() {
        String userId = "someuserId";
        when(vehicleRepository.findAllVehiclesByUserId(userId)).thenReturn(Collections.emptyList());

        List<VehicleResponseModel> result = vehicleService.getAllVehiclesByCustomerId(userId);

        assertTrue(result.isEmpty());
        verify(vehicleRepository).findAllVehiclesByUserId(userId);
        verify(vehicleResponseMapper, never()).entityToResponseModelList(any());
    }


    @Test
    void getAllVehiclesByCustomerId_shouldReturnNull() {
        String userId = "testuserId";
        List<Vehicle> vehicles = Arrays.asList(new Vehicle());
        when(vehicleRepository.findAllVehiclesByUserId(userId)).thenReturn(vehicles);
        when(vehicleResponseMapper.entityToResponseModelList(vehicles)).thenReturn(null);

        List<VehicleResponseModel> result = vehicleService.getAllVehiclesByCustomerId(userId);

        assertNull(result);
        verify(vehicleRepository).findAllVehiclesByUserId(userId);
        verify(vehicleResponseMapper).entityToResponseModelList(vehicles);
    }

    @Test
    void getVehicleByVehicleId_shouldSucceed() {
        // Arrange
        Vehicle vehicle = new Vehicle();
        String vehicleId = new VehicleIdentifier().toString();
        String userId = new UserIdentifier().toString();
        VehicleResponseModel responseModel = new VehicleResponseModel(
                vehicleId,
                userId,
                "Honda",
                "Civic",
                "2023",
                TransmissionType.AUTOMATIC,
                "100000"
        );

        when(vehicleRepository.findVehicleByUserIdAndVehicleIdentifier_VehicleId(userId, vehicleId)).thenReturn(vehicle);
        when(vehicleResponseMapper.entityToResponseModel(vehicle)).thenReturn(responseModel);

        VehicleResponseModel result = vehicleService.getVehicleByVehicleId(userId, vehicleId);

        // Assert
        assertNotNull(result);
        assertEquals("Honda", result.getMake());
        verify(vehicleRepository).findVehicleByUserIdAndVehicleIdentifier_VehicleId(userId, vehicleId);
        verify(vehicleResponseMapper).entityToResponseModel(vehicle);
    }

    @Test
    void getVehicleByInvalidVehicleId_shouldReturnNull() {
        // Arrange
        String vehicleId = new VehicleIdentifier().toString();
        String userId = new UserIdentifier().toString();

        // Mock the repository and mapper methods with correct parameters
        when(vehicleRepository.findVehicleByUserIdAndVehicleIdentifier_VehicleId(userId, vehicleId)).thenReturn(null);

        // Act
        VehicleResponseModel result = vehicleService.getVehicleByVehicleId(userId, vehicleId);

        // Assert
        assertNull(result);
        verify(vehicleRepository).findVehicleByUserIdAndVehicleIdentifier_VehicleId(userId, vehicleId);
        verify(vehicleResponseMapper, never()).entityToResponseModel(any());
    }

    @Test
    void addVehicleToCustomerAccount_shouldSucceed() {
        String customerId = "existingCustomerId";
        VehicleRequestModel request = new VehicleRequestModel(customerId, "Honda", "Civic", "2020", TransmissionType.MANUAL, "30000");

        VehicleIdentifier vehicleIdentifier = new VehicleIdentifier();
        Vehicle newVehicle = new Vehicle();
        newVehicle.setVehicleIdentifier(vehicleIdentifier);

        VehicleResponseModel expectedResponse = new VehicleResponseModel(customerId, vehicleIdentifier.getVehicleId(), "Honda", "Civic", "2020", TransmissionType.MANUAL, "30000"); // Populate with expected data

        when(userRepository.findUserByUserIdentifier_UserId(customerId)).thenReturn(new User());
        when(vehicleRepository.save(any(Vehicle.class))).thenReturn(newVehicle);
        when(vehicleResponseMapper.entityToResponseModel(any(Vehicle.class))).thenReturn(expectedResponse);

        VehicleResponseModel actualResponse = vehicleService.addVehicleToCustomerAccount(customerId, request);

        assertNotNull(actualResponse);
        assertEquals(expectedResponse, actualResponse); // Verify the response is as expected
        verify(userRepository).findUserByUserIdentifier_UserId(customerId);
        verify(vehicleRepository).save(any(Vehicle.class));
        verify(vehicleResponseMapper).entityToResponseModel(any(Vehicle.class));
    }

    @Test
    void addVehicleToInvalidCustomerAccount_shouldReturnNull() {
        String customerId = "nonExistingCustomerId";
        when(userRepository.findUserByUserIdentifier_UserId(customerId)).thenReturn(null);

        VehicleRequestModel request = new VehicleRequestModel(customerId,"Toyota", "Corolla", "2021", TransmissionType.AUTOMATIC, "50000");

        assertNull(vehicleService.addVehicleToCustomerAccount(customerId, request));
        verify(userRepository).findUserByUserIdentifier_UserId(customerId);
        verify(vehicleRepository, never()).save(any(Vehicle.class));
    }

    @Test
    void updateVehicleByVehicleId_shouldSucceed() {
        // Arrange
        Vehicle vehicle = new Vehicle();
        String vehicleId = new VehicleIdentifier().toString();
        String userId = new UserIdentifier().toString();
        String transmissionTypeAsString = TransmissionType.AUTOMATIC.toString();
        VehicleRequestModel requestModel = new VehicleRequestModel(
                userId,
                "Honda",
                "Civic",
                "2023",
                TransmissionType.MANUAL,
                "100000"
        );

        when(vehicleRepository.findVehicleByUserIdAndVehicleIdentifier_VehicleId(userId, vehicleId)).thenReturn(vehicle);

        // Act
        vehicleService.updateVehicleByVehicleId(requestModel, userId, vehicleId);

        // Assert
        verify(vehicleRepository).findVehicleByUserIdAndVehicleIdentifier_VehicleId(userId, vehicleId);
        verify(vehicleRepository).save(vehicle);
    }

    @Test
    void updateVehicleByInvalidVehicleId_shouldReturnNull() {
        // Arrange
        String vehicleId = new VehicleIdentifier().toString();
        String userId = new UserIdentifier().toString();
        String transmissionTypeAsString = TransmissionType.AUTOMATIC.toString();
        VehicleRequestModel requestModel = new VehicleRequestModel(
                userId,
                "Honda",
                "Civic",
                "2023",
                TransmissionType.AUTOMATIC,
                "100000"
        );

        when(vehicleRepository.findVehicleByUserIdAndVehicleIdentifier_VehicleId(userId, vehicleId)).thenReturn(null);

        // Act
        VehicleResponseModel result = vehicleService.updateVehicleByVehicleId(requestModel, userId, vehicleId);

        // Assert
        assertNull(result);
        verify(vehicleRepository).findVehicleByUserIdAndVehicleIdentifier_VehicleId(userId, vehicleId);
        verify(vehicleRepository, never()).save(any());
    }


    @Test
    void deleteVehicleByVehicleId_shouldSucceed() {
        // Arrange
        String userId = "existingCustomerId";
        String vehicleId = "existingVehicleId";
        Vehicle vehicle = new Vehicle(); // Create a dummy Vehicle instance

        when(vehicleRepository.findVehicleByUserIdAndVehicleIdentifier_VehicleId(userId, vehicleId)).thenReturn(vehicle);

        // Act
        vehicleService.deleteVehicleByVehicleId(userId, vehicleId);

        // Assert
        verify(vehicleRepository).findVehicleByUserIdAndVehicleIdentifier_VehicleId(userId, vehicleId);
        verify(vehicleRepository).delete(vehicle);
    }


    @Test
    void deleteVehicleByInvalidVehicleId_shouldReturnNull() {
        // Arrange
        String userId = "nonExistingCustomerId";
        String vehicleId = "nonExistingVehicleId";

        when(vehicleRepository.findVehicleByUserIdAndVehicleIdentifier_VehicleId(userId, vehicleId)).thenReturn(null);
    }

    @Test
    void deleteAllVehiclesByCustomerId_shouldSucceed() {
        // Arrange
        String customerId = "testCustomerId";

        List<Vehicle> vehicles = Arrays.asList(
                new Vehicle(),
                new Vehicle(),
                new Vehicle()
        );

        when(vehicleRepository.findAllVehiclesByUserId(customerId)).thenReturn(vehicles);

        // Act
        vehicleService.deleteAllVehiclesByCustomerId(customerId);

        // Assert
        verify(vehicleRepository, times(3)).delete(any(Vehicle.class));
    }




}