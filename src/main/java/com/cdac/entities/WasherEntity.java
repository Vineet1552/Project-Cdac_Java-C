package com.cdac.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "washers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WasherEntity extends BaseEntity {

    @NotBlank(message = "Name is required")
    @Column(nullable = false)
    private String name;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    @Column(nullable = false, unique = true)
    private String email;
    
    @NotBlank(message = "Password is required")
	@Column(nullable = false)
	private String password;

    @NotBlank(message = "Phone number is required")
    @Column(nullable = false)
    private String phone;

    @NotBlank(message = "Status is required")
    @Column(nullable = false)
    private String status; // Example: "Available", "Busy", etc.

    @PositiveOrZero(message = "Rating must be 0 or higher")
    @Column(nullable = false)
    private Double rating; // e.g., 4.5 stars

    @NotBlank(message = "Area is required")
    @Column(nullable = false)
    private String area; // e.g., locality or service area
    
    
//    //--------------->
//    @NotBlank(message = "Role is required")
//    @Column(nullable = false)
//    private String role; // e.g., "ROLE_WASHER"
    
 // Relationships
    @OneToMany(mappedBy = "washer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BookingEntity> bookings = new ArrayList<>();

    @OneToMany(mappedBy = "washer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReviewEntity> reviews = new ArrayList<>();

}
