package com.cdac.controller;

import com.cdac.dto.ApiResponse;
import com.cdac.dto.VehicleDto;
import com.cdac.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehicles")
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;

    @PostMapping
    public ResponseEntity<VehicleDto> createVehicle(@RequestBody VehicleDto vehicleDto) {
        VehicleDto savedVehicle = vehicleService.addVehicle(vehicleDto);
        return ResponseEntity.ok(savedVehicle);
    }
    @GetMapping
    public ResponseEntity<List<VehicleDto>> getAllVehicles() {
        return ResponseEntity.ok(vehicleService.getAllVehicles());
    }

    @GetMapping("/{id}")
    public ResponseEntity<VehicleDto> getVehicleById(@PathVariable Long id) {
        return ResponseEntity.ok(vehicleService.getVehicleById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<VehicleDto> updateVehicle(@PathVariable Long id, @RequestBody VehicleDto vehicleDto) {
        return ResponseEntity.ok(vehicleService.updateVehicle(id, vehicleDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteVehicle(@PathVariable Long id) {
        vehicleService.deleteVehicle(id);
        return ResponseEntity.ok(new ApiResponse("Vehicle deleted successfully"));
    }
}
