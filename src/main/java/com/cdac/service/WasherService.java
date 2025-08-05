package com.cdac.service;

import java.util.List;

import com.cdac.dto.BookingDto;
import com.cdac.dto.ReviewDto;
import com.cdac.dto.WasherDto;

public interface WasherService {

    WasherDto registerWasher(WasherDto washerDto);

    String loginWasher(String email, String password);

    WasherDto getWasherProfile(String email);

    WasherDto updateWasherProfile(String email, WasherDto dto);

    void deleteWasherProfile(String email);

    WasherDto getWasherById(Long id);

    WasherDto updateWasher(Long id, WasherDto dto);

    List<WasherDto> getAllWashers();

    void deleteWasher(Long id);

    // ✅ NEW:
    List<BookingDto> getWasherBookings(String email);

    List<ReviewDto> getWasherReviews(String email);
}
