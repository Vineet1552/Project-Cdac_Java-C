package com.cdac.dto;
import com.cdac.entities.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class UserRequestDto {
	private String fullName;
    private String email;
    private String password;
    private Role role;
//    private String role;
    private String phone;
    private Boolean isActive;
}
