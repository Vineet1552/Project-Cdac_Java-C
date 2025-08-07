package com.cdac.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class PackageDto {
	private Long id;
    private String name;
    private String description;
    private Double price;
    private Integer duration;
	
//	private Long id;
//
//    @NotBlank(message = "Package name is required")
//    private String name;
//
//    @NotBlank(message = "Description is required")
//    private String description;
//
//    @NotNull(message = "Price is required")
//    @Positive(message = "Price must be greater than zero")
//    private Double price;
//
//    @NotNull(message = "Duration is required")
//    @Positive(message = "Duration must be a positive number")
//    private Integer duration;
//
//    private Long washerId;
}
