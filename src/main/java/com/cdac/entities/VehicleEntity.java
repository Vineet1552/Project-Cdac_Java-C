package com.cdac.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "new_vehicles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class VehicleEntity extends BaseEntity {
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	@NotNull(message = "User is required")
	private UserEntity user;
	
	@NotBlank(message = "Model is required")
	@Column(nullable = false)
	private String model;
	
	@NotBlank(message = "Type is required")
	@Column(nullable = false)
	private String type;
	
	@NotBlank(message = "Registration number is required")
    @Column(name = "registration_number", nullable = false, unique = true)
	private String regNumber;
	
}
