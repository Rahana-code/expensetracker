package org.example.expense.config;

import lombok.RequiredArgsConstructor;
import org.example.expense.security.JWTFilter;

import org.example.expense.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;
    private final JWTFilter jwtFilter;

    //  Password Encoder (IMPORTANT for DB users)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //  Required for authenticationManager in AuthController
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                // disable CSRF (needed for Postman + JWT)
                .csrf(csrf -> csrf.disable())

                // Stateless session (VERY IMPORTANT for JWT)
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                // H2 console fix
                .headers(headers -> headers.frameOptions(frame -> frame.disable()))

                // URL security rules
                .authorizeHttpRequests(auth -> auth

                        // PUBLIC endpoints
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers("/h2-console/**").permitAll()

                        // ROLE BASED ACCESS
                        .requestMatchers("/api/expenses/delete/**").hasRole("ADMIN")
                        .requestMatchers("/api/expenses/**").hasAnyRole("USER", "ADMIN")

                        // everything else
                        .anyRequest().authenticated()
                )

                //  JWT filter added BEFORE authentication
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)

                // optional (basic auth disabled in JWT systems, but harmless)
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }
}