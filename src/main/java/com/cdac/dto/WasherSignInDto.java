package com.cdac.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class WasherSignInDto {
	
    @Email
    private String email;

    @NotBlank
    private String password;
}
