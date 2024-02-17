package com.upwork.expense_tracker.service;

import com.upwork.expense_tracker.exception.IllegalEntityAccessException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * {@code EntityAccessManager} is the service class that checks user access to operate on a
 * specific entity. The objects of the class should be used in the Controllers or higher
 * layer to avoid circular dependencies.
 * */
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EntityAccessManager {

    private final TransactionService transactionService;
    private final UserService userService;

    public void isAllowedAccessToTransactionOrElseThrow(String userEmail, Integer transactionId) {
        Integer userId = userService.getByEmail(userEmail).getId();
        Integer transactionUserId = transactionService.get(transactionId).getUserId();
        if (!Objects.equals(userId, transactionUserId)) {
            throw new IllegalEntityAccessException("User with email = " + userEmail,
                    "Transaction with id = " + transactionId);
        }
    }

}
