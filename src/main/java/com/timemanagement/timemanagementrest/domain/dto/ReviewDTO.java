package com.timemanagement.timemanagementrest.domain.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ReviewDTO {

    @NotNull
    private String message;
}
