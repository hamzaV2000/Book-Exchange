package com.example.demo.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static com.example.demo.DemoApplication.randomGlobal;

@Component
public class JwtUtil {


    private final String SECRET_KEY = randomGlobal + "secret" + randomGlobal;

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    public Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    public Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public Map<String, String> generateTokens(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", userDetails.getAuthorities());
        return createToken(claims, userDetails);
    }

    private Map<String, String> createToken(Map<String, Object> claims, UserDetails subject) {
        System.out.println(claims);
        Map<String, String> tokens = new HashMap<>();
        String access_token =
                Jwts.builder()
                        .setClaims(claims)
                        .setSubject(subject.getUsername()).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 2))// 2 hours
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();

        String refresh_token = Jwts.builder().setClaims(null).setSubject(subject.getUsername()).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))// 24 hours
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
        tokens.put("access_token" , access_token);
        tokens.put("refresh_token", refresh_token);

        return tokens;

    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        Collection list = extractAllClaims(token).get("roles", Collection.class);

        if(list == null)
            return false;

        userDetails.getAuthorities().stream().forEach(grantedAuthority -> {
            list.contains(grantedAuthority);
        });

        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token) );
    }
    public Boolean validateRefreshToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        Collection list = extractAllClaims(token).get("roles", Collection.class);

        if(list != null)
            return false;
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
