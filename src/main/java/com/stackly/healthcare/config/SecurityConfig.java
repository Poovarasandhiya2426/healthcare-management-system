package com.stackly.healthcare.config;

import com.stackly.healthcare.security.JwtAuthFilter;
import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())

                .cors(cors -> {})

                .sessionManagement(session ->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        )
                )

                .authorizeHttpRequests(auth -> auth

                        // Login API
                        .requestMatchers(
                                "/api/auth/**"
                        ).permitAll()

                        // CORS preflight
                        .requestMatchers(
                                HttpMethod.OPTIONS,
                                "/**"
                        ).permitAll()

                        // Admin APIs
                        .requestMatchers(
                                "/api/admin/**"
                        ).hasRole("ADMIN")

                        // Patient Management APIs
                        .requestMatchers(
                                "/api/patients/**"
                        ).hasRole("ADMIN")

                        // Doctor Management APIs
                        .requestMatchers(
                                "/api/doctors/**"
                        ).hasRole("ADMIN")

                        // Appointment Management APIs
                        .requestMatchers(
                                "/api/appointments/**"
                        ).hasRole("ADMIN")

                        // Prescriptions Management APIs
                        .requestMatchers(
                                "/api/prescriptions/**"
                        ).hasRole("ADMIN")

                        // medical-records Management APIs
                        .requestMatchers(
                                "/api/medical-records/**"
                        ).hasRole("ADMIN")

                        // billings Management APIs
                        .requestMatchers(
                                "/api/billings/**"
                        ).hasRole("ADMIN")


                        // All other APIs require JWT
                        .anyRequest().authenticated()
                )

                .addFilterBefore(
                        jwtAuthFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration)
            throws Exception {

        return configuration.getAuthenticationManager();
    }
}