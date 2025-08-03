package com.cdac.service;

import java.util.List;

import com.cdac.dto.UserRequestDto;
import com.cdac.dto.UserResponseDto;

public interface UserService {
		UserResponseDto createUser(UserRequestDto userDto);
		UserResponseDto getUserById(Long id);
	    List<UserResponseDto> getAllUsers();
	    void deleteUser(Long id);
	    UserResponseDto updateUser(Long id, UserRequestDto userDto);
	    
	    
	    // new updated
//	    UserResponseDto getUserWithVehicles(Long id); 
	    
	}

