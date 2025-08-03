package com.cdac.service;

import java.util.List;

import com.cdac.dto.VehicleDto;

public interface VehicleService {
	    VehicleDto addVehicle(VehicleDto vehicleDto);
	    VehicleDto updateVehicle(Long id, VehicleDto vehicleDto);
	    void deleteVehicle(Long id);
	    VehicleDto getVehicleById(Long id);
	    List<VehicleDto> getAllVehicles();
	    List<VehicleDto> getVehiclesByUserId(Long userId);
}
