package com.cdac.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class BookingCreateDto {
	@NotNull
    private Long vehicleId;

    @NotNull
    private Long packageId;

    @NotNull
    private Long washerId;

    @NotNull
    private LocalDateTime bookingAt; // use date+time

    private String remarks;
}
