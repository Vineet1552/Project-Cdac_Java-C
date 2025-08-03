package com.cdac.service;

import java.util.List;

import com.cdac.dto.PackageDto;

public interface PackageService {
	
	PackageDto createPackage(PackageDto packageDto);

    PackageDto updatePackage(Long id, PackageDto packageDto);

    String deletePackage(Long id);

    PackageDto getPackageById(Long id);

    List<PackageDto> getAllPackages();
}
