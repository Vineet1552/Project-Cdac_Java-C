package com.cdac.service;

import java.util.List;

import com.cdac.dto.VehicleDto;

public interface VehicleService {
    VehicleDto addVehicle(VehicleDto vehicleDto, String userEmail);

    List<VehicleDto> getAllVehicles();

    VehicleDto getVehicleById(Long id);

    VehicleDto updateVehicle(Long id, VehicleDto vehicleDto);

    void deleteVehicle(Long id);

    List<VehicleDto> getVehiclesByUser(String userEmail);

}
