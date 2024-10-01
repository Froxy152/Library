package ru.shestakov.book.security;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("constants")
public class SecurityConstants {
    public  String secret;
}
