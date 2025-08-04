package com.cdac.controller;

import com.cdac.dto.SignInDto;
import com.cdac.dto.SignupReqDto;
import com.cdac.dto.AuthResp;
import com.cdac.security.JwtUtils;
import com.cdac.service.UserService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    // ✅ Register new user
    @PostMapping("/signup")
    public ResponseEntity<AuthResp> signUp(@RequestBody @Valid SignupReqDto signupDto) {
        userService.signUp(signupDto);
        // Optional: auto-login and return JWT on signup
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signupDto.getEmail(), signupDto.getPassword())
        );
        String token = jwtUtils.generateJwtToken(authentication);
        return ResponseEntity.ok(new AuthResp("User registered successfully", token));
    }

    // ✅ Login and return JWT
    @PostMapping("/signin")
    public ResponseEntity<AuthResp> signIn(@RequestBody @Valid SignInDto signInDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        signInDto.getEmail(), signInDto.getPassword()
                )
        );

        String token = jwtUtils.generateJwtToken(authentication);
        return ResponseEntity.ok(new AuthResp("Login successful", token));
    }
}
