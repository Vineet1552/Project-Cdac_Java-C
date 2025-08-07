package com.cdac.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor


public class SignInResponseDto {
	private String message;
    private String jwtToken;
    private UserResponseDto user;
}
