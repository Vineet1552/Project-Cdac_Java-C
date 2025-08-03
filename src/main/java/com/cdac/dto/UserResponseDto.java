package com.cdac.dto;
import java.util.List;

import com.cdac.entities.*;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class UserResponseDto {
	private Long id;
    private String fullName;
    private String email;
    private Role role;
    private String phone;
    private Boolean isActive;
    
    private List<VehicleDto> vehicles;
}
