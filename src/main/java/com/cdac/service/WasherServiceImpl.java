package com.cdac.service;

import java.util.List;



import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cdac.custom_exceptions.ResourceNotFoundException;
import com.cdac.dao.WasherDao;
import com.cdac.dto.BookingDto;
import com.cdac.dto.PackageDto;
import com.cdac.dto.ReviewDto;
import com.cdac.dto.WasherDto;
import com.cdac.entities.PackageEntity;
import com.cdac.entities.WasherEntity;
import com.cdac.security.JwtUtils;

import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import com.cdac.dao.PackageDao;
import com.cdac.dto.*;

@Service
@Transactional
@AllArgsConstructor
public class WasherServiceImpl implements WasherService {

    @Autowired
    private WasherDao washerDao;
    
    @Autowired
    private PackageDao packageDao;
    
    @Autowired
    private BookingService bookingService; 
    
    @Autowired
    private ReviewService reviewService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public WasherDto registerWasher(WasherDto dto) {
    	
//    	// Check if email already exists
//        if (washerDao.findByEmail(washerDto.getEmail()).isPresent()) {
//            throw new IllegalStateException("Email already registered");
//        }
    	
        if (washerDao.findAll().stream().anyMatch(w -> w.getEmail().equals(dto.getEmail()))) {
            throw new IllegalArgumentException("Email already exists");
        }

        if (dto.getPassword() == null || dto.getPassword().isBlank()) {
            throw new IllegalArgumentException("Password cannot be null or blank");
        }

        WasherEntity entity = dtoToEntity(dto);
        WasherEntity saved = washerDao.save(entity);

        return entityToDto(saved); 
    }

//    @Override
//    public String loginWasher(String email, String password) {
//        WasherEntity washer = washerDao.findAll().stream()
//            .filter(w -> w.getEmail().equals(email))
//            .findFirst()
//            .orElseThrow(() -> new RuntimeException("Washer not found"));
//
//        if (!passwordEncoder.matches(password, washer.getPassword())) {
//            throw new RuntimeException("Invalid credentials");
//        }
//
//        return jwtUtils.generateJwtToken(
//            new UsernamePasswordAuthenticationToken(
//                email,
//                null,
//                List.of(new SimpleGrantedAuthority("ROLE_WASHER"))
//            )
//        );
//    }
    @Override
    public String loginWasher(String email, String password) {
        // 1. Find washer by email
        WasherEntity washer = washerDao.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Washer not found with email: " + email));

        // 2. Check password
        if (!passwordEncoder.matches(password, washer.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        // 3. Create authentication object for JWT
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                washer.getEmail(),
                null,
                List.of(new SimpleGrantedAuthority("ROLE_WASHER"))
        );

        // 4. Generate JWT
        return jwtUtils.generateJwtToken(authentication);
    }


    @Override
    public WasherDto getWasherProfile(String email) {
        WasherEntity washer = washerDao.findAll().stream()
            .filter(w -> w.getEmail().equals(email))
            .findFirst()
            .orElseThrow(() -> new ResourceNotFoundException("Washer", "email", email));

        return entityToDto(washer);
    }

    @Override
    public WasherDto updateWasherProfile(String email, WasherDto dto) {
        WasherEntity washer = washerDao.findAll().stream()
            .filter(w -> w.getEmail().equals(email))
            .findFirst()
            .orElseThrow(() -> new ResourceNotFoundException("Washer", "email", email));

        washer.setName(dto.getName());
        washer.setPhone(dto.getPhone());
        washer.setStatus(dto.getStatus());
        washer.setRating(dto.getRating());
        washer.setArea(dto.getArea());

        // If new password provided, encode and update
        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            washer.setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        WasherEntity updated = washerDao.save(washer);
        return entityToDto(updated);
    }

    @Override
    public void deleteWasherProfile(String email) {
        WasherEntity washer = washerDao.findAll().stream()
            .filter(w -> w.getEmail().equals(email))
            .findFirst()
            .orElseThrow(() -> new ResourceNotFoundException("Washer", "email", email));

        washerDao.delete(washer);
    }

    @Override
    public WasherDto updateWasher(Long id, WasherDto dto) {
        WasherEntity washer = washerDao.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Washer", "ID", id));

        washer.setName(dto.getName());
        washer.setEmail(dto.getEmail());
        washer.setPhone(dto.getPhone());
        washer.setStatus(dto.getStatus());
        washer.setRating(dto.getRating());
        washer.setArea(dto.getArea());

        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            washer.setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        WasherEntity updated = washerDao.save(washer);
        return this.entityToDto(updated);
    }

    @Override
    public WasherDto getWasherById(Long id) {
        WasherEntity washer = washerDao.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Washer", "ID", id));
        return this.entityToDto(washer);
    }

    @Override
    public List<WasherDto> getAllWashers() {
        List<WasherEntity> washers = washerDao.findAll();
        return washers.stream().map(this::entityToDto).collect(Collectors.toList());
    }
    
    

   
    
    
    
    
    

    @Override
    public void deleteWasher(Long id) {
        WasherEntity washer = washerDao.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Washer", "ID", id));
        washerDao.delete(washer);
    }
    
    @Override
    public List<BookingDto> getWasherBookings(String email) {
        WasherEntity washer = washerDao.findAll().stream()
            .filter(w -> w.getEmail().equals(email))
            .findFirst()
            .orElseThrow(() -> new ResourceNotFoundException("Washer", "email", email));

        // Assuming bookingService has method: getBookingsByWasherId
        return bookingService.getBookingsByWasherId(washer.getId());
    }

    @Override
    public List<ReviewDto> getWasherReviews(String email) {
        WasherEntity washer = washerDao.findAll().stream()
            .filter(w -> w.getEmail().equals(email))
            .findFirst()
            .orElseThrow(() -> new ResourceNotFoundException("Washer", "email", email));

        // Assuming reviewService has method: getReviewsForWasherId
        return reviewService.getReviewsForWasherId(washer.getId());
    }

    private WasherDto entityToDto(WasherEntity entity) {
        WasherDto dto = new WasherDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setEmail(entity.getEmail());
        dto.setPassword(entity.getPassword());
        dto.setPhone(entity.getPhone());
        dto.setStatus(entity.getStatus());
        dto.setRating(entity.getRating());
        dto.setArea(entity.getArea());
        return dto;
    }

    private WasherEntity dtoToEntity(WasherDto dto) {
        WasherEntity entity = new WasherEntity();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setEmail(dto.getEmail());
        entity.setPhone(dto.getPhone());
        entity.setStatus(dto.getStatus());
        entity.setRating(dto.getRating());
        entity.setArea(dto.getArea());

        if (dto.getPassword() != null) {
            entity.setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        return entity;
    }
}