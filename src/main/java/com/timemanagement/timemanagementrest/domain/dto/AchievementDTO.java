package com.timemanagement.timemanagementrest.domain.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AchievementDTO {

    @NotNull
    private String name;

    @NotNull
    private String description;

    @NotNull
    private String styles;
}
