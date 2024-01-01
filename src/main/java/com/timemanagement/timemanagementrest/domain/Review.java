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

@Entity(name = "reviews")
@Data
@Component
public class Review {
    @Id
    @SequenceGenerator(name = "reviewsSeqGen", sequenceName = "reviews_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "reviewsSeqGen")
    private Long id;

    @NotNull
    @Column(name = "reviews_description")
    private String message;

    @Column(name = "created_at")
    private LocalDateTime created_at;

    @NotNull
    @Column(name = "user_id")
    private Long userId;
}