package com.stackly.healthcare.controller;

import com.stackly.healthcare.request.LoginRequest;
import com.stackly.healthcare.response.LoginResponse;
import com.stackly.healthcare.security.JwtUtil;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;

    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @RequestBody LoginRequest request) {

        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.getUsername(),
                                request.getPassword()
                        )
                );

        UserDetails userDetails =
                (UserDetails) authentication.getPrincipal();

        String token =
                jwtUtil.generateToken(userDetails);

        String role =
                userDetails.getAuthorities()
                        .iterator()
                        .next()
                        .getAuthority();

        LoginResponse response =
                new LoginResponse(
                        token,
                        userDetails.getUsername(),
                        role
                );

        return ResponseEntity.ok(response);
    }
}