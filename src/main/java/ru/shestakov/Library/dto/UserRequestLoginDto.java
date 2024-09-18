package ru.shestakov.Library.dto;

import lombok.Data;

@Data
public class UserRequestLoginDto {
    private String username;
    private String password;
}
