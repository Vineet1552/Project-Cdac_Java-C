package com.cdac.controller;

import com.cdac.dto.WasherDto;
import com.cdac.dto.WasherRespDto;
import com.cdac.custom_exceptions.ResourceNotFoundException;
import com.cdac.service.WasherService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/washers")
public class WasherController {

    @Autowired
    private WasherService washerService;

    // Create Washer
    @PostMapping
    public ResponseEntity<WasherRespDto> createWasher(@Valid @RequestBody WasherDto washerDto) {
        WasherDto createdWasher = washerService.createWasher(washerDto);
        
        WasherRespDto resp = new WasherRespDto();
        resp.setName(createdWasher.getName());
        resp.setEmail(createdWasher.getEmail());
        resp.setPhone(createdWasher.getPhone());
        resp.setStatus(createdWasher.getStatus());
        resp.setRating(createdWasher.getRating());
        resp.setArea(createdWasher.getArea());

        return ResponseEntity.ok(resp);
    }

    // Get all Washers
    @GetMapping
    public ResponseEntity<List<WasherDto>> getAllWashers() {
        return ResponseEntity.ok(washerService.getAllWashers());
    }

    // Get single Washer by ID
    @GetMapping("/{id}")
    public ResponseEntity<WasherDto> getWasherById(@PathVariable Long id) {
        WasherDto washer = washerService.getWasherById(id);
        return ResponseEntity.ok(washer);
    }

    // Update Washer by ID
    @PutMapping("/{id}")
    public ResponseEntity<WasherDto> updateWasher(@PathVariable Long id, @Valid @RequestBody WasherDto washerDto) {
        WasherDto updatedWasher = washerService.updateWasher(id, washerDto);
        return ResponseEntity.ok(updatedWasher);
    }

    // Delete Washer by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteWasher(@PathVariable Long id) {
        washerService.deleteWasher(id);
        return ResponseEntity.ok("Washer with ID " + id + " deleted successfully");
    }
}