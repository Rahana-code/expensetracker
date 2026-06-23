package org.example.expense;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class ExpenseApplication {

    public static void main(String[] args) {
        SpringApplication.run(
                ExpenseApplication.class,
                args);
    }
}
