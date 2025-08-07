package com.cdac.dto;

import com.cdac.entities.Role;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WasherDto {

    private Long id;

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Password is required")
    private String password;

    @NotBlank(message = "Phone number is required")
    private String phone;

    @NotBlank(message = "Status is required") 
    private String status;

    @NotNull(message = "Rating is required")
    @DecimalMin(value = "0.0", message = "Rating must be 0 or more")
    private Double rating;

    @NotBlank(message = "Area is required")
    private String area;
    
    private Role role = Role.WASHER; // default value

    
//    @NotBlank(message = "Role is required")
//    private String role; // e.g., "ROLE_WASHER"
}