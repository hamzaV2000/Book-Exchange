package com.example.controller;


import com.example.demo.exception_handling.MyException;
import com.example.demo.jwt.JwtUtil;
import com.example.entity.UserToken;
import com.example.services.UserService;
import com.example.services.UserTokenService;
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

    private final UserTokenService userTokenService;

    public AuthController(AuthenticationManager authenticationManager, UserDetailsService userDetailsService, JwtUtil jwtUtil, UserTokenService userTokenService) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
        this.userTokenService = userTokenService;
    }

    @PostMapping("/auth")
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequest request){
        if(SecurityContextHolder.getContext().getAuthentication() != null)
            return ResponseEntity.ok("already logged in");
        try{
            System.out.println("here");
            final UserDetails user
                    = userDetailsService.loadUserByUsername(request.getEmail());

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken
                            (request.getEmail(), request.getPassword())
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            if(user != null){
                Map<String, String> tokens = jwtUtil.generateTokens(user);

                UserToken token = userTokenService.findUserTokenByUserName(request.getEmail());
                if(token == null){
                    userTokenService.save(new UserToken(request.getEmail(), tokens.get("access_token"), tokens.get("refresh_token")));
                }
                else{
                    token.setAccess_token(tokens.get("access_token"));
                    token.setRefresh_token(tokens.get("refresh_token"));

                    userTokenService.save(token);
                }
                return ResponseEntity.ok(tokens);
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
                    UserToken token = userTokenService.findUserTokenByUserName(userEmail);

                    tokens.put("refresh_token", jwtToken);

                    token.setAccess_token(tokens.get("access_token"));
                    token.setRefresh_token(tokens.get("refresh_token"));

                    userTokenService.save(token);
                    return ResponseEntity.ok(tokens);
                }
            }
        }
        return ResponseEntity.badRequest().body("Token is needed");
    }

}


