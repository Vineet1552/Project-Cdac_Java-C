package com.cdac.service;



import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdac.custom_exceptions.ResourceNotFoundException;
import com.cdac.dao.PackageDao;
import com.cdac.dao.WasherDao;
import com.cdac.dto.PackageDto;
import com.cdac.dto.PackageRespDto;
import com.cdac.entities.PackageEntity;
import com.cdac.entities.WasherEntity;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class PackageServiceImpl implements PackageService {

    @Autowired
    private PackageDao packageDao;

    @Autowired
    private WasherDao washerDao;

    
    @Override
    public List<PackageRespDto> getAllPackagesForWasher(Long washerId) {
        WasherEntity washer = washerDao.findById(washerId)
                .orElseThrow(() -> new ResourceNotFoundException("Washer not found with id: " + washerId));

        List<PackageEntity> packages = packageDao.findByWasher(washer);

        return packages.stream()
                .map(this::entityToDto1)
                .collect(Collectors.toList());
    }
    
    private PackageRespDto entityToDto1(PackageEntity pkg) {
        PackageRespDto dto = new PackageRespDto();
        dto.setId(pkg.getId());
        dto.setName(pkg.getName());
        dto.setDescription(pkg.getDescription());
        dto.setPrice(pkg.getPrice());
        dto.setDuration(pkg.getDuration());
        return dto;
    }
    
    @Override
    public PackageDto createPackage(PackageDto packageDto, String washerEmail) {
        WasherEntity washer = washerDao.findByEmail(washerEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Washer not found with email: " + washerEmail));

        PackageEntity entity = dtoToEntity(packageDto);
        entity.setWasher(washer);

        PackageEntity saved = packageDao.save(entity);
        return entityToDto(saved);
    }

    @Override
    public PackageDto updatePackage(Long id, PackageDto packageDto, String washerEmail) {
        PackageEntity existing = packageDao.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Package not found with ID: " + id));

        if (!existing.getWasher().getEmail().equals(washerEmail)) {
            throw new RuntimeException("Unauthorized access: This package does not belong to the logged-in washer");
        }

        existing.setName(packageDto.getName());
        existing.setDescription(packageDto.getDescription());
        existing.setPrice(packageDto.getPrice());
        existing.setDuration(packageDto.getDuration());
      //  existing.setImage(packageDto.getImage());

        PackageEntity updated = packageDao.save(existing);
        return entityToDto(updated);
    }

    @Override
    public String deletePackage(Long id, String washerEmail) {
        PackageEntity existing = packageDao.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Package not found with ID: " + id));

        if (!existing.getWasher().getEmail().equals(washerEmail)) {
            throw new RuntimeException("Unauthorized access: This package does not belong to the logged-in washer");
        }

        packageDao.delete(existing);
        return "Package deleted successfully.";
    }

    @Override
    public PackageDto getPackageById(Long id) {
        PackageEntity entity = packageDao.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Package not found with ID: " + id));

        return entityToDto(entity);
    }

//    @Override
//    public List<PackageDto> getAllPackagesForWasher(String washerEmail) {
//        WasherEntity washer = washerDao.findByEmail(washerEmail)
//                .orElseThrow(() -> new ResourceNotFoundException("Washer not found with email: " + washerEmail));
//
//        List<PackageEntity> packages = packageDao.findByWasher(washer);
//
//        return packages.stream()
//                .map(this::entityToDto)
//                .collect(Collectors.toList());
//    }
    @Override
    public List<PackageDto> getAllPackagesForWasher(String washerEmail) {
        WasherEntity washer = washerDao.findByEmail(washerEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Washer not found with email: " + washerEmail));

        List<PackageEntity> packages = packageDao.findByWasher(washer);

        return packages.stream()
                .map(this::entityToDto)
                .collect(Collectors.toList());
    }

    
    // ------------------------
    // DTO ↔ Entity conversion
    // ------------------------

    private PackageDto entityToDto(PackageEntity entity) {
        PackageDto dto = new PackageDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setPrice(entity.getPrice());
        dto.setDuration(entity.getDuration());
       // dto.setImage(entity.getImage());
        // You may optionally include washerId or email if needed
        return dto;
    }

    private PackageEntity dtoToEntity(PackageDto dto) {
    	
        PackageEntity entity = new PackageEntity();
        if (dto.getId() != null) {
    		entity.setId(dto.getId());
        }
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setPrice(dto.getPrice());
        entity.setDuration(dto.getDuration());
        //entity.setImage(dto.getImage());
        return entity;
    }
}
