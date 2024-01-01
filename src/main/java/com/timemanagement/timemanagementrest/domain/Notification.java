package com.timemanagement.timemanagementrest.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Entity(name = "notifications")
@Data
@Component
public class Notification {
    @Id
    @SequenceGenerator(name = "notificationsSeqGen", sequenceName = "notifications_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "notificationsSeqGen")
    private Long id;

    @NotNull
    @Column(name = "user_id")
    private Long userId;

    @NotNull
    @Column(name = "notifications_message")
    private String message;

    @Column(name = "created_at")
    private LocalDateTime created_at;
}