package org.example.expense.service;

import lombok.RequiredArgsConstructor;
import org.example.expense.dto.*;
import org.example.expense.entity.Expense;
import org.example.expense.exception.ResourceNotFoundException;
import org.example.expense.repository.ExpenseRepository;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpenseService {

    private final ExpenseRepository repository;

    public ExpenseResponseDTO createExpense(ExpenseRequestDTO dto) {

        Expense expense = Expense.builder()
                .title(dto.getTitle())
                .amount(dto.getAmount())
                .category(dto.getCategory())
                .expenseDate(dto.getExpenseDate())
                .build();

        Expense saved = repository.save(expense);

        return mapToDTO(saved);
    }

    @Cacheable(value = "expenses")
    public List<ExpenseResponseDTO> getAllExpenses() {

        return repository.findAll()
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    public ExpenseResponseDTO getExpenseById(Long id) {

        Expense expense = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Expense not found with id " + id));

        return mapToDTO(expense);
    }

    @CachePut(value = "expenses", key = "#id")
    public ExpenseResponseDTO updateExpense(Long id, ExpenseRequestDTO dto) {

        Expense expense = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Expense not found with id " + id));

        expense.setTitle(dto.getTitle());
        expense.setAmount(dto.getAmount());
        expense.setCategory(dto.getCategory());
        expense.setExpenseDate(dto.getExpenseDate());

        return mapToDTO(repository.save(expense));
    }

    @CacheEvict(value = "expenses", key = "#id")
    public void deleteExpense(Long id) {

        Expense expense = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Expense not found with id " + id));

        repository.delete(expense);
    }

    public List<ExpenseResponseDTO> getExpensesWithOffset(int page, int size) {

        int offset = page * size;

        return repository.findExpensesWithOffsetPagination(size, offset)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    private ExpenseResponseDTO mapToDTO(Expense expense) {

        return ExpenseResponseDTO.builder()
                .expenseId(expense.getExpenseId())
                .title(expense.getTitle())
                .amount(expense.getAmount())
                .category(expense.getCategory())
                .expenseDate(expense.getExpenseDate())
                .build();
    }
}