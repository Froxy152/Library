package ru.shestakov.book.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Date;

public class JWTAuthFilter  extends OncePerRequestFilter {


    @Autowired
    private SecurityConstants securityConstants;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token =getJWTFromRequest(request);
        if(StringUtils.hasText(token) && validateToken(token)) {
            String username = getUsernameFromJWT(token);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,null,null);
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request)); 
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        filterChain.doFilter(request,response);
    }
    private String getJWTFromRequest(HttpServletRequest request){
        String bearerToken = request.getHeader("Authorization");
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")){
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
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
