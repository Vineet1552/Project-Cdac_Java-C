package com.cdac.entities;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "notifications")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NotificationEntity extends BaseEntity {

    @NotNull(message = "User is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @NotBlank(message = "Message is required")
    @Column(nullable = false)
    private String message;

    @NotBlank(message = "Notification type is required")
    @Column(nullable = false)
    private String type; // e.g., "INFO", "ALERT", "REMINDER"

    @NotBlank(message = "Status is required")
    @Column(nullable = false)
    private String status; // e.g., "UNREAD", "READ"

    @NotNull(message = "Creation time is required")
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
}
