package com.cdac.service;

import java.util.List;


import com.cdac.dto.AuthResp;
import com.cdac.dto.SignInDto;
import com.cdac.dto.SignupReqDto;
import com.cdac.dto.UserRequestDto;
import com.cdac.dto.UserResponseDto;
import org.springframework.security.core.userdetails.UserDetails;


public interface UserService {
	
	
//	UserResponseDto getUserById(Long id, String requesterEmail);
//	UserResponseDto getUserById(Long id);
//		UserResponseDto createUser(UserRequestDto userDto);
	    List<UserResponseDto> getAllUsers();
	    void deleteUser(Long id);
	    UserResponseDto updateUser(Long id, UserRequestDto userDto);
	    
	    
	    // new updated
//	    UserResponseDto getUserWithVehicles(Long id); 
	    UserResponseDto signUp(SignupReqDto dto);
	  //  AuthResp registerUser(SignupReqDto dto);
	   // AuthResp authenticateUser(SignInDto dto);
	 // in UserService
//	    UserResponseDto getUserById(Long id, String requesterEmail);
	    
	    
	    UserResponseDto getUserByEmail(String email);
	    UserDetails loadUserByUsername(String username);
	   

	    
}

