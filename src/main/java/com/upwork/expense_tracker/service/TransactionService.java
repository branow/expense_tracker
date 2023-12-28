package com.upwork.expense_tracker.service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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
    InputsChecking inputsChecking;

    @Autowired
    TokenUtility tokenUtility;

    public List<String> createTransaction(Transaction transaction, String token) {

        if (token == null) {
            return Arrays.asList(Messages.EMPTY_TOKEN);
        }

        if (!tokenUtility.validateToken(token)) {
            return Arrays.asList(Messages.INVALID_TOKEN);
        }
        String userName = tokenUtility.extractUsername(token);

        List<String> messages = inputsChecking.checkCreateTransaction(transaction);
        if (!messages.isEmpty()) {
            return messages;
        }

        Integer userId = userRepository.findIdByEmail(userName);
        transaction.setUserId(userId);
        transaction.setDate(new Date());
        transactionRepository.save(transaction);
        return Arrays.asList(Messages.SUCCESS);
    }

    public List<String> updateTransaction(Transaction transaction, String token) {

        Optional<Transaction> optionalTransaction;
        if (token == null) {
            return Arrays.asList(Messages.EMPTY_TOKEN);
        }

        if (!tokenUtility.validateToken(token)) {
            return Arrays.asList(Messages.INVALID_TOKEN);
        }

        List<String> messages = inputsChecking.checkCreateTransaction(transaction);
        if (!messages.isEmpty()) {
            return messages;
        }

        optionalTransaction = transactionRepository.findById(transaction.getId());
        if (!optionalTransaction.isPresent()) {
            return Arrays.asList(Messages.INVALID_TRANSACTION);
        }
        Transaction getTransaction = optionalTransaction.get();
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
