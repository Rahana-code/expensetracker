package org.example.expense.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ExpenseRequestDTO {

    @NotBlank
    private String title;

    @NotNull
    @Positive
    private Double amount;

    @NotBlank
    private String category;

    @NotNull
    private LocalDate expenseDate;
}