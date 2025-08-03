package com.cdac.service;

import com.cdac.dao.UserDao;
import com.cdac.dao.VehicleDao;
import com.cdac.dto.VehicleDto;
import com.cdac.entities.UserEntity;
import com.cdac.entities.VehicleEntity;
import com.cdac.service.VehicleService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VehicleServiceImpl implements VehicleService {

    @Autowired
    private VehicleDao vehicleDao;

    @Autowired
    private UserDao userDao;

    private VehicleDto convertToDto(VehicleEntity entity) {
        VehicleDto dto = new VehicleDto();
//        dto.setId(entity.getId());
        dto.setUserId(entity.getUser().getId());
        dto.setModel(entity.getModel());
        dto.setType(entity.getType());
        dto.setRegNumber(entity.getRegNumber());
        return dto;
    }

    private VehicleEntity convertToEntity(VehicleDto dto) {
        VehicleEntity entity = new VehicleEntity();
        UserEntity user = userDao.findById(dto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + dto.getUserId()));
        entity.setUser(user);
        entity.setModel(dto.getModel());
        entity.setType(dto.getType());
        entity.setRegNumber(dto.getRegNumber());
        return entity;
    }

    @Override
    public VehicleDto addVehicle(VehicleDto vehicleDto) {
        VehicleEntity vehicle = convertToEntity(vehicleDto);
        VehicleEntity saved = vehicleDao.save(vehicle);
        return convertToDto(saved);
    }

    @Override
    public VehicleDto updateVehicle(Long id, VehicleDto vehicleDto) {
        VehicleEntity existing = vehicleDao.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Vehicle not found with ID: " + id));

        existing.setModel(vehicleDto.getModel());
        existing.setType(vehicleDto.getType());
        existing.setRegNumber(vehicleDto.getRegNumber());

        if (!existing.getUser().getId().equals(vehicleDto.getUserId())) {
            UserEntity newUser = userDao.findById(vehicleDto.getUserId())
                    .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + vehicleDto.getUserId()));
            existing.setUser(newUser);
        }

        VehicleEntity updated = vehicleDao.save(existing);
        return convertToDto(updated);
    }

    @Override
    public void deleteVehicle(Long id) {
        if (!vehicleDao.existsById(id)) {
            throw new EntityNotFoundException("Vehicle not found with ID: " + id);
        }
        vehicleDao.deleteById(id);
    }

    @Override
    public VehicleDto getVehicleById(Long id) {
        VehicleEntity vehicle = vehicleDao.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Vehicle not found with ID: " + id));
        return convertToDto(vehicle);
    }

    @Override
    public List<VehicleDto> getAllVehicles() {
        return vehicleDao.findAll()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<VehicleDto> getVehiclesByUserId(Long userId) {
        return vehicleDao.findByUserId(userId)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
}
