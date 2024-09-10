package ru.shestakov.Library.security;

import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTGenerator {
    public String generateToken(Authentication auth){
        String username = auth.getName();
        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime() + SecurityConstants.JWT_EXPIRATIONS);

        String token = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512,SecurityConstants.SECRET)
                .compact();
        return token;
    }
    public String getUsernameFromJWT(String token){
        Claims claims = Jwts.parserBuilder().setSigningKey(SecurityConstants.SECRET)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    public boolean validateToken(String token){
        try{
            Jwts.parserBuilder().setSigningKey(SecurityConstants.SECRET).build().parseClaimsJws(token);
            return  true;
        }catch (Exception e){
            throw  new AuthenticationCredentialsNotFoundException("jwt expired");
        }
    }
}
