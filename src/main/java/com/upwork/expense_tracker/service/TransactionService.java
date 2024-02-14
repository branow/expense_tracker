package com.upwork.expense_tracker.service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.upwork.expense_tracker.dto.TransactionCreatingRequest;
import com.upwork.expense_tracker.dto.TransactionUpdatingRequest;
import com.upwork.expense_tracker.exception.EntityNotFoundException;
import com.upwork.expense_tracker.mapper.TransactionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.upwork.expense_tracker.constant.Messages;
import com.upwork.expense_tracker.entity.Transaction;
import com.upwork.expense_tracker.repository.TransactionRepository;
import com.upwork.expense_tracker.repository.UserRepository;

@Service
public class TransactionService {

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TokenUtility tokenUtility;

    @Autowired
    TransactionMapper mapper;

    public List<String> createTransaction(TransactionCreatingRequest dto, String token) {
        if (token == null) {
            return Arrays.asList(Messages.EMPTY_TOKEN);
        }

        if (!tokenUtility.validateToken(token)) {
            return Arrays.asList(Messages.INVALID_TOKEN);
        }
        String userName = tokenUtility.extractUsername(token);

        Integer userId = userRepository.findIdByEmail(userName);

        Transaction transaction = mapper.toTransaction(dto);
        transaction.setUserId(userId);
        transaction.setDate(new Date());
        transactionRepository.save(transaction);
        return Arrays.asList(Messages.SUCCESS);
    }

    public List<String> updateTransaction(TransactionUpdatingRequest transaction, String token) {

        Optional<Transaction> optionalTransaction;
        if (token == null) {
            return Arrays.asList(Messages.EMPTY_TOKEN);
        }

        if (!tokenUtility.validateToken(token)) {
            return Arrays.asList(Messages.INVALID_TOKEN);
        }

        String userName = tokenUtility.extractUsername(token);

        optionalTransaction = transactionRepository.findByTransactionIdUserId(transaction.getId(), userRepository.findIdByEmail(userName));

        Transaction getTransaction = optionalTransaction.orElseThrow(() ->
                new EntityNotFoundException(Transaction.class, "id", transaction.getId()));

        getTransaction.setType(transaction.getType());
        getTransaction.setDescription(transaction.getDescription());

        transactionRepository.save(getTransaction);
        return Arrays.asList(Messages.SUCCESS);
    }

    public List<String> deleteTransaction(Integer id, String token) {

        if (token == null) {
            return Arrays.asList(Messages.EMPTY_TOKEN);
        }

        if (!tokenUtility.validateToken(token)) {
            return Arrays.asList(Messages.INVALID_TOKEN);
        }

        if (id == null) {
            return Arrays.asList(Messages.EMPTY_TRANSACTION_ID);
        }

        transactionRepository.deleteById(id);
        return Arrays.asList(Messages.DELETED);
    }

    public Object getTransactions(String token) {

        if (token == null) {
            return Arrays.asList(Messages.EMPTY_TOKEN);
        }

        if (!tokenUtility.validateToken(token)) {
            return Arrays.asList(Messages.INVALID_TOKEN);
        }
        String userName = tokenUtility.extractUsername(token);

        Integer userId = userRepository.findIdByEmail(userName);

        return transactionRepository.findByUserId(userId);
    }
}
