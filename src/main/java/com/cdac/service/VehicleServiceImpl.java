package com.cdac.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdac.custom_exceptions.ResourceNotFoundException;
import com.cdac.dao.UserDao;
import com.cdac.dao.VehicleDao;
import com.cdac.dto.VehicleDto;
import com.cdac.entities.UserEntity;
import com.cdac.entities.VehicleEntity;

@Service
public class VehicleServiceImpl implements VehicleService {

    @Autowired
    private VehicleDao vehicleDao; // using DAO interface extending JpaRepository

    @Autowired
    private UserDao userDao;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public VehicleDto addVehicle(VehicleDto vehicleDto, String userEmail) {
        UserEntity user = userDao.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", userEmail));

        VehicleEntity vehicle = modelMapper.map(vehicleDto, VehicleEntity.class);
        vehicle.setUser(user);

        VehicleEntity savedVehicle = vehicleDao.save(vehicle);
        return modelMapper.map(savedVehicle, VehicleDto.class);
    }

    @Override
    public List<VehicleDto> getAllVehicles() {
        List<VehicleEntity> vehicles = vehicleDao.findAll();
        return vehicles.stream()
                .map(vehicle -> modelMapper.map(vehicle, VehicleDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public VehicleDto getVehicleById(Long id) {
        VehicleEntity vehicle = vehicleDao.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle", "id", id));
        return modelMapper.map(vehicle, VehicleDto.class);
    }

    @Override
    public VehicleDto updateVehicle(Long id, VehicleDto vehicleDto) {
        VehicleEntity vehicle = vehicleDao.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle", "id", id));

     
        vehicle.setModel(vehicleDto.getModel());
     
        vehicle.setRegNumber(vehicleDto.getRegNumber());

        VehicleEntity updatedVehicle = vehicleDao.save(vehicle);
        return modelMapper.map(updatedVehicle, VehicleDto.class);
    }

    @Override
    public void deleteVehicle(Long id) {
        VehicleEntity vehicle = vehicleDao.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle", "id", id));
        vehicleDao.delete(vehicle);
    }

    @Override
    public List<VehicleDto> getVehiclesByUser(String userEmail) {
        UserEntity user = userDao.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", userEmail));

        List<VehicleEntity> vehicles = vehicleDao.findByUser(user);
        return vehicles.stream()
                .map(vehicle -> modelMapper.map(vehicle, VehicleDto.class))
                .collect(Collectors.toList());
    }
    
    private VehicleDto toDto(VehicleEntity vehicle) {
        VehicleDto dto = modelMapper.map(vehicle, VehicleDto.class);
        dto.setUserId(vehicle.getUser().getId());
        return dto;
    }

	}

