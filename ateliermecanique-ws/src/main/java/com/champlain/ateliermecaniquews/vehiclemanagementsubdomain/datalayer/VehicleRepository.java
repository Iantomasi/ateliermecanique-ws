package com.champlain.ateliermecaniquews.vehiclemanagementsubdomain.datalayer;

import com.champlain.ateliermecaniquews.vehiclemanagementsubdomain.businesslayer.VehicleService;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VehicleRepository extends JpaRepository<Vehicle, Integer> {

    Vehicle findVehicleByVehicleIdentifier_VehicleId(String vehicleId);

    List<Vehicle> findByCustomerAccountIdentifier_CustomerId(String customerId);;

}
