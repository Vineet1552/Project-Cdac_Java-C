package com.cdac.controller;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cdac.dto.BookingDto;
import com.cdac.security.JwtUtils;
import com.cdac.service.BookingService;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

	@Autowired
	private BookingService bookingService;

	@Autowired
	private JwtUtils jwtUtils;

	@PostMapping
	public ResponseEntity<BookingDto> createBooking(@RequestBody BookingDto dto, HttpServletRequest request) {

		// 🔐 Extract email from JWT
		String jwt = jwtUtils.extractJwtFromRequest(request);
		String userEmail = jwtUtils.extractUsername(jwt);

		// 🛠️ Call service with authenticated user's email
		BookingDto createdBooking = bookingService.createBooking(dto, userEmail);
		return ResponseEntity.ok(createdBooking);
	}

	@GetMapping("/{id}")
	public ResponseEntity<BookingDto> getBookingById(@PathVariable Long id) {
		return ResponseEntity.ok(bookingService.getBookingById(id));
	}

	@GetMapping
	public ResponseEntity<List<BookingDto>> getAllBookings() {
		return ResponseEntity.ok(bookingService.getAllBookings());
	}

	@PutMapping("/{id}")
	public ResponseEntity<BookingDto> updateBooking(@PathVariable Long id, @RequestBody BookingDto dto) {
		return ResponseEntity.ok(bookingService.updateBooking(id, dto));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteBooking(@PathVariable Long id) {
		bookingService.deleteBooking(id);
		return ResponseEntity.noContent().build();
	}
}
