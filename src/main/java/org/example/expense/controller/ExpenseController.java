package org.example.expense.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.expense.dto.*;
import org.example.expense.service.ExpenseService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/expenses")
@RequiredArgsConstructor
public class ExpenseController {

    private final ExpenseService service;

    @PostMapping
    public ResponseEntity<ExpenseResponseDTO> createExpense(
            @Valid @RequestBody ExpenseRequestDTO dto) {

        return new ResponseEntity<>(
                service.createExpense(dto),
                HttpStatus.CREATED);
    }
@GetMapping("/health")
public String getHealth(){
        return "Healthy";
}

    @GetMapping("/{id}")
    public ResponseEntity<ExpenseResponseDTO> getExpenseById(@PathVariable Long id) {

        return ResponseEntity.ok(service.getExpenseById(id));
    }

    @GetMapping
    public ResponseEntity<List<ExpenseResponseDTO>> getAllExpenses() {

        return ResponseEntity.ok(service.getAllExpenses());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExpenseResponseDTO> updateExpense(
            @PathVariable Long id,
            @Valid @RequestBody ExpenseRequestDTO dto) {

        return ResponseEntity.ok(service.updateExpense(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteExpense(@PathVariable Long id) {

        service.deleteExpense(id);
        return ResponseEntity.ok("Expense deleted successfully");
    }

    //  OFFSET PAGINATION ENDPOINT
    @GetMapping("/offset")
    public ResponseEntity<List<ExpenseResponseDTO>> getPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        return ResponseEntity.ok(
                service.getExpensesWithOffset(page, size));
    }

}