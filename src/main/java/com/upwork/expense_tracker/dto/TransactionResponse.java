package com.upwork.expense_tracker.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TransactionResponse {

    Integer id;
    String type;
    String description;
    @JsonFormat(pattern = "yyyy-MM-dd")
    Date date;
    Integer userId;

}
