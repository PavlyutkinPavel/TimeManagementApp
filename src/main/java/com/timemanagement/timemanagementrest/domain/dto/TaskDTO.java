package com.timemanagement.timemanagementrest.domain.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TaskDTO {

    @NotNull
    private String title;

    @NotNull
    private String description;

    @NotNull
    private LocalDateTime dueDate;

    @NotNull
    private String category;
}
