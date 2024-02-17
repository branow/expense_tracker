package com.upwork.expense_tracker.dto;

import com.upwork.expense_tracker.validation.TransactionType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TransactionUpdatingRequest {

    @NotNull(message = "The id is required")
    Integer id;

    @NotEmpty(message = "The type is required")
    @TransactionType
    String type;

    @NotEmpty(message = "The description is required")
    String description;

}

