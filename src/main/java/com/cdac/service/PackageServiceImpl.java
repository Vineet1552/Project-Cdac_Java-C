package com.cdac.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdac.dao.PackageDao;
import com.cdac.dto.PackageDto;
import com.cdac.entities.PackageEntity;
import com.cdac.custom_exceptions.ResourceNotFoundException;
import com.cdac.service.PackageService;

@Service
public class PackageServiceImpl implements PackageService {

    @Autowired
    private PackageDao packageDao;

    @Override
    public PackageDto createPackage(PackageDto dto) {
        PackageEntity entity = mapToEntity(dto);
        PackageEntity saved = packageDao.save(entity);
        return mapToDto(saved);
    }

    @Override
    public PackageDto updatePackage(Long id, PackageDto dto) {
        PackageEntity entity = packageDao.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Package", "id", id));

        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setPrice(dto.getPrice());
        entity.setDuration(dto.getDuration());

        return mapToDto(packageDao.save(entity));
    }

    @Override
    public String deletePackage(Long id) {
        PackageEntity entity = packageDao.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Package", "id", id));
        packageDao.delete(entity);
        
        return "Package with ID " + id + " has been deleted successfully.";
    }

    @Override
    public PackageDto getPackageById(Long id) {
        PackageEntity entity = packageDao.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Package", "id", id));
        return mapToDto(entity);
    }

    @Override
    public List<PackageDto> getAllPackages() {
        List<PackageEntity> list = packageDao.findAll();
        return list.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    private PackageDto mapToDto(PackageEntity entity) {
        PackageDto dto = new PackageDto();
//        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setPrice(entity.getPrice());
        dto.setDuration(entity.getDuration());
        return dto;
    }

    private PackageEntity mapToEntity(PackageDto dto) {
        PackageEntity entity = new PackageEntity();
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setPrice(dto.getPrice());
        entity.setDuration(dto.getDuration());
        return entity;
    }
}
