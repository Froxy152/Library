package ru.shestakov.Authorization.dto;

import lombok.Data;

@Data
public class UserResponseRegistrationDto {
    private String username;
    private String password;
}
