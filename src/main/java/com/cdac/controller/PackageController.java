package com.cdac.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cdac.dto.PackageDto;
import com.cdac.service.PackageService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/packages")
public class PackageController {

    @Autowired
    private PackageService packageService;

    @PostMapping
    public ResponseEntity<PackageDto> createPackage(@Valid @RequestBody PackageDto dto) {
        return ResponseEntity.ok(packageService.createPackage(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PackageDto> updatePackage(@PathVariable Long id,
                                                    @Valid @RequestBody PackageDto dto) {
        return ResponseEntity.ok(packageService.updatePackage(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePackage(@PathVariable Long id) {
        packageService.deletePackage(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PackageDto> getPackage(@PathVariable Long id) {
        return ResponseEntity.ok(packageService.getPackageById(id));
    }

    @GetMapping
    public ResponseEntity<List<PackageDto>> getAllPackages() {
        return ResponseEntity.ok(packageService.getAllPackages());
    }
}
