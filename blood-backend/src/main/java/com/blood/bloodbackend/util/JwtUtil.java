package com.blood.bloodbackend.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;

@Service
public class JwtUtil {
    private String SECRET_KEY = "12345";

    public String extractUsername (String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public<T> T extractClaim (String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    private Claims extractAllClaims (String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired (String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken (UserDetails userDetailsService) {
        Map<String, Object> claims = new HashMap<>();
        List<String> papeis = new ArrayList<>();
        userDetailsService.getAuthorities().forEach(x -> {papeis.add(x.getAuthority());});
        claims.put("roles", papeis);
        return createToken(claims, userDetailsService.getUsername());
    }

    private String createToken (Map<String, Object> claims, String subject) {
        System.out.println("CRIANDO O TOKEN");
        System.out.println(claims.get("roles").toString());
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)).signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
