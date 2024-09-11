package ru.shestakov.Library.dto;

import lombok.Data;
import ru.shestakov.Library.security.SecurityConstants;

import java.text.SimpleDateFormat;
import java.util.Date;

@Data
public class AuthResponseDto {
    private String tokenType = "Bearer ";
    private String accessToken;
    public AuthResponseDto(String accessToken){
        this.accessToken = accessToken;
    }
}
