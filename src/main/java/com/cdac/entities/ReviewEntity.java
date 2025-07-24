package com.cdac.entities;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "reviews")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewEntity extends BaseEntity {

    @NotNull(message = "User is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @NotNull(message = "Washer is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "washer_id", nullable = false)
    private WasherEntity washer;

    @NotNull(message = "Rating is required")
    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 5, message = "Rating must not exceed 5")
    @Column(nullable = false)
    private Integer rating;

    @NotBlank(message = "Comment is required")
    @Column(nullable = false, length = 1000)
    private String comment;

    @NotNull(message = "Review date is required")
    @Column(name = "review_date", nullable = false)
    private LocalDateTime reviewDate;
}
