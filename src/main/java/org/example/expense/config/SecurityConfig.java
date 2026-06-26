package org.example.expense.config;

import lombok.RequiredArgsConstructor;
import org.example.expense.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.http.HttpMethod;
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                //  IMPORTANT for Postman + H2
                .csrf(csrf -> csrf.disable())

                //  H2 console fix
                .headers(headers -> headers.frameOptions(frame -> frame.disable()))

                .authorizeHttpRequests(auth -> auth

                        // allow H2 console
                        .requestMatchers("/h2-console/**").permitAll()

                        // role-based rules
                        .requestMatchers("/api/expenses/delete/**").hasRole("ADMIN")
                        .requestMatchers("/api/expenses/**").hasAnyRole("USER", "ADMIN")

                        .anyRequest().authenticated()
                )

                // bASIC AUTH (this is what gives login popup / Postman auth)
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }
}