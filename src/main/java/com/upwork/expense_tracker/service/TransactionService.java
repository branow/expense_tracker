package com.upwork.expense_tracker.service;

import com.upwork.expense_tracker.dto.TransactionCreatingRequest;
import com.upwork.expense_tracker.dto.TransactionResponse;
import com.upwork.expense_tracker.dto.TransactionUpdatingRequest;
import com.upwork.expense_tracker.entity.Transaction;
import com.upwork.expense_tracker.exception.EntityNotFoundException;
import com.upwork.expense_tracker.mapper.TransactionMapper;
import com.upwork.expense_tracker.repository.TransactionRepository;
import com.upwork.expense_tracker.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TransactionService {

    TransactionRepository repository;
    UserRepository userRepository;
    TransactionMapper mapper;


    public TransactionResponse create(TransactionCreatingRequest dto, String userEmail) {
        Integer userId = userRepository.findIdByEmail(userEmail);
        Transaction transaction = mapper.toTransaction(dto);
        transaction.setUserId(userId);
        transaction.setDate(new Date());
        return mapper.toTransactionResponse(repository.save(transaction));
    }

    public Transaction get(Integer transactionId) {
        return repository.findById(transactionId)
                .orElseThrow(() -> new EntityNotFoundException(Transaction.class, "id", transactionId));
    }
    public List<TransactionResponse> getAll(String userEmail) {
        Integer userId = userRepository.findIdByEmail(userEmail);
        return repository.findByUserId(userId).stream()
                .map(mapper::toTransactionResponse)
                .toList();
    }

    public TransactionResponse update(TransactionUpdatingRequest transaction) {
        Transaction old = get(transaction.getId());
        old.setType(transaction.getType());
        old.setDescription(transaction.getDescription());
        return mapper.toTransactionResponse(repository.save(old));
    }

    public void delete(Integer id) {
        repository.deleteById(id);
    }

}
