package com.cdac.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdac.dao.WasherDao;
import com.cdac.dto.WasherDto;
import com.cdac.entities.WasherEntity;
import com.cdac.custom_exceptions.ResourceNotFoundException;
import com.cdac.service.WasherService;

@Service
public class WasherServiceImpl implements WasherService {

    @Autowired
    private WasherDao washerDao;

    @Override
    public WasherDto createWasher(WasherDto dto) {
        WasherEntity entity = this.dtoToEntity(dto);
        WasherEntity saved = washerDao.save(entity);
        return this.entityToDto(saved);
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



    private WasherDto entityToDto(WasherEntity entity) {
        WasherDto dto = new WasherDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setEmail(entity.getEmail());
        dto.setPhone(entity.getPhone());
        dto.setStatus(entity.getStatus());
        dto.setRating(entity.getRating());
        dto.setArea(entity.getArea());
        return dto;
    }

    private WasherEntity dtoToEntity(WasherDto dto) {
        WasherEntity entity = new WasherEntity();
        entity.setName(dto.getName());
        entity.setEmail(dto.getEmail());
        entity.setPhone(dto.getPhone());
        entity.setStatus(dto.getStatus());
        entity.setRating(dto.getRating());
        entity.setArea(dto.getArea());
        return entity;
    }
}