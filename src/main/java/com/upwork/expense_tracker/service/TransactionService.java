package com.upwork.expense_tracker.service;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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

    public Object createTransaction(Transaction transaction, String token) {

        HashMap<String, String> map = new LinkedHashMap<>();

        if (token == null) {
            map.put("error_code", Messages.EMPTY_TOKEN);
            map.put("error_description", Messages.EMPTY_TOKEN_MESSAGE);
            return map;
        }

        if (!tokenUtility.validateToken(token)) {
            map.put("error_code", Messages.INVALID_TOKEN);
            map.put("error_description", Messages.INVALID_TOKEN_MESSAGE);
            return map;
        }
        String userName = tokenUtility.extractUsername(token);

        Map<String, String> messages = inputsChecking.checkCreateTransaction(transaction, Messages.CREATE);
        if (!messages.isEmpty()) {
            return messages;
        }
        Integer userId = userRepository.findIdByEmail(userName);
        transaction.setUserId(userId);
        transaction.setDate(new Date());
        transactionRepository.save(transaction);
        return Arrays.asList(Messages.SUCCESS);
    }

    public Object updateTransaction(Transaction transaction, String token) {

        HashMap<String, String> map = new LinkedHashMap<>();

        Optional<Transaction> optionalTransaction;
        if (token == null) {
            map.put("error_code", Messages.EMPTY_TOKEN);
            map.put("error_description", Messages.EMPTY_TOKEN_MESSAGE);
            return map;
        }

        if (!tokenUtility.validateToken(token)) {
            map.put("error_code", Messages.INVALID_TOKEN);
            map.put("error_description", Messages.INVALID_TOKEN_MESSAGE);
            return map;
        }

        Map<String, String> messages = inputsChecking.checkCreateTransaction(transaction, Messages.UPDATE);
        if (!messages.isEmpty()) {
            return messages;
        }
        String userName = tokenUtility.extractUsername(token);

        optionalTransaction = transactionRepository.findByTransactionIdUserId(transaction.getId(),
                userRepository.findIdByEmail(userName));

        if (!optionalTransaction.isPresent()) {
            map.put("error_code", Messages.INVALID_TRANSACTION);
            map.put("error_description", Messages.INVALID_TRANSACTION_MESSAGE);
            return map;
        }
        Transaction getTransaction = optionalTransaction.get();
        getTransaction.setType(transaction.getType());
        getTransaction.setTag(transaction.getTag());
        getTransaction.setDescription(transaction.getDescription());

        transactionRepository.save(getTransaction);
        return Arrays.asList(Messages.SUCCESS);
    }

    public Object deleteTransaction(Integer id, String token) {

        HashMap<String, String> map = new LinkedHashMap<>();

        if (token == null) {
            map.put("error_code", Messages.EMPTY_TOKEN);
            map.put("error_description", Messages.EMPTY_TOKEN_MESSAGE);
            return map;
        }

        if (!tokenUtility.validateToken(token)) {
            map.put("error_code", Messages.INVALID_TOKEN);
            map.put("error_description", Messages.INVALID_TOKEN_MESSAGE);
            return map;
        }

        if (id == null) {
            map.put("error_code", Messages.EMPTY_TRANSACTION_ID);
            map.put("error_description", Messages.EMPTY_TRANSACTION_ID_MESSAGE);
            return map;
        }

        transactionRepository.deleteById(id);
        return Arrays.asList(Messages.DELETED);
    }

    public Object getTransactions(String token) {

        HashMap<String, String> map = new LinkedHashMap<>();

        if (token == null) {
            map.put("error_code", Messages.EMPTY_TOKEN);
            map.put("error_description", Messages.EMPTY_TOKEN_MESSAGE);
            return map;
        }

        if (!tokenUtility.validateToken(token)) {
            map.put("error_code", Messages.INVALID_TOKEN);
            map.put("error_description", Messages.INVALID_TOKEN_MESSAGE);
            return map;
        }
        String userName = tokenUtility.extractUsername(token);

        Integer userId = userRepository.findIdByEmail(userName);

        return transactionRepository.findByUserId(userId);
    }
}
