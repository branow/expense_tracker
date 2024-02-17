package com.upwork.expense_tracker.dto;

import com.upwork.expense_tracker.validation.Base64Image;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreatingRequest {

    @NotEmpty(message = "The email is required.")
    @Email(message = "The email must be valid.")
    String email;

    @NotEmpty(message = "The password is required.")
    String password;

    @NotEmpty(message = "The profile is required.")
    @Base64Image(message = "The profile must be a valid base64 image.")
    String profile;

}
