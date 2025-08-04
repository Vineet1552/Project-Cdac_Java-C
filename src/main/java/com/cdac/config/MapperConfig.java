package com.cdac.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cdac.dto.VehicleDto;
import com.cdac.entities.VehicleEntity;

@Configuration
public class MapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();

        // Explicit mapping from vehicle.user.id → vehicleDto.userId
        mapper.addMappings(new PropertyMap<VehicleEntity, VehicleDto>() {
            @Override
            protected void configure() {
                map(source.getUser().getId(), destination.getUserId());
            }
        });

        return mapper;
    }
}
