package com.cdac.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "new_users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor


public class UserEntity extends BaseEntity {
	
	
	
	@NotBlank(message = "full name is required	")
	@Column(name = "full_name", nullable = false)
	private String fullName;
	
	@Email(message = "Invalid email format")
	@NotBlank(message = "Email is required")
	@Column(nullable = false, unique = true)
	private String email;
	
	@NotBlank(message = "Password is required")
	@Column(nullable = false)
	private String password;
	
	@NotNull(message = "Role is required")
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Role role;
	
	@NotBlank(message = "Phone number is required")
    @Column(nullable = false)
	private String phone;
	
	@NotNull(message = "Active status must be specified")
    @Column(nullable = false)
	private Boolean isActive;
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<VehicleEntity> vehicles = new ArrayList<>();	
	
}
