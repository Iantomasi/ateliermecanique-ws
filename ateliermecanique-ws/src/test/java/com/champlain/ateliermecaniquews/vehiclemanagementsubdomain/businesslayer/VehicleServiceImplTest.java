package com.champlain.ateliermecaniquews.vehiclemanagementsubdomain.businesslayer;

import com.champlain.ateliermecaniquews.vehiclemanagementsubdomain.datalayer.TransmissionType;
import com.champlain.ateliermecaniquews.vehiclemanagementsubdomain.datalayer.Vehicle;
import com.champlain.ateliermecaniquews.vehiclemanagementsubdomain.datalayer.VehicleIdentifier;
import com.champlain.ateliermecaniquews.vehiclemanagementsubdomain.datalayer.VehicleRepository;
import com.champlain.ateliermecaniquews.vehiclemanagementsubdomain.datamapperlayer.VehicleResponseMapper;
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

    @Test
    void whenNoVehiclesForCustomer_thenReturnEmptyList() {
        String customerId = "someCustomerId";
        when(vehicleRepository.findByCustomerId(customerId)).thenReturn(Collections.emptyList());

        List<VehicleResponseModel> result = vehicleService.getAllVehiclesForCustomer(customerId);

        assertTrue(result.isEmpty());
        verify(vehicleRepository).findByCustomerId(customerId);
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

        when(vehicleRepository.findByCustomerId(customerId)).thenReturn(vehicles);
        when(vehicleResponseMapper.entityToResponseModelList(vehicles)).thenReturn(Arrays.asList(responseModel));

        // Act
        List<VehicleResponseModel> result = vehicleService.getAllVehiclesForCustomer(customerId);

        // Assert
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals("Honda", result.get(0).getMake());
        verify(vehicleRepository).findByCustomerId(customerId);
        verify(vehicleResponseMapper).entityToResponseModelList(vehicles);
    }

    @Test
    void whenMapperReturnsNull_thenLogWarning() {
        String customerId = "testCustomerId";
        List<Vehicle> vehicles = Arrays.asList(new Vehicle());
        when(vehicleRepository.findByCustomerId(customerId)).thenReturn(vehicles);
        when(vehicleResponseMapper.entityToResponseModelList(vehicles)).thenReturn(null);

        List<VehicleResponseModel> result = vehicleService.getAllVehiclesForCustomer(customerId);

        assertNull(result);
        verify(vehicleRepository).findByCustomerId(customerId);
        verify(vehicleResponseMapper).entityToResponseModelList(vehicles);
    }

    @Test
    void whenMapperReturnsEmptyList_thenLogWarning() {
        String customerId = "testCustomerId";
        List<Vehicle> vehicles = Arrays.asList(new Vehicle());
        when(vehicleRepository.findByCustomerId(customerId)).thenReturn(vehicles);
        when(vehicleResponseMapper.entityToResponseModelList(vehicles)).thenReturn(Collections.emptyList());

        List<VehicleResponseModel> result = vehicleService.getAllVehiclesForCustomer(customerId);

        assertTrue(result.isEmpty());
        verify(vehicleRepository).findByCustomerId(customerId);
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
        when(vehicleRepository.findByCustomerId(customerId)).thenReturn(vehicles);
        when(vehicleResponseMapper.entityToResponseModelList(vehicles)).thenReturn(Arrays.asList(responseModel));

        List<VehicleResponseModel> result = vehicleService.getAllVehiclesForCustomer(customerId);

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        verify(vehicleRepository).findByCustomerId(customerId);
        verify(vehicleResponseMapper).entityToResponseModelList(vehicles);
    }




}