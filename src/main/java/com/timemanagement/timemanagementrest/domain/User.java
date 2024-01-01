package com.timemanagement.timemanagementrest.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Schema(description = "Описание пользователя")
@Data
@Entity(name = "users")
public class User {

    @Schema(description = "Это уникальный идентификатор пользователя")
    @Id
    @SequenceGenerator(name = "userSeqGen", sequenceName = "users_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "userSeqGen")
    private Long id;

    @Column(name = "first_name")
    @NotNull
    private String firstName;

    @Column(name = "last_name")
    @NotNull
    private String lastName;

    @Column(name = "created")
    private LocalDateTime createdAt;

    @Column(name = "email")
    @NotNull
    private String email;

    @Column(name = "average_progress")
    private Double average_progress;

}

