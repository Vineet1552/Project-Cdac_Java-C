package com.cdac.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cdac.dto.AuthResp;
import com.cdac.dto.SignInDto;
import com.cdac.dto.WasherDto;
import com.cdac.security.JwtUtils;
import com.cdac.service.WasherService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/washers")
@RequiredArgsConstructor
public class WasherAuthController {

    private final WasherService washerService;
    
    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/signup")
    public ResponseEntity<AuthResp> registerWasher(@RequestBody @Valid WasherDto washerDto) {
    	
    	// Default role to ROLE_WASHER if not provided
        if (washerDto.getRole() == null || washerDto.getRole().isBlank()) {
            washerDto.setRole("ROLE_WASHER");
        }
    	
        washerService.registerWasher(washerDto); // register only

        // Generate token manually
        String token = jwtUtils.generateJwtToken(
            new UsernamePasswordAuthenticationToken(
                washerDto.getEmail(), 
                null, 
                List.of(new SimpleGrantedAuthority("ROLE_WASHER"))
            )
        );

        return ResponseEntity.ok(new AuthResp("Washer registered successfully", token));
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthResp> login(@RequestBody SignInDto dto) {
        String token = washerService.loginWasher(dto.getEmail(), dto.getPassword());
        return ResponseEntity.ok(new AuthResp("Washer login successful", token));
    }
}
