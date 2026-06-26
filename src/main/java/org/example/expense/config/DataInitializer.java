package org.example.expense.config;

import lombok.RequiredArgsConstructor;
import org.example.expense.entity.AppUser;
import org.example.expense.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {

        if (userRepository.findByUsername("user").isEmpty()) {
            userRepository.save(AppUser.builder()
                    .username("user")
                    .password(passwordEncoder.encode("1234"))
                    .role("USER")
                    .build());
        }

        if (userRepository.findByUsername("admin").isEmpty()) {
            userRepository.save(AppUser.builder()
                    .username("admin")
                    .password(passwordEncoder.encode("admin123"))
                    .role("ADMIN")
                    .build());
        }
    }
}