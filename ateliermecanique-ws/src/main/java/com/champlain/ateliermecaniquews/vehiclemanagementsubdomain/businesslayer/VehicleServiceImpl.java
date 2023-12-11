package com.champlain.ateliermecaniquews.vehiclemanagementsubdomain.businesslayer;

import com.champlain.ateliermecaniquews.vehiclemanagementsubdomain.datalayer.Vehicle;
import com.champlain.ateliermecaniquews.vehiclemanagementsubdomain.datalayer.VehicleRepository;
import com.champlain.ateliermecaniquews.vehiclemanagementsubdomain.datamapperlayer.VehicleRequestMapper;
import com.champlain.ateliermecaniquews.vehiclemanagementsubdomain.datamapperlayer.VehicleResponseMapper;
import com.champlain.ateliermecaniquews.vehiclemanagementsubdomain.presentationlayer.VehicleResponseModel;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class VehicleServiceImpl implements VehicleService {

    private VehicleRepository vehicleRepository;

    private VehicleResponseMapper vehicleResponseMapper;

    private VehicleRequestMapper vehicleRequestMapper;

    public VehicleServiceImpl(VehicleRepository vehicleRepository, VehicleResponseMapper vehicleResponseMapper, VehicleRequestMapper vehicleRequestMapper) {
        this.vehicleRepository = vehicleRepository;
        this.vehicleResponseMapper = vehicleResponseMapper;
        this.vehicleRequestMapper = vehicleRequestMapper;
    }

    @Override
    public List<VehicleResponseModel> getAllVehiclesForCustomer(String customerId) {
        List<Vehicle> vehicles = vehicleRepository.findByCustomerAccountIdentifier_CustomerId(customerId);
        if (vehicles == null) {
            return Collections.emptyList();
        }
        return vehicleResponseMapper.entityToResponseModelList(vehicles);
    }

}
