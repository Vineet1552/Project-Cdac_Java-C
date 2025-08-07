package com.cdac.controller;

import com.cdac.dto.WasherDto;
import com.cdac.dto.BookingDto;
import com.cdac.dto.ReviewDto;
import com.cdac.service.WasherService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/washers")
public class WasherController {

    @Autowired
    private WasherService washerService;
    
    
//    @GetMapping
//    public ResponseEntity<List<PackageDto>> getAllPackages(@RequestParam(required = false) String washerEmail) {
//        List<PackageDto> washerPackages = washerEmail != null 
//            ? washerService.getAllPackagesForWasher(washerEmail)
//            : washerService.getAllWasherPackages();
//        return ResponseEntity.ok(washerPackages);
//    }

    // ======================== Washer Profile ========================

    // 🔒 Washer: Get their own profile
    @GetMapping("/profile")
    public ResponseEntity<WasherDto> getWasherProfile() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        WasherDto profile = washerService.getWasherProfile(email);
        return ResponseEntity.ok(profile);
    }

    // 🔒 Washer: Update their own profile
    @PutMapping("/profile")
    public ResponseEntity<WasherDto> updateWasherProfile(@Valid @RequestBody WasherDto dto) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        WasherDto updated = washerService.updateWasherProfile(email, dto);
        return ResponseEntity.ok(updated);
    }

    // 🔒 Washer: Delete their own profile
    @DeleteMapping("/profile")
    public ResponseEntity<String> deleteWasherProfile() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        washerService.deleteWasherProfile(email);
        return ResponseEntity.ok("Washer profile deleted successfully");
    }

    // ======================== Washer-specific Endpoints ========================

    // 🔒 Washer: View all their bookings
    @GetMapping("/bookings")
    public ResponseEntity<List<BookingDto>> getWasherBookings() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        List<BookingDto> bookings = washerService.getWasherBookings(email);
        return ResponseEntity.ok(bookings);
    }

    // 🔒 Washer: View all reviews given to them
    @GetMapping("/reviews")
    public ResponseEntity<List<ReviewDto>> getWasherReviews() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        List<ReviewDto> reviews = washerService.getWasherReviews(email);
        return ResponseEntity.ok(reviews);
    }

    // ======================== Admin CRUD Operations ========================

    // 🔒 Admin: Get all washers --> i want to get all with user login
    @GetMapping
    public ResponseEntity<List<WasherDto>> getAllWashers() {
        return ResponseEntity.ok(washerService.getAllWashers());
    }

    // 🔒 Admin: Get washer by ID
    @GetMapping("/{id}")
    public ResponseEntity<WasherDto> getWasherById(@PathVariable Long id) {
        return ResponseEntity.ok(washerService.getWasherById(id));
    }

    // 🔒 Admin: Update washer by ID
    @PutMapping("/{id}")
    public ResponseEntity<WasherDto> updateWasher(@PathVariable Long id, @Valid @RequestBody WasherDto washerDto) {
        return ResponseEntity.ok(washerService.updateWasher(id, washerDto));
    }

    // 🔒 Admin: Delete washer by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteWasher(@PathVariable Long id) {
        washerService.deleteWasher(id);
        return ResponseEntity.ok("Washer with ID " + id + " deleted successfully");
    }
}
