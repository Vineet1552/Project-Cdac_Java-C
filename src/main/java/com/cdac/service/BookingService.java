package com.cdac.service;

import java.util.List;

import com.cdac.dto.BookingDto;

public interface BookingService {
    BookingDto createBooking(BookingDto bookingDto);
    BookingDto getBookingById(Long id);
    List<BookingDto> getAllBookings();
    BookingDto updateBooking(Long id, BookingDto bookingDto);
    void deleteBooking(Long id);
}
