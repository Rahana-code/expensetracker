package org.example.expense.service;

import org.example.expense.dto.*;
import org.example.expense.entity.Expense;
import org.example.expense.exception.ResourceNotFoundException;
import org.example.expense.repository.ExpenseRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpenseServiceImpl
        implements ExpenseService {

    private final ExpenseRepository repository;

    @Override
    @CachePut(value="expenses",
            key="#result.expenseId")
    public ExpenseResponseDTO createExpense(
            ExpenseRequestDTO dto) {

        Expense expense = Expense.builder()
                .title(dto.getTitle())
                .amount(dto.getAmount())
                .category(dto.getCategory())
                .expenseDate(LocalDate.now())
                .build();

        Expense saved = repository.save(expense);

        return mapToDTO(saved);
    }

    @Override
    @Cacheable(value="expenses",
            key="#id")
    public ExpenseResponseDTO getExpenseById(
            Long id) {

        Expense expense =
                repository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Expense not found with id " + id));

        return mapToDTO(expense);
    }

    @Override
    public List<ExpenseResponseDTO> getAllExpenses() {

        return repository.findAll()
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    @CachePut(value="expenses",
            key="#id")
    public ExpenseResponseDTO updateExpense(
            Long id,
            ExpenseRequestDTO dto) {

        Expense expense =
                repository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Expense not found with id " + id));

        expense.setTitle(dto.getTitle());
        expense.setAmount(dto.getAmount());
        expense.setCategory(dto.getCategory());

        Expense updated =
                repository.save(expense);

        return mapToDTO(updated);
    }

    @Override
    @CacheEvict(value="expenses",
            key="#id")
    public void deleteExpense(Long id) {

        Expense expense =
                repository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Expense not found with id " + id));

        repository.delete(expense);
    }

    private ExpenseResponseDTO mapToDTO(
            Expense expense) {

        return ExpenseResponseDTO.builder()
                .expenseId(expense.getExpenseId())
                .title(expense.getTitle())
                .amount(expense.getAmount())
                .category(expense.getCategory())
                .build();
    }
}