package com.cdac.service;

import java.util.List;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdac.custom_exceptions.ResourceNotFoundException;
import com.cdac.dao.UserDao;
import com.cdac.dto.UserRequestDto;
import com.cdac.dto.UserResponseDto;
import com.cdac.dto.VehicleDto;
import com.cdac.entities.UserEntity;

import jakarta.transaction.Transactional;

import com.cdac.entities.*;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
    private UserDao userDao;
	
	
	
	
	private UserEntity toEntity(UserRequestDto dto) {
	        UserEntity user = new UserEntity();
	        user.setFullName(dto.getFullName());
	        user.setEmail(dto.getEmail());
	        user.setPassword(dto.getPassword());
	        user.setPhone(dto.getPhone());
	        user.setIsActive(dto.getIsActive());
	        user.setRole(dto.getRole());
//	        user.setRole(Role.valueOf(dto.getRole().toUpperCase()));
	        return user;
	}
	
	private UserResponseDto toResponseDto(UserEntity entity) {
        UserResponseDto dto = new UserResponseDto();
        dto.setId(entity.getId());
        dto.setFullName(entity.getFullName());
        dto.setEmail(entity.getEmail());
        dto.setPhone(entity.getPhone());
        dto.setIsActive(entity.getIsActive());
        dto.setRole(entity.getRole());
        return dto;
    }
	 @Override
	    public UserResponseDto createUser(UserRequestDto userDto) {
	        UserEntity user = toEntity(userDto);
	        return toResponseDto(userDao.save(user));
	    }

	    @Override
	    public UserResponseDto updateUser(Long id, UserRequestDto userDto) {
	        UserEntity user = userDao.findById(id)
	                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + id));

	        user.setFullName(userDto.getFullName());
	        user.setEmail(userDto.getEmail());
	        user.setPassword(userDto.getPassword());
	        user.setPhone(userDto.getPhone());
	        user.setIsActive(userDto.getIsActive());
	        user.setRole(userDto.getRole());
//	        user.setRole(Role.valueOf(userDto.getRole().toUpperCase()));

	        return toResponseDto(userDao.save(user));
	    }

	    @Override
	    @Transactional
	    public UserResponseDto getUserById(Long id) {
	        UserEntity user = userDao.findById(id)
	                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + id));
	        
	        
	        UserResponseDto dto = new UserResponseDto();
	        dto.setId(user.getId());
	        dto.setFullName(user.getFullName());
	        dto.setEmail(user.getEmail());
	        dto.setPhone(user.getPhone());
	        dto.setRole(user.getRole());
	        dto.setIsActive(user.getIsActive());
	        
	     // Convert list of VehicleEntity to VehicleDto
	        List<VehicleDto> vehicles = user.getVehicles().stream().map(vehicle -> {
	            VehicleDto v = new VehicleDto();
//	            v.setId(vehicle.getId());
	            v.setUserId(user.getId());
	            v.setModel(vehicle.getModel());
	            v.setType(vehicle.getType());
	            v.setRegNumber(vehicle.getRegNumber());
	            return v;
	        }).collect(Collectors.toList());
	        
	        
	        dto.setVehicles(vehicles);
	        
	        return dto;
	    }

	    @Override
	    public List<UserResponseDto> getAllUsers() {
	        return userDao.findAll().stream()
	                .map(this::toResponseDto)
	                .collect(Collectors.toList());
	    }

	    @Override
	    public void deleteUser(Long id) {
	        UserEntity user = userDao.findById(id)
	                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + id));
	        userDao.delete(user);
	    }
	}
