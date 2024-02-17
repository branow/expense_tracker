package com.upwork.expense_tracker.controller;

import com.upwork.expense_tracker.dto.TransactionCreatingRequest;
import com.upwork.expense_tracker.dto.TransactionResponse;
import com.upwork.expense_tracker.dto.TransactionUpdatingRequest;
import com.upwork.expense_tracker.service.EntityAccessManager;
import com.upwork.expense_tracker.service.TransactionService;
import com.upwork.expense_tracker.service.RequestExtractor;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transaction")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TransactionController {

    TransactionService service;
    RequestExtractor extractor;
    EntityAccessManager entityAccessManager;


    @Operation(summary = "Get Transactions")
    @GetMapping("/get-all")
    public ResponseEntity<List<TransactionResponse>> getAll(HttpServletRequest request) {
        String email = extractor.extractJwtSubject(request);
        return ResponseEntity.ok(service.getAll(email));
    }

    @Operation(summary = "Create Transaction")
    @PostMapping("/create")
    public ResponseEntity<TransactionResponse> create(@RequestBody @Valid TransactionCreatingRequest transaction,
                                         HttpServletRequest request) {
        String email = extractor.extractJwtSubject(request);
        return new ResponseEntity<>(service.create(transaction, email), HttpStatus.CREATED);
    }

    @Operation(summary = "Update Transaction")
    @PutMapping("/update")
    public ResponseEntity<TransactionResponse> update(@RequestBody @Valid TransactionUpdatingRequest transaction,
                                         HttpServletRequest request) {
        String email = extractor.extractJwtSubject(request);
        entityAccessManager.isAllowedAccessToTransactionOrElseThrow(email, transaction.getId());
        return ResponseEntity.ok(service.update(transaction));
    }

    @Operation(summary = "Delete Transaction")
    @DeleteMapping("/delete/{transactionId}")
    public ResponseEntity<String> delete(@PathVariable Integer transactionId,
                                         HttpServletRequest request) {
        String email = extractor.extractJwtSubject(request);
        entityAccessManager.isAllowedAccessToTransactionOrElseThrow(email, transactionId);
        service.delete(transactionId);
        return ResponseEntity.ok("Transaction was deleted successfully: " + transactionId);
    }

}
