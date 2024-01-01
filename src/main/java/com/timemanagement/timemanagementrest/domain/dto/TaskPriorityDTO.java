package com.timemanagement.timemanagementrest.domain.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TaskPriorityDTO {

    @NotNull
    private Long id;

    @NotNull
    private String myLogin;

    @NotNull
    private String category;
}
