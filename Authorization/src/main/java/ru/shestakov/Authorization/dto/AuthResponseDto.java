package ru.shestakov.Authorization.dto;

import lombok.Data;


@Data
public class AuthResponseDto {
    private String tokenType = "Bearer ";
    private String accessToken;
    public AuthResponseDto(String accessToken){
        this.accessToken = accessToken;
    }
}
