package com.upwork.expense_tracker.dto;

import com.upwork.expense_tracker.validation.TransactionType;
import jakarta.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TransactionCreatingRequest {

    @NotEmpty(message = "The type is required.")
    @TransactionType
    String type;

    @NotEmpty(message = "The description is required.")
    String description;

}
