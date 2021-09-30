package com.example.user_management.models;

import lombok.Data;

@Data
public class User {

    private Long id;

    private String username;

    private String password;

    private String email;

    private Gender gender;

    private String firstName;

    private String lastName;
}
