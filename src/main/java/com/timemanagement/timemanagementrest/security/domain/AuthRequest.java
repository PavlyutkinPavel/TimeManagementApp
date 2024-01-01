package com.timemanagement.timemanagementrest.security.domain;

import lombok.Data;

@Data
public class AuthRequest {
    private String login;
    private String password;
}