package com.timemanagement.timemanagementrest.security.domain;

import lombok.Data;

@Data
public class RegistrationDTO {

    private String firstName;

    private String lastName;

    private String userLogin;

    private String userPassword;

    private String email;

    private Double average_progress;
}
