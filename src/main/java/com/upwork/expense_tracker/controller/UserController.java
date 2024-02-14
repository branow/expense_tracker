package com.upwork.expense_tracker.controller;

import com.upwork.expense_tracker.dto.*;
import com.upwork.expense_tracker.service.TransactionService;
import com.upwork.expense_tracker.service.UserService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@OpenAPIDefinition(info = @Info(title = "User Controller", description = "This controller consist of 8 operations: createUser, loginUser, createTransaction, getTransactions, updateTransaction, deleteTransaction, signout, updateProfile"))
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    TransactionService transactionService;

    @Operation(summary = "Create User")
    @PostMapping("/createUser")
    public Object createUser(@RequestBody @Valid UserCreatingRequest user) {
        return userService.createUser(user);
    }

    @Operation(summary = "Login User")
    @PostMapping("/loginUser")
    public Object loginUser(@RequestBody @Valid UserLoginRequest user) {
        return userService.loginUser(user);
    }

    @Operation(summary = "Create Transaction")
    @PostMapping("/createTransaction")
    public List<String> createTransaction(HttpServletRequest request,
                                          @RequestBody @Valid TransactionCreatingRequest transaction) {
        return transactionService.createTransaction(transaction, request.getHeader("authorization"));
    }

    @Operation(summary = "Get Transactions")
    @GetMapping("/getTransactions")
    public Object getTransactions(HttpServletRequest request) {
        return transactionService.getTransactions(request.getHeader("authorization"));
    }

    @Operation(summary = "Update Transaction")
    @PutMapping("/updateTransaction")
    public List<String> updateTransaction(HttpServletRequest request,
                                          @RequestBody @Valid TransactionUpdatingRequest transaction) {
        return transactionService.updateTransaction(transaction, request.getHeader("authorization"));
    }

    @Operation(summary = "Delete Transaction")
    @DeleteMapping("/deleteTransaction")
    public List<String> deleteTransaction(HttpServletRequest request,
                                          @RequestParam Integer transaction_id) {
        return transactionService.deleteTransaction(transaction_id, request.getHeader("authorization"));
    }

    @Operation(summary = "Signout")
    @GetMapping("/signout")
    public List<String> signout(HttpServletRequest request) {
        return userService.signout(request.getHeader("authorization"));
    }

    @Operation(summary = "Update Profile")
    @PutMapping("/updateProfile")
    public List<String> updateProfile(HttpServletRequest request,
                                      @RequestBody @Valid ProfileUpdatingRequest profile) {
        return userService.updateProfile(request.getHeader("authorization"), profile);
    }

    @Operation(summary = "Get User Info")
    @GetMapping("/getUser")
    public Map<String, String> getUser(HttpServletRequest request) {
        return userService.getUser(request.getHeader("authorization"));
    }
}
