package com.cdac.service;

import java.util.List;
import com.cdac.dto.WasherDto;

public interface WasherService {

    WasherDto createWasher(WasherDto washerDto);

    WasherDto updateWasher(Long id, WasherDto washerDto);

    WasherDto getWasherById(Long id);

    List<WasherDto> getAllWashers();

    void deleteWasher(Long id);
}