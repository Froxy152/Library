package ru.shestakov.Authorization.dto;

import lombok.Data;

@Data
public class UserRequestLoginDto {
    private String username;
    private String password;
}
