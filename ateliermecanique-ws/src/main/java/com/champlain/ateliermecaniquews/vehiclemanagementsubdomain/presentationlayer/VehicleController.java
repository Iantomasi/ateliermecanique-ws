package com.champlain.ateliermecaniquews.vehiclemanagementsubdomain.presentationlayer;


import com.champlain.ateliermecaniquews.vehiclemanagementsubdomain.businesslayer.VehicleService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class VehicleController {

    final private VehicleService vehicleService;

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping("/customers/{customerId}/vehicles")
    public ResponseEntity<List<VehicleResponseModel>> getAllVehiclesByCustomerId(@PathVariable String customerId) {
        List<VehicleResponseModel> vehicles = vehicleService.getAllVehiclesByCustomerId(customerId);
        if (vehicles == null || vehicles.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(vehicles);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping("/customers/{customerId}/vehicles/{vehicleId}")
    public ResponseEntity<VehicleResponseModel> getVehicleByVehicleId(@PathVariable String customerId, @PathVariable String vehicleId) {
        VehicleResponseModel vehicle = vehicleService.getVehicleByVehicleId(customerId, vehicleId);
        if (vehicle == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(vehicle);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @PutMapping(value = "/customers/{customerId}/vehicles/{vehicleId}", consumes = "application/json")
    public ResponseEntity<VehicleResponseModel> updateVehicleByVehicleId(@RequestBody VehicleRequestModel vehicleRequestModel, @PathVariable String customerId, @PathVariable String vehicleId) {
        VehicleResponseModel vehicle = vehicleService.updateVehicleByVehicleId(vehicleRequestModel, customerId, vehicleId);
        if (vehicle == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(vehicle);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @PostMapping(value = "/customers/{customerId}/vehicles", consumes = "application/json")
    public ResponseEntity<VehicleResponseModel> addVehicleToCustomerAccount(@PathVariable String customerId, @RequestBody VehicleRequestModel vehicleRequestModel) {
        VehicleResponseModel vehicle = vehicleService.addVehicleToCustomerAccount(customerId, vehicleRequestModel);
        if (vehicle == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(vehicle);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @DeleteMapping(value = "/customers/{customerId}/vehicles/{vehicleId}")
    public ResponseEntity<Void> deleteVehicleByVehicleId(@PathVariable String customerId, @PathVariable String vehicleId){
        vehicleService.deleteVehicleByVehicleId(customerId, vehicleId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
