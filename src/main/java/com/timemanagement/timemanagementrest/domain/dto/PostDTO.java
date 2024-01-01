package com.timemanagement.timemanagementrest.domain.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class PostDTO {

    @NotNull
    private String title;

    @NotNull
    private String content;

}
