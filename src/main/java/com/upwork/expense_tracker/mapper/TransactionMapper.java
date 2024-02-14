package com.upwork.expense_tracker.mapper;

import com.upwork.expense_tracker.dto.TransactionCreatingRequest;
import com.upwork.expense_tracker.entity.Transaction;
import org.springframework.stereotype.Service;

@Service
public class TransactionMapper {

    public Transaction toTransaction(TransactionCreatingRequest dto) {
        return Transaction.builder()
                .type(dto.getType())
                .description(dto.getDescription())
                .build();
    }

}
