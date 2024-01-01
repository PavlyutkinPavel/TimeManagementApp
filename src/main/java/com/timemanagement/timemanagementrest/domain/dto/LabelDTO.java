package com.timemanagement.timemanagementrest.domain.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LabelDTO {

    @NotNull
    private String category;

    @NotNull
    private String style;
}