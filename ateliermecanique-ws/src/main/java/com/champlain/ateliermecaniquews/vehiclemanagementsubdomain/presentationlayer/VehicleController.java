package com.champlain.ateliermecaniquews.vehiclemanagementsubdomain.presentationlayer;


import com.champlain.ateliermecaniquews.vehiclemanagementsubdomain.businesslayer.VehicleService;
import lombok.AllArgsConstructor;
import lombok.Generated;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class VehicleController {

    final private VehicleService vehicleService;

    @GetMapping("/customers/{customerId}/vehicles")
    public ResponseEntity<List<VehicleResponseModel>> getAllVehiclesForCustomer(@PathVariable String customerId) {
        List<VehicleResponseModel> vehicles = vehicleService.getAllVehiclesForCustomer(customerId);
        if (vehicles == null || vehicles.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(vehicles);
    }

    @GetMapping("/customers/{customerId}/vehicles/{vehicleId}")
    public ResponseEntity<VehicleResponseModel> getVehicleByVehicleId(@PathVariable String customerId, @PathVariable String vehicleId) {
        VehicleResponseModel vehicle = vehicleService.getVehicleByVehicleId(customerId, vehicleId);
        if (vehicle == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(vehicle);
    }

    @PutMapping(value = "/customers/{customerId}/vehicles/{vehicleId}", consumes = "application/json")
    public ResponseEntity<VehicleResponseModel> updateVehicleByVehicleId(@RequestBody VehicleRequestModel vehicleRequestModel, @PathVariable String customerId, @PathVariable String vehicleId) {
        VehicleResponseModel vehicle = vehicleService.updateVehicleByVehicleId(vehicleRequestModel, customerId, vehicleId);
        if (vehicle == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(vehicle);
    }


    @PostMapping(value = "/customers/{customerId}/vehicles", consumes = "application/json")
    public ResponseEntity<VehicleResponseModel> addVehicleToCustomer(@PathVariable String customerId, @RequestBody VehicleRequestModel vehicleRequestModel) {
        VehicleResponseModel vehicle = vehicleService.addVehicleToCustomer(customerId, vehicleRequestModel);
        if (vehicle == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(vehicle);
    }

}
