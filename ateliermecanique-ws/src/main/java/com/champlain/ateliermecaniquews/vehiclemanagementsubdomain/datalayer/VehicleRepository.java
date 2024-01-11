package com.champlain.ateliermecaniquews.vehiclemanagementsubdomain.datalayer;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VehicleRepository extends JpaRepository<Vehicle, Integer> {

    List<Vehicle> findAllVehiclesByUserId(String userId);

    Vehicle findVehicleByUserIdAndVehicleIdentifier_VehicleId(String userId, String vehicleId);

}
