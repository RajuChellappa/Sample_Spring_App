package com.example.user_management.models;

import lombok.Data;

@Data
public class UserValidationRequest {

    private String email;

    private String password;
}
