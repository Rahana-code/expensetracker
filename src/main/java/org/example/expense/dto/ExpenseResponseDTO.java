package org.example.expense.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExpenseResponseDTO {

    private Long expenseId;
    private String title;
    private Double amount;
    private String category;
}
