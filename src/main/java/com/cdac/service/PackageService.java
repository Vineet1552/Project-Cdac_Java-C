package com.cdac.service;

import java.util.List;

import com.cdac.dto.PackageDto;

public interface PackageService {
	
    PackageDto createPackage(PackageDto packageDto, String washerEmail);

    PackageDto updatePackage(Long id, PackageDto packageDto, String washerEmail);

    String deletePackage(Long id, String washerEmail);

    PackageDto getPackageById(Long id);

    List<PackageDto> getAllPackagesForWasher(String washerEmail);
}
