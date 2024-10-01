package ru.shestakov.Authorization.security;


import io.jsonwebtoken.Claims;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;


import java.util.Date;

@Component
public class JWTGenerator {
    @Autowired
    private final   SecurityConstants securityConstants;
    @Autowired
    public JWTGenerator(SecurityConstants securityConstants) {
        this.securityConstants = securityConstants;
    }

    public String generateToken(Authentication auth){
        String username = auth.getName();
        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime() + securityConstants.jwtExpired);

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

    public boolean validateTokenBySignature(String token){
        try{
            Jwts.parserBuilder().setSigningKey(securityConstants.getSecret()).build().parseClaimsJws(token);
            return  true;
        }catch (Exception e){
            throw  new AuthenticationCredentialsNotFoundException("jwt signature is not valid ");
        }
    }
    public boolean isExpired(String token){
        Claims claims = Jwts.parserBuilder().setSigningKey(securityConstants.getSecret()).build().parseClaimsJws(token).getBody();
        return claims.getExpiration().before(new Date());
    }

    public boolean validateToken(String token){
        return validateTokenBySignature(token) && !isExpired(token);
    }
}
