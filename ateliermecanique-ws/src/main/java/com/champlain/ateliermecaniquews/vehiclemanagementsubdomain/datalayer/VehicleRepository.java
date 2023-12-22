package com.champlain.ateliermecaniquews.vehiclemanagementsubdomain.datalayer;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VehicleRepository extends JpaRepository<Vehicle, Integer> {

    List<Vehicle> findAllVehiclesByCustomerId(String customerId);

    Vehicle findVehicleByCustomerIdAndVehicleIdentifier_VehicleId(String customerId, String vehicleId);

}
