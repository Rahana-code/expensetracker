package org.example.expense.repository;

import org.example.expense.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    @Query(value = "SELECT * FROM expenses ORDER BY expense_id LIMIT :size OFFSET :offset", nativeQuery = true)
    List<Expense> findExpensesWithOffsetPagination(
            @Param("size") int size,
            @Param("offset") int offset
    );
}