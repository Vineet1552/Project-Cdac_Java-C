	package com.cdac.service;

import java.util.List;

import com.cdac.dto.BookingCreateDto;
import com.cdac.dto.BookingDto;

import jakarta.servlet.http.HttpServletRequest;

public interface BookingService {
//	BookingDto createBooking(BookingDto dto, String userEmail);
//    BookingDto getBookingById(Long id);
//    List<BookingDto> getAllBookings();
//    BookingDto updateBooking(Long id, BookingDto bookingDto);
//    void deleteBooking(Long id);
//    List<BookingDto> getBookingsByUserEmail(String email);
//    List<BookingDto> getBookingsByWasherId(Long washerId);
	
	BookingDto createBooking(BookingCreateDto createDto, String userEmail);
    BookingDto getBookingById(Long id);
    List<BookingDto> getAllBookings();
    BookingDto updateBooking(Long id, BookingDto bookingDto);
    void deleteBooking(Long id);
    List<BookingDto> getBookingsByUserEmail(String email);
    List<BookingDto> getBookingsByWasherId(Long washerId);

}
