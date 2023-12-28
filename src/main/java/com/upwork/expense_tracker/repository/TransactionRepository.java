package com.upwork.expense_tracker.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.upwork.expense_tracker.entity.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

    @Query("SELECT t FROM Transaction t WHERE t.user_id = :userId")
    List<Transaction> findByUserId(@Param("userId") int userId);
}
