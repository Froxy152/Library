package ru.shestakov.Authorization.dto;

import lombok.Data;

@Data
public class UserRequestRegistrationDto {
    private String username;
    private String password;
}
