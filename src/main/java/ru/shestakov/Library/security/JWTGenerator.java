package ru.shestakov.Library.security;


import io.jsonwebtoken.Claims;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;


import java.util.Date;

@Component
public class JWTGenerator {
private final    SecurityConstants securityConstants;
@Autowired
    public JWTGenerator(SecurityConstants securityConstants) {
        this.securityConstants = securityConstants;
    }

    public String generateToken(Authentication auth){
        String username = auth.getName();
        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime() + securityConstants.getJwtExpired());

        String token = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512,securityConstants.getSecret())
                .compact();
        return token;
    }
    public String getUsernameFromJWT(String token){
        Claims claims = Jwts.parserBuilder().setSigningKey(securityConstants.getSecret())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    public boolean validateToken(String token){
        try{
            Jwts.parserBuilder().setSigningKey(securityConstants.getSecret()).build().parseClaimsJws(token);
            return  true;
        }catch (Exception e){
            throw  new AuthenticationCredentialsNotFoundException("jwt expired");
        }
    }
}
