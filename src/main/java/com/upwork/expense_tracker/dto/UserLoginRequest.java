package com.upwork.expense_tracker.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserLoginRequest {

    @NotEmpty(message = "The email is required.")
    @Email(message = "The email must be valid.")
    String email;

    @NotEmpty(message = "The password is required.")
    String password;

}