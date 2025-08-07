package com.cdac.controller;

import com.cdac.dto.PackageDto;
import com.cdac.dto.PackageRespDto;
import com.cdac.service.PackageService;
import com.cdac.service.WasherService;
import com.cdac.security.JwtUtils;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/washer/packages")
public class PackageController {

    @Autowired
    private PackageService packageService;

    @Autowired
    private JwtUtils jwtUtils;
    
    @Autowired
    private WasherService washerService;

    // Create new service package
    @PostMapping
    public ResponseEntity<PackageDto> createPackage(@Valid @RequestBody PackageDto packageDto,
                                                    Authentication authentication) {
        String washerEmail = authentication.getName();
        PackageDto createdPackage = packageService.createPackage(packageDto, washerEmail);
        return ResponseEntity.ok(createdPackage);
    }

    // Update existing package (only if owned by logged-in washer)
    @PutMapping("/{id}")
    public ResponseEntity<PackageDto> updatePackage(@PathVariable Long id,
                                                    @Valid @RequestBody PackageDto packageDto,
                                                    Authentication authentication) {
        String washerEmail = authentication.getName();
        PackageDto updatedPackage = packageService.updatePackage(id, packageDto, washerEmail);
        return ResponseEntity.ok(updatedPackage);
    }

    // Delete package (only if owned by logged-in washer)
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePackage(@PathVariable Long id,
                                                Authentication authentication) {
        String washerEmail = authentication.getName();
        String message = packageService.deletePackage(id, washerEmail);
        return ResponseEntity.ok(message);
    }

    // Get specific package (anyone can view a package by ID)
    @GetMapping("/{id}")
    public ResponseEntity<PackageDto> getPackageById(@PathVariable Long id) {
        PackageDto pkg = packageService.getPackageById(id);
        return ResponseEntity.ok(pkg);
    }

    // Get all packages of the currently logged-in washer
    @GetMapping
    public ResponseEntity<List<PackageDto>> getAllPackages(Authentication authentication) {
        String washerEmail = authentication.getName();
        System.out.println("helllllllloooooo");
        List<PackageDto> washerPackages = packageService.getAllPackagesForWasher(washerEmail);
        return ResponseEntity.ok(washerPackages);
    }
    
    // public
    @GetMapping("/public")
    public ResponseEntity<List<PackageRespDto>> getAllPackages(@RequestParam Long washerId) {
        List<PackageRespDto> packages = packageService.getAllPackagesForWasher(washerId);
        return ResponseEntity.ok(packages);
    }
    
//    @GetMapping
//    public ResponseEntity<List<PackageDto>> getAllPackages(@RequestParam(required = false) String washerEmail) {
//        List<PackageDto> washerPackages = washerEmail != null 
//            ? washerService.getAllPackagesForWasher(washerEmail)
//            : washerService.getAllWasherPackages();
//        return ResponseEntity.ok(washerPackages);
//    }
}
