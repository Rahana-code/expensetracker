package org.example.expense.service;


import org.example.expense.dto.*;

import java.util.List;

public interface ExpenseService {

    ExpenseResponseDTO createExpense(
            ExpenseRequestDTO requestDTO);

    ExpenseResponseDTO getExpenseById(Long id);

    List<ExpenseResponseDTO> getAllExpenses();

    ExpenseResponseDTO updateExpense(
            Long id,
            ExpenseRequestDTO requestDTO);

    void deleteExpense(Long id);
}

