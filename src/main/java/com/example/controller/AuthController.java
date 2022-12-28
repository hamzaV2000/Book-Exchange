package com.example.controller;


import com.example.demo.exception_handling.MyException;
import com.example.demo.jwt.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RestController
@RequestMapping("/api/v1")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;

    public AuthController(AuthenticationManager authenticationManager, UserDetailsService userDetailsService, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/auth")
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequest request){
        if(SecurityContextHolder.getContext().getAuthentication() != null)
            return ResponseEntity.ok("already logged in");
        try{
            final UserDetails user
                    = userDetailsService.loadUserByUsername(request.getEmail());

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken
                            (request.getEmail(), request.getPassword())
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            if(user != null){
                return ResponseEntity.ok(jwtUtil.generateTokens(user));
            }
        }catch (Exception e){
            throw  new MyException("Problem with Credentials");
        }


        return null;
    }
    @GetMapping("/refreshToken")
    public ResponseEntity<?> refreshToken(HttpServletRequest request){

        String header = request.getHeader(AUTHORIZATION);
        System.out.println(header);
        if(header != null && header.startsWith("Bearer")){
            String jwtToken = header.substring(7);
            String userEmail = jwtUtil.extractUsername(jwtToken);

            if(userEmail != null){
                UserDetails userDetails
                        = userDetailsService.loadUserByUsername(userEmail);
                final boolean isTokenValid = jwtUtil.validateRefreshToken(jwtToken, userDetails);
                if(isTokenValid){
                    Map<String, String> tokens = jwtUtil.generateTokens(userDetails);
                    tokens.put("refresh_token", jwtToken);
                    return ResponseEntity.ok(tokens);
                }
            }
        }
        return ResponseEntity.badRequest().body("Token is needed");
    }

}


