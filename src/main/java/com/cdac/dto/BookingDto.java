package com.cdac.dto;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class BookingDto {
//	private Long id;
    private Long userId;
    private Long vehicleId;
    private Long packageId;
    private Long washerId;
//    private String bookingDate;
    private LocalDate bookingDate;
    private String status;
    private String remarks;
}
