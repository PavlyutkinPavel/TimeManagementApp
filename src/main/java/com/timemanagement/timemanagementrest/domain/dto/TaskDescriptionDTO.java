package com.timemanagement.timemanagementrest.domain.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TaskDescriptionDTO {

    @NotNull
    private Long id;

    @NotNull
    private String myLogin;

    @NotNull
    private String newDescription;
}
