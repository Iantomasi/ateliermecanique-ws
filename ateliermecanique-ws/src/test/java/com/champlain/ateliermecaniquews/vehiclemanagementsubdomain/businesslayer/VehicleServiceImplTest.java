package com.champlain.ateliermecaniquews.vehiclemanagementsubdomain.businesslayer;

import com.champlain.ateliermecaniquews.customeraccountsmanagementsubdomain.datalayer.CustomerAccount;
import com.champlain.ateliermecaniquews.customeraccountsmanagementsubdomain.datalayer.CustomerAccountRepository;
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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class VehicleServiceImplTest {

    @Mock
    private VehicleRepository vehicleRepository;

    @Mock
    private VehicleResponseMapper vehicleResponseMapper;

    @InjectMocks
    private VehicleServiceImpl vehicleService;

    @Mock
    private CustomerAccountRepository customerAccountRepository;

    @Test
    void whenNoVehiclesForCustomer_thenReturnEmptyList() {
        String customerId = "someCustomerId";
        when(vehicleRepository.findAllByCustomerId(customerId)).thenReturn(Collections.emptyList());

        List<VehicleResponseModel> result = vehicleService.getAllVehiclesForCustomer(customerId);

        assertTrue(result.isEmpty());
        verify(vehicleRepository).findAllByCustomerId(customerId);
        verify(vehicleResponseMapper, never()).entityToResponseModelList(any());
    }

    @Test
    void whenRepositoryReturnsVehicles_thenReturnVehicleResponseModelList() {
        // Arrange
        String customerId = "testCustomerId";
        Vehicle vehicle = new Vehicle(); // You might need to set up this vehicle with required properties
        vehicle.setVehicleIdentifier(new VehicleIdentifier()); // Assuming VehicleIdentifier is another class you have
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

        when(vehicleRepository.findAllByCustomerId(customerId)).thenReturn(vehicles);
        when(vehicleResponseMapper.entityToResponseModelList(vehicles)).thenReturn(Arrays.asList(responseModel));

        // Act
        List<VehicleResponseModel> result = vehicleService.getAllVehiclesForCustomer(customerId);

        // Assert
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals("Honda", result.get(0).getMake());
        verify(vehicleRepository).findAllByCustomerId(customerId);
        verify(vehicleResponseMapper).entityToResponseModelList(vehicles);
    }

    @Test
    void whenMapperReturnsNull_thenLogWarning() {
        String customerId = "testCustomerId";
        List<Vehicle> vehicles = Arrays.asList(new Vehicle());
        when(vehicleRepository.findAllByCustomerId(customerId)).thenReturn(vehicles);
        when(vehicleResponseMapper.entityToResponseModelList(vehicles)).thenReturn(null);

        List<VehicleResponseModel> result = vehicleService.getAllVehiclesForCustomer(customerId);

        assertNull(result);
        verify(vehicleRepository).findAllByCustomerId(customerId);
        verify(vehicleResponseMapper).entityToResponseModelList(vehicles);
    }

    @Test
    void whenCustomerNotFound_returnNull() {
        String customerId = "nonExistingCustomerId";
        when(customerAccountRepository.findCustomerAccountByCustomerAccountIdentifier_CustomerId(customerId)).thenReturn(null);

        VehicleRequestModel request = new VehicleRequestModel(customerId,"Toyota", "Corolla", "2021", TransmissionType.AUTOMATIC, "50000");

        assertNull(vehicleService.addVehicleToCustomer(customerId, request));
        verify(customerAccountRepository).findCustomerAccountByCustomerAccountIdentifier_CustomerId(customerId);
        verify(vehicleRepository, never()).save(any(Vehicle.class));
    }

    @Test
    void whenCustomerFound_addVehicleSuccessfully() {
        String customerId = "existingCustomerId";
        VehicleRequestModel request = new VehicleRequestModel(customerId, "Honda", "Civic", "2020", TransmissionType.MANUAL, "30000");

        VehicleIdentifier vehicleIdentifier = new VehicleIdentifier(); // Initialize VehicleIdentifier
        Vehicle newVehicle = new Vehicle();
        newVehicle.setVehicleIdentifier(vehicleIdentifier); // Set the VehicleIdentifier to the newVehicle

        VehicleResponseModel expectedResponse = new VehicleResponseModel(customerId, vehicleIdentifier.getVehicleId(), "Honda", "Civic", "2020", TransmissionType.MANUAL, "30000"); // Populate with expected data

        when(customerAccountRepository.findCustomerAccountByCustomerAccountIdentifier_CustomerId(customerId)).thenReturn(new CustomerAccount()); // Mock customer account found
        when(vehicleRepository.save(any(Vehicle.class))).thenReturn(newVehicle);
        when(vehicleResponseMapper.entityToResponseModel(any(Vehicle.class))).thenReturn(expectedResponse);

        VehicleResponseModel actualResponse = vehicleService.addVehicleToCustomer(customerId, request);

        assertNotNull(actualResponse);
        assertEquals(expectedResponse, actualResponse); // Verify the response is as expected
        verify(customerAccountRepository).findCustomerAccountByCustomerAccountIdentifier_CustomerId(customerId);
        verify(vehicleRepository).save(any(Vehicle.class));
        verify(vehicleResponseMapper).entityToResponseModel(any(Vehicle.class));
    }


    @Test
    void whenMapperReturnsEmptyList_thenLogWarning() {
        String customerId = "testCustomerId";
        List<Vehicle> vehicles = Arrays.asList(new Vehicle());
        when(vehicleRepository.findAllByCustomerId(customerId)).thenReturn(vehicles);
        when(vehicleResponseMapper.entityToResponseModelList(vehicles)).thenReturn(Collections.emptyList());

        List<VehicleResponseModel> result = vehicleService.getAllVehiclesForCustomer(customerId);

        assertTrue(result.isEmpty());
        verify(vehicleRepository).findAllByCustomerId(customerId);
        verify(vehicleResponseMapper).entityToResponseModelList(vehicles);
    }

    @Test
    void whenMapperReturnsData_thenLogInfo() {
        String customerId = "testCustomerId";
        List<Vehicle> vehicles = Arrays.asList(new Vehicle());
        VehicleResponseModel responseModel = new VehicleResponseModel(
                "1",
                "testCustomerId",
                "Honda",
                "Civic",
                "2023",
                TransmissionType.AUTOMATIC,
                "100000"
        );
        when(vehicleRepository.findAllByCustomerId(customerId)).thenReturn(vehicles);
        when(vehicleResponseMapper.entityToResponseModelList(vehicles)).thenReturn(Arrays.asList(responseModel));

        List<VehicleResponseModel> result = vehicleService.getAllVehiclesForCustomer(customerId);

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        verify(vehicleRepository).findAllByCustomerId(customerId);
        verify(vehicleResponseMapper).entityToResponseModelList(vehicles);
    }

    @Test
    void deleteAllVehiclesByCustomerId_shouldDeleteAllVehicles() {
        // Arrange
        String customerId = "testCustomerId";

        List<Vehicle> vehicles = Arrays.asList(
                new Vehicle(),
                new Vehicle(),
                new Vehicle()
        );

        when(vehicleRepository.findAllByCustomerId(customerId)).thenReturn(vehicles);

        // Act
        vehicleService.deleteAllVehiclesByCustomerId(customerId);

        // Assert
        verify(vehicleRepository, times(3)).delete(any(Vehicle.class));
    }



}