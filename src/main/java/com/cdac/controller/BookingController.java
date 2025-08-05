package com.cdac.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cdac.dto.BookingDto;
import com.cdac.security.JwtUtils;
import com.cdac.service.BookingService;

import jakarta.servlet.http.HttpServletRequest;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/bookings")
public class BookingController {
	
	@Autowired
    private BookingService bookingService;

    // ✅ USER: Get bookings of logged-in user
    @GetMapping("/user")
    @PreAuthorize("hasRole('USER')")
    public List<BookingDto> getBookingsForLoggedInUser(@AuthenticationPrincipal UserDetails userDetails) {
        return bookingService.getBookingsByUserEmail(userDetails.getUsername());
    }

    // ✅ ADMIN: Get all bookings
    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public List<BookingDto> getAllBookingsForAdmin() {
        return bookingService.getAllBookings();
    }

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
