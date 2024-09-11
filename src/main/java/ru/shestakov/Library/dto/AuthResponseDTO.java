package ru.shestakov.Library.dto;

import lombok.Data;
import ru.shestakov.Library.security.SecurityConstants;

import java.text.SimpleDateFormat;
import java.util.Date;

@Data
public class AuthResponseDTO {
    private String tokenType = "Bearer ";
    private String accessToken;
    private String expireTime = new SimpleDateFormat("HH:mm:ss").format(new Date(System.currentTimeMillis() + SecurityConstants.JWT_EXPIRATIONS));

    public AuthResponseDTO(String accessToken){
        this.accessToken = accessToken;
    }
}
