package ru.shestakov.Library.security;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
@Data
@ConfigurationProperties("constants")
public class SecurityConstants {

    public  long jwtExpired;
    public  String secret;

}
