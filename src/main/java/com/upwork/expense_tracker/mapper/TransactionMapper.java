package com.upwork.expense_tracker.mapper;

import com.upwork.expense_tracker.dto.TransactionCreatingRequest;
import com.upwork.expense_tracker.dto.TransactionResponse;
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

    public TransactionResponse toTransactionResponse(Transaction entity) {
        return TransactionResponse.builder()
                .id(entity.getId())
                .type(entity.getType())
                .description(entity.getDescription())
                .date(entity.getDate())
                .userId(entity.getUserId())
                .build();
    }

}
