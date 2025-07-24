package com.cdac.entities;

import java.time.LocalDateTime;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "bookings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class BookingEntity extends BaseEntity {
	
//	5. Booking Entity
//	Fields: id, userId, vehicleId, packageId, washerId, bookingDate, status, remarks
	 	@NotNull(message = "User is required")
	    @ManyToOne(fetch = FetchType.LAZY)
	    @JoinColumn(name = "user_id", nullable = false)
	    private UserEntity user;

	    @NotNull(message = "Vehicle is required")
	    @ManyToOne(fetch = FetchType.LAZY)
	    @JoinColumn(name = "vehicle_id", nullable = false)
	    private VehicleEntity vehicle;
	    
//	    @NotNull(message = "Package is required")
//	    @ManyToOne(fetch = FetchType.LAZY)
//	    @JoinColumn(name = "package_id", nullable = false)
//	    private PackageEntity servicePackage;
	    
	    @NotNull(message = "Washer is required")
	    @ManyToOne(fetch = FetchType.LAZY)
	    @JoinColumn(name = "washer_id", nullable = false)
	    private WasherEntity washer;

	    @NotNull(message = "Booking date is required")
	    @FutureOrPresent(message = "Booking date must be now or in the future")
	    @Column(name = "booking_date", nullable = false)
	    private LocalDateTime bookingDate;

	    @NotBlank(message = "Status is required")
	    @Column(nullable = false)
	    private String status; // e.g., "PENDING", "CONFIRMED", "COMPLETED", "CANCELLED"

	    private String remarks;
	    
	 // One-to-One with Payment
	    @OneToOne(mappedBy = "booking", cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
	    private PaymentEntity payment;
	    
	    @ManyToOne(fetch = FetchType.LAZY)
	    @JoinColumn(name = "package_id", nullable = false)
	    private PackageEntity packageEntity;

	
}
