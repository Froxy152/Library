package ru.shestakov.Auth.dto;

import lombok.Data;

@Data
public class UserRequestLoginDto {
    private String username;
    private String password;
}
