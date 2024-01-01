package com.timemanagement.timemanagementrest.domain.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TaskProgressDTO {

    @NotNull
    private Long id;

    @NotNull
    private Integer progress;
}
