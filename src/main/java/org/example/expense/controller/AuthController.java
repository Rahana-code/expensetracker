package org.example.expense.controller;

import lombok.RequiredArgsConstructor;
import org.example.expense.dto.AuthRequestDTO;
import org.example.expense.security.JWTService;
import org.example.expense.service.CustomUserDetailsService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService userDetailsService;
    private final JWTService jwtService;

    @PostMapping("/login")
    public String login(@RequestBody AuthRequestDTO request) {
        System.out.println("LOGIN API HIT");
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        UserDetails userDetails =
                userDetailsService.loadUserByUsername(request.getUsername());

        return jwtService.generateToken(userDetails.getUsername());
    }
}
