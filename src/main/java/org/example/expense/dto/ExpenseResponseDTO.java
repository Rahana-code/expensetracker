package org.example.expense.dto;

import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExpenseResponseDTO {

    private Long expenseId;
    private String title;
    private Double amount;
    private String category;
    private LocalDate expenseDate;
}
