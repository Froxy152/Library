package ru.shestakov.Library.dto;

import lombok.Data;

@Data
public class UserRequestRegistrationDto {
    private String username;
    private String password;
}
